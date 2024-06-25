<script>
		$(document).ready(function() {
			$('.filterId').click(function() {
				// 모든 필터의 색상을 초기화
				$('.filterId').removeClass('selectedFlag');
	
				// 클릭된 요소에 클래스 추가
				$(this).addClass('selectedFlag');
	
				// 클릭된 요소의 id 추출
				var selectedId = $(this).attr('id');
				console.log(selectedId);
	
				// id를 이용한 추가 작업을 여기에 작성
			})
		});
	</script>