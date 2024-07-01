$(document).ready(function () {
	$('.filter-id').click(function () {
		// 모든 필터의 색상을 초기화
		$('.filter-id').removeClass('selected-flag');

		// 클릭된 요소에 클래스 추가
		$(this).addClass('selected-flag');

		// 클릭된 요소의 id 추출
		var selectedId = $(this).attr('id');
		console.log(selectedId);

		// id를 이용한 추가 작업을 여기에 작성
	});
});







// 페이지네이션 생성 함수
function createPagination(currentPage, totalPages) {
	console.log(totalPages);

	var paginationHtml = '<div class="pagination">';
	var startPage, endPage;
	var maxPage = 10;

	// 현재 페이지가 1부터 10 사이인 경우
	if (currentPage <= maxPage) {
		startPage = 1;
		endPage = maxPage;
	} else {
		// 현재 페이지를 기준으로 앞뒤로 5페이지씩 표시
		var startOffset = (currentPage % maxPage) === 0 ? maxPage : currentPage % maxPage;
		startPage = currentPage - startOffset + 1;
		endPage = startPage + maxPage - 1;

		// 총 페이지 수를 넘지 않도록 조정
		if (endPage > totalPages) {
			endPage = totalPages;
			startPage = totalPages - maxPage + 1;
		}
	}

	// '처음' 페이지로 이동하는 버튼
	if (startPage > 1) {
		paginationHtml += '<button class="page-item page-link" data-page="1">처음</button>';
	}

	// '이전 10페이지' 버튼 생성
	if (startPage > maxPage) {
		paginationHtml += '<button class="page-item page-link" data-page="' + (startPage - maxPage) + '">이전 10페이지</button>';
	}

	// 페이지 번호 버튼 생성
	for (var i = startPage; i <= endPage; i++) {
		paginationHtml += '<button class="page-item page-link' + (i === currentPage ? ' active' : '') + '" data-page="' + i + '">' + i + '</button>';
	}

	// '다음 10페이지' 버튼 생성
	if (endPage < totalPages) {
		paginationHtml += '<button class="page-item page-link" data-page="' + (endPage + 1) + '">다음 10페이지</button>';
	}

	// '마지막' 페이지로 이동하는 버튼
	if (endPage < totalPages) {
		paginationHtml += '<button class="page-item page-link" data-page="' + totalPages + '">마지막</button>';
	}

	paginationHtml += '</div>';
	$('.pagination').html(paginationHtml);
}

// AJAX를 사용하여 부동산 목록 데이터를 가져오는 함수
function fetchRealEstateData(realEstateId, page, size) {
	$.ajax({
		url: '/realestate/search',
		type: 'GET',
		data: {
			realEstateId: realEstateId,
			page: page,
			size: size
		},
		success: function (data) {
			if (data && data.length > 0) {
				//데이터가 존재하는 경우, HTML 생성 및 삽입
				let realEstateListHtml = '';
				data.forEach(function (realEstate) {
					realEstateListHtml += `<div class="real-estate-item"><h4>${realEstate.realEstateSale.salesId}</h4></div>`;
				});

				$('.district-list-container').html(realEstateListHtml);
			} else {
				// 데이터가 없을 경우, 사용자에게 알림
				$('.district-list-container').html('<p>부동산 목록이 없습니다.</p>')
			}

		},
		error: function (error) {
			console.error('데이터를 가져오는데 실패했습니다:', error);
		}
	});
}

// 총 데이터 개수를 가져오는 AJAX 요청 함수
function fetchEstateCount(realEstateId) {
	$.ajax({
		url: '/realestate/count',
		type: 'GET',
		data: { realEstateId: realEstateId },
		success: function (count) {
			totalEstateCount = count;
			console.log(count);
			createPagination(1, Math.ceil(count / 10));
		},
		error: function (error) {
			console.error('Error fetching estate count:', error);
		}
	});
}

// 총 데이터 개수를 가져오는 AJAX 요청 함수
function fetchEstateCount(realEstateId) {
	return new Promise((resolve, reject) => {
		$.ajax({
			url: '/realestate/count',
			type: 'GET',
			data: { realEstateId: realEstateId },
			success: function (count) {
				console.log(count);
				resolve(count);
			},
			error: function (error) {
				console.error('Error fetching estate count:', error);
				reject(error);
			}
		});
	});
}
// 페이지 번호 클릭 이벤트 핸들러
$(document).on('click', '.page-link', function (e) {
	e.preventDefault();
	var selectedPage = parseInt($(this).data('page'));
	console.log(selectedPage);
	fetchEstateCount(0).then(allCount => {
		console.log(allCount);
		fetchRealEstateData(0, selectedPage, 10); // 여기서 0은 realEstateId의 예시 값입니다.
		createPagination(selectedPage, Math.ceil(allCount / 10));
	}).catch(error => {
		console.error('Error in pagination:', error);
	});
});

// #homeIcon 클릭 시 데이터를 가져오는 함수와 전체 게시물 수를 가져오는 함수를 호출합니다.
$('#home-icon').click(function () {
	fetchEstateCount(0).then(allCount => {
		console.log(allCount);
		createPagination(1, Math.ceil(allCount / 10));
	}); // 첫 번째 페이지 데이터와 총 데이터 개수를 가져옵니다.
	fetchRealEstateData(0, 1, 10); //첫 번째 페이지 
});






  // 전용 면적 관려 js


// 최소 면적 선택박스 변경 시 최대 면적 옵션 업데이트 함수
function updateMaxAreaOptions() {
    var minArea = parseInt(document.getElementById('min-area').value);
    var maxAreaSelect = document.getElementById('max-area');
    maxAreaSelect.innerHTML = ''; // 기존 옵션 제거

    // 최소 면적 선택 시 최대 면적 옵션 생성
    for (var i = minArea + 10; i <= 100; i += 10) {
        var option = document.createElement('option');
        option.value = i;
        option.textContent = i + '㎡';
        maxAreaSelect.appendChild(option);
    }
    
    // '최대' 옵션 추가
    var maxOption = document.createElement('option');
    maxOption.value = 'max'; // '최대' 옵션의 value 설정
    maxOption.textContent = '최대';
    maxAreaSelect.appendChild(maxOption);

    updateAreaDisplay(); // 선택된 면적대 표시 업데이트
}

// 선택된 면적대 표시 업데이트 함수
function updateAreaDisplay() {
    var minArea = document.getElementById('min-area').value;
    var maxArea = document.getElementById('max-area').value;
    var minAreaDisplay = minArea === '0' ? '0㎡' : minArea + '㎡';
    var maxAreaDisplay = maxArea === 'max' ? '최대' : maxArea + '㎡';
    
    document.getElementById('min-area-display').textContent = minAreaDisplay;
    document.getElementById('max-area-display').textContent = maxAreaDisplay;
}

// 면적대 확인 함수
function confirmAreaRange() {
    var minArea = document.getElementById('min-area').value;
    var maxArea = document.getElementById('max-area').value;

    // 화면에 표시
    document.getElementById('min-area-display').textContent = minArea + '㎡';
    document.getElementById('max-area-display').textContent = maxArea === 'max' ? '최대' : maxArea + '㎡';
    
    // TODO: 서버로 값 전송하는 로직 구현
	 sendSearchCriteria();
	 closeAreaPopup(); // 면적대를 확인한 후 팝업창을 닫습니다.
}


// 면적대 선택 버튼 클릭 이벤트 리스너
document.getElementById('area-select-btn').addEventListener('click', function() {
    var popup = document.getElementById('area-range-popup');
    popup.style.display = popup.style.display === 'block' ? 'none' : 'block';
});

	// 최소 가격 선택박스 변경 시 최대 가격 옵션 업데이트 및 선택된 가격대 표시 업데이트
	document.getElementById('min-area').addEventListener('change', function() {
	    updateMaxAreaOptions();
 	    updateAreaDisplay();
 	});

