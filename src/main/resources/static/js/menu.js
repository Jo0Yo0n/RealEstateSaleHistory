$(document).ready(function () {
    function toggleMenu(iconId, menuId) {
        $("#" + iconId).click(function () {
            var isActive = $("#" + menuId).hasClass("active");

            $(".menu").removeClass("active");
            $(".close-menu").removeClass("active");

            if (!isActive) {
                $("#" + menuId).addClass("active");
                $("#" + menuId).find(".close-menu").addClass("active"); // 메뉴가 열릴 때 close-menu 버튼 활성화
            }
        });

        // close-menu 버튼에 클릭 이벤트 추가
        $("#" + menuId).find(".close-menu").click(function () {
            $("#" + menuId).removeClass("active"); // 메뉴 닫기
            $(this).removeClass("active"); // close-menu 버튼 비활성화
        });
    }

    toggleMenu("home-icon", "home-menu");
    toggleMenu("comm-icon", "comm-menu");
    toggleMenu("user-icon", "user-menu");
});