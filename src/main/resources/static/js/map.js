$(document).ready(function () {
	// 닫기 버튼에 클릭 이벤트 리스너를 추가합니다.
	document.getElementById('close-button').addEventListener('click', function() {
	    // 'closeable-container' 요소를 찾아서 숨깁니다.
	    document.getElementById('same-districtid-container').style.display = 'none';
	});
});

