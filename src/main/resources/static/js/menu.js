$(document).ready(function () {
  // 검색창의 값을 전역 변수에 저장하지 않고 바로 동기화
  $('.search-box').on('input', function() {
    var searchValue = $(this).val();
    // 모든 검색창에 입력값 동기화
    $('.search-box').val(searchValue);
  });

  function toggleMenu() {
    var $homeMenu = $("#home-menu");
    var $toggleBtn = $("#toggle-menu-btn");

    $toggleBtn.click(function () {
      var isActive = $homeMenu.hasClass("active");

      if (!isActive) {
        $homeMenu.addClass("active");
        $toggleBtn.addClass("active");
        // 메뉴가 열릴 때 데이터를 불러옵니다.
        if (typeof sendSearchCriteria === 'function') {
          sendSearchCriteria();
        }
      } else {
        $homeMenu.removeClass("active");
        $toggleBtn.removeClass("active");
      }
    });

    $homeMenu.find(".close-menu").click(function () {
      $homeMenu.removeClass("active");
      $toggleBtn.removeClass("active");
    });
  }

  // 메뉴 토글 기능 초기화
  toggleMenu();

  // 페이지 로드 시 초기 데이터 로딩
  if (typeof sendSearchCriteria === 'function') {
    sendSearchCriteria();
  }
});