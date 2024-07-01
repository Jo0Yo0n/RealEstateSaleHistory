$(document).ready(function () {
  let savedPassword = '';

  // 회원 정보 수정 버튼 기능
  $(document).on('click', '.update', function () {
    $('.modal-name').text('회원 정보 수정');
    $('#passwordConfirm').prop('required', true).show();
    $('label[for="passwordConfirm"]').show();
    $('.submit').text('정보 수정').removeClass('btn-danger').addClass('btn-primary');
    $('#passwordModal').modal('show');
  });

  // 회원 탈퇴 버튼 기능
  $(document).on('click', '.delete', function () {
    $('.modal-name').text('회원 탈퇴');
    $('#passwordConfirm').prop('required', false).hide();
    $('label[for="passwordConfirm"]').hide();
    $('.submit').text('회원 탈퇴').removeClass('btn-primary').addClass('btn-danger');
    $('#passwordModal').modal('show');
  });

  // 관리자 신청 버튼 기능
  $(document).on('click', '.agent', function () {
    $('#agentModalLabel').text('중개사 신청');
    $('#agentModal').modal('show');
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
});