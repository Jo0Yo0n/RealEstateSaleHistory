$(document).ready(function () {
  let currentTab = 'favorites';
  let currentPage = 0;
  let savedPassword = '';

  // 초기 로드
  loadContent(currentTab, currentPage);

  // 탭 클릭 이벤트
  $('.tab').click(function () {
    $('.tab').removeClass('active');
    $(this).addClass('active');
    currentTab = $(this).data('tab');
    currentPage = 0;
    loadContent(currentTab, currentPage);
  });

  // 페이지네이션 클릭 이벤트
  $(document).on('click', '.pagination span', function () {
    if ($(this).hasClass('disabled')) {
      return;
    }
    currentPage = parseInt($(this).data('page'));
    loadContent(currentTab, currentPage);
  });

  function loadContent(tab, page) {
    let url = tab === 'favorites' ? `/favorites` : `/community/${userId}`;

    $.ajax({
      url: `${url}?page=${page}`,
      method: 'GET',
      success: function (response) {
        $("#content-container").html(response);

        let paginationInfo = $("#pagination-info");
        let currentPage = parseInt(paginationInfo.data('current-page'));
        let totalPages = parseInt(paginationInfo.data('total-pages'));
        if (totalPages !== 0) {
          updatePagination(currentPage, totalPages);
        } else {
          $(".pagination").text('');
        }
      },
      error: function (xhr, status, error) {
        console.error('Error loading content: ', error);
      }
    });
  }

  function updatePagination(currentPage, totalPages) {
    let paginationHtml = '';
    const visiblePages = 5; // 한 번에 보여줄 페이지 번호의 개수

    let startPage = Math.max(0, currentPage - Math.floor(visiblePages / 2));
    let endPage = Math.min(totalPages - 1, startPage + visiblePages - 1);

    // 시작 페이지 조정
    if (endPage - startPage + 1 < visiblePages) {
      startPage = Math.max(0, endPage - visiblePages + 1);
    }

    // 이전 페이지 버튼
    if (currentPage > 0) {
      paginationHtml += `<span data-page="${currentPage - 1}">&laquo;</span>`;
    } else {
      paginationHtml += `<span class="disabled">&laquo;</span>`
    }

    // 페이지 번호
    for (let i = startPage; i <= endPage; i++) {
      if (i === currentPage) {
        paginationHtml += `<span class="active" data-page="${i}">${i
        + 1}</span>`;
      } else {
        paginationHtml += `<span data-page=${i}>${i + 1}</span>`;
      }
    }

    // 다음 페이지 버튼
    if (currentPage < totalPages - 1) {
      paginationHtml += `<span data-page="${currentPage + 1}">&raquo;</span>`;
    } else {
      paginationHtml += `<span class="disabled">&raquo;</span>`
    }

    $('.pagination').html(paginationHtml);
  }

  // 회원 정보 수정 버튼 기능
  $(document).on('click', '.update', function () {
    $('.modal-name').text('회원 정보 수정');
    $('#passwordConfirm').prop('required', true).show();
    $('label[for="passwordConfirm"]').show();
    $('.submit').text('정보 수정').removeClass('btn-danger').addClass(
        'btn-primary');
    $('#passwordModal').modal('show');
  });

  // 회원 탈퇴 버튼 기능
  $(document).on('click', '.delete', function () {
    $('.modal-name').text('회원 탈퇴');
    $('#passwordConfirm').prop('required', false).hide();
    $('label[for="passwordConfirm"]').hide();
    $('.submit').text('회원 탈퇴').removeClass('btn-primary').addClass(
        'btn-danger');
    $('#passwordModal').modal('show');
  });

  // 관리자 신청 버튼 기능
  $(document).on('click', '.agent', function () {
    $('#agentModalLabel').text('중개사 신청');
    $('#agentModal').modal('show');
  });

  // 즐겨찾기 클릭
  $(document).on('click', '.building', function () {

    // 특정 요소를 클릭한 경우 이벤트 중단
    if ($(event.target).closest('.favorite-remove').length > 0) {
      return;
    }

    var buildingName = $(this).find('.building-name').text();
    var neighborhood = $(this).find('.neighborhood').text().replace(' / ', '');
    window.location.href = `/realestate?lo=` + neighborhood + " " + buildingName
        + `&ms=`;

  });

  // 즐겨찾기 삭제 버튼 기능
  $(document).on('click', '.favorite-remove', function () {
    var favoriteId = $(this).closest('.building-header').find(
        '.favorite-id').text();
    $('.id').text(favoriteId);
    $('#favoriteModalLabel').text('즐겨찾기 삭제');
    $('#favoriteModal').modal('show');
  });

  // 게시글 클릭
  $(document).on('click', '.post', function () {

    // 특정 요소를 클릭한 경우 이벤트 중단
    if ($(event.target).closest('.post-remove').length > 0) {
      return;
    }
    var postId = $(this).find('.post-id').text();
    window.location.href = '/communityCard?postId=' + postId;
  });

  // 게시글 삭제 버튼 기능
  $(document).on('click', '.post-remove', function () {
    var postId = $(this).closest('.post-header').find('.post-id').text();
    console.log(postId);
    $('.id').text(postId);
    $('#postModalLabel').text('게시글 삭제');
    $('#postModal').modal('show');
  });

  // 폼 제출 이벤트 핸들러
  $(document).on('submit', '#form', function (event) {
    event.preventDefault();
    savedPassword = $('#password').val();
    let savedPasswordConfirm = $('#passwordConfirm').val();

    if ($('.modal-name').text() === '회원 탈퇴') {
      $('#passwordModal').modal('hide');
      $('#confirmModal').modal('show');
    } else {
      sendUpdateRequest(savedPassword, savedPasswordConfirm);
    }
  });

  // 회원탈퇴-확인 모달의 "네" 버튼 클릭 이벤트
  $(document).on('click', '#confirmDelete', function () {
    $('#confirmModal').modal('hide');
    sendDeleteRequest(savedPassword);
  });

  // 회원탈퇴-확인 모달의 "아니요" 버튼 클릭 이벤트
  $(document).on('click', '.close', function () {
    $('#passwordModal').modal('hide');
    $('#confirmModal').modal('hide');
  });

  // 즐겨찾기확인 모달의 "네" 버튼 클릭 이벤트
  $(document).on('click', '#favoriteDelete', function () {
    $('#confirmModal').modal('hide');
    sendDeleteFavorite(document.querySelector('.id').textContent);
  });

  // 게시글확인 모달의 "네" 버튼 클릭 이벤트
  $(document).on('click', '#postDelete', function () {
    $('#confirmModal').modal('hide');
    console.log(document.querySelector('.id').textContent);

    sendDeletePost(document.querySelector('.id').textContent);
  });

  // 모달 닫힘 이벤트 처리
  $('#passwordModal').on('hidden.bs.modal', function () {
    $('#password').val('');
    $('#passwordConfirm').val('');
    $('.submit').removeClass('btn-primary btn-danger');
  });

  // 중개사 신청 요청 처리
  $(document).on('click', '#agent-yes', function () {
    $('#agentModal').modal('hide');

    $.ajax({
      url: '/users/me/permission',
      type: 'POST',
      success: function (response) {
        $('#resultModalLabel').text('중개사 권한 신청 결과');
        $('#resultModalBody').text(response.message);
        $('#resultModal').modal('show');
      },
      error: function (xhr) {
        let errorMessage = "오류 발생: 서버에서 에러 메시지를 받지 못했습니다.";
        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        }

        $('#resultModalLabel').text('중개사 권한 신청 결과');
        $('#resultModalBody').text(errorMessage);
        $('#resultModal').modal('show');
      }
    });
  });

  // 회원 정보 수정 요청 처리
  function sendUpdateRequest(savedPassword, savedPasswordConfirm) {
    $.ajax({
      url: '/users/me',
      type: 'PUT',
      contentType: 'application/json',
      data: JSON.stringify({
        password: savedPassword,
        passwordConfirm: savedPasswordConfirm
      }),
      success: function (response) {
        $('#resultModalLabel').text('회원 정보 수정 결과');
        $('#resultModalBody').text('성공적으로 수정되었습니다.');
        $('#resultModal').modal('show');
        $('#closeModalButton').click(function () {
          window.location.href = '/users/logout';
        });
      },
      error: function (xhr) {
        let errorMessage = "오류 발생: 서버에서 에러 메시지를 받지 못했습니다.";
        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        }
        $('#resultModalLabel').text('회원 정보 수정 결과');
        $('#resultModalBody').text(errorMessage);
        $('#resultModal').modal('show');
      }
    });
    $('#confirmModal').modal('hide');
    $('#passwordModal').modal('hide');
  }

  // 회원 탈퇴 요청 처리
  function sendDeleteRequest(savedPassword) {
    $.ajax({
      url: '/users/me',
      type: 'DELETE',
      contentType: 'application/json',
      data: JSON.stringify({
        password: savedPassword
      }),
      success: function (response) {
        $('#resultModalLabel').text('회원 탈퇴 결과');
        $('#resultModalBody').text('탈퇴되었습니다.');
        $('#resultModal').modal('show');
        $('#closeModalButton').click(function () {
          window.location.href = '/users/logout';
        });
      },
      error: function (xhr) {
        let errorMessage = "오류 발생: 서버에서 에러 메시지를 받지 못했습니다.";
        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        }
        $('#resultModalLabel').text('회원 탈퇴 결과');
        $('#resultModalBody').text(errorMessage);
        $('#resultModal').modal('show');
      }
    });
  }

  // 즐겨찾기 삭제 요청 처리
  function sendDeleteFavorite(delFavoriteId) {
    $.ajax({
      url: '/favorites/' + delFavoriteId,
      type: 'DELETE',
      contentType: 'application/json',
      success: function (response) {
        $('#resultModalLabel').text('즐겨찾기 삭제 결과');
        $('#resultModalBody').text('삭제되었습니다.');
        $('#resultModal').modal('show');
        $('#closeModalButton').click(function () {
          window.location.href = '/users/me';
        });
      },
      error: function (xhr) {
        let errorMessage = "오류 발생: 서버에서 에러 메시지를 받지 못했습니다.";
        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        }
        $('#resultModalLabel').text('즐겨찾기 결과');
        $('#resultModalBody').text(errorMessage);
        $('#resultModal').modal('show');
      }
    });
  }

  // 게시글 삭제 요청 처리
  function sendDeletePost(postId) {
    $.ajax({
      url: '/postDelete?postId=' + postId,
      type: 'DELETE',
      contentType: 'application/json',
      success: function (response) {
        $('#resultModalLabel').text('게시글 삭제 결과');
        $('#resultModalBody').text('삭제되었습니다.');
        $('#resultModal').modal('show');
        $('#closeModalButton').click(function () {
          window.location.href = '/users/me';
        });
      },
      error: function (xhr) {
        let errorMessage = "오류 발생: 서버에서 에러 메시지를 받지 못했습니다.";
        if (xhr.responseJSON && xhr.responseJSON.message) {
          errorMessage = xhr.responseJSON.message;
        }
        $('#resultModalLabel').text('게시글 결과');
        $('#resultModalBody').text(errorMessage);
        $('#resultModal').modal('show');
      }
    });
  }
});