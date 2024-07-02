
$(document).ready(function () {
    // 검색창의 값을 전역 변수에 저장하지 않고 바로 동기화
    $('.search-box').on('input', function() {
        var searchValue = $(this).val();
        // 모든 검색창에 입력값 동기화
        $('.search-box').val(searchValue);
    });
	
   function toggleMenu(iconId, menuId) {
    $("#" + iconId).click(function () {
        var isActive = $("#" + menuId).hasClass("active");

        $(".menu").removeClass("active");
        $(".close-menu").removeClass("active");

        if (!isActive) {
            $("#" + menuId).addClass("active");
            $("#" + menuId).find(".close-menu").addClass("active");
            // home-icon 클릭 시에만 search-container 안의 search-box 숨김
/*            if (iconId === "home-icon") {
                $('.search-container .search').hide();
            }*/
        } else {
            // 다른 메뉴 아이콘 클릭 시 search-container 안의 search-box 표시
            $('.search-container .search').show();
        }
    });

    $("#" + menuId).find(".close-menu").click(function () {
        $("#" + menuId).removeClass("active");
        $(this).removeClass("active");
        // 메뉴 닫을 때 search-container 안의 search-box 표시
        $('.search-container .search').show();
    });
}



    // 기존 메뉴 토글 기능
    toggleMenu("home-icon", "home-menu");
    toggleMenu("comm-icon", "comm-menu");
    toggleMenu("user-icon", "user-menu");
});