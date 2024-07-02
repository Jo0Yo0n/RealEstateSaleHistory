// 현재 페이지 번호를 저장하는 전역 변수
var currentPageNumber = 1;
var estateTotalPages;
/*$(document).ready(function () {
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
});*/

// 페이지네이션 생성 함수
function createPagination(currentPage, totalPages) {
    var paginationHtml = '<div class="pagination">';
    var startPage, endPage;
    var maxPage = 5;

    // 총 페이지 수가 최대 페이지 수보다 작은 경우
    if (totalPages <= maxPage) {
        startPage = 1;
        endPage = totalPages;
    } else {
        // 현재 페이지를 기준으로 앞뒤로 페이지를 표시
        var startOffset = (currentPage % maxPage) === 0 ? maxPage : currentPage % maxPage;
        startPage = currentPage - startOffset + 1;
        endPage = startPage + maxPage - 1;

        // 총 페이지 수를 넘지 않도록 조정
        if (endPage > totalPages) {
            endPage = totalPages;
            startPage = endPage - maxPage + 1;
        }
    }

    // '이전' 페이지 버튼 생성
    if (currentPage > 1) {
        paginationHtml += '<button class="page-item page-link" data-page="' + (currentPage - 1) + '">이전</button>';
    }

    // 페이지 번호 버튼 생성
    for (var i = startPage; i <= endPage; i++) {
        paginationHtml += '<button class="page-item page-link' + (i === currentPage ? ' active' : '') + '" data-page="' + i + '">' + i + '</button>';
    }

	// '처음' 페이지로 이동하는 버튼
	// if (startPage > 1) {
	// 	paginationHtml += '<button class="page-item page-link" data-page="1">&laquo;</button>';
	// }
	
    // '다음' 페이지 버튼 생성
    if (currentPage < totalPages) {
        paginationHtml += '<button class="page-item page-link" data-page="' + (currentPage + 1) + '">다음</button>';
    }

    paginationHtml += '</div>';
    $('.pagination').html(paginationHtml);

	// '다음 10페이지' 버튼 생성
	if (endPage < totalPages) {
		paginationHtml += '<button class="page-item page-link" data-page="' + (endPage + 1) + '">&gt;</button>';
	}

	// '마지막' 페이지로 이동하는 버튼
	// if (endPage < totalPages) {
	// 	paginationHtml += '<button class="page-item page-link" data-page="' + totalPages + '">&raquo;</button>';
	// }


    // 페이지 번호 버튼 클릭 이벤트 리스너
    $('.page-link').on('click', function() {
        var clickedPageNumber = $(this).data('page');
        currentPageNumber = clickedPageNumber; // 클릭된 페이지 번호를 전역 변수에 저장합니다.
        sendSearchCriteria(); // 페이지 번호가 업데이트된 후 sendSearchCriteria 함수를 호출합니다.
    });
}



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


	// 선택된 조건에 따른 데이터 개수를 가져오는 AJAX 요청 함수
	function getFilteredEstateCount(district, neighborhood, PriceMin, PriceMax, ExclusiveSizeMin, ExclusiveSizeMax) {
	    return new Promise((resolve, reject) => {
	        $.ajax({
	            url: '/realestate/count',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				districtName: district,
				neighborhoodName: neighborhood,
				PriceMin: PriceMin,
				PriceMax: PriceMax,
				ExclusiveSizeMin: ExclusiveSizeMin,
				ExclusiveSizeMax:ExclusiveSizeMax
			}),
			dataType: 'json',
	            success: function (count) {
	                resolve(count);
	            },
	            error: function (error) {
	                console.error('Error fetching estate count by criteria:', error);
	                reject(error);
	            }
	        });
	    });
	}



function showRealEstate(response) {
    // response에서 부동산 데이터를 추출하여 HTML로 변환합니다.
    var realEstateHtml = response.map(function(estate) {
        return `
            <div class="building">
                <div class="building-header">
                	<div class="building-id" data-real-estate-id="${estate.realEstateId}"></div>
                    <div class="building-label">아파트</div>
                    <div class="building-name">${estate.complexName}</div>
                </div>
                <div class="building-info">
                    <div>
                        <span>${estate.cityName}</span>
                        <span> / ${estate.districtName}</span>
                        <span> / ${estate.neighborhoodName}</span>
                    </div>
                    <div>
                        <span>설립:${estate.constructionYear}</span>
                        <span> / 번지:${estate.address}</span>
                        <span> / 도로명:${estate.addressStreet}</span>
                    </div>
                </div>
                <div class="building-sale">
                    <div>
                        <div class="lately-title">최근 매매 실거래가</div>
                        <div class="lately-price">${estate.latelySalePrice}억</div>
					<div class="lately-info">
					    <span class="lately-contract-date">${estate.latelyContractDate}</span>,
					    <span class="lately-floor">${estate.latelyFloor}</span>,
					    <span class="lately-exclusive-area">${estate.latelyExclusiveArea}</span>㎡
					</div>
                    </div>
                    <div class="sale-info">
                        <div>
                            <span class="sale-title">전체 매매가</span>
                            <span class="min-price">${estate.minSalePrice}억</span> ~ <span class="max-price">${estate.maxSalePrice}억</span>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }).join('');
    
    // 생성된 HTML을 district-list-container에 삽입합니다.
    $('.district-list-container').html(realEstateHtml);
}

// 중복되지 않는 아파트의 매매 정보를 조회하여 보여주는 함수
function sendEstateData() {
  fetch('/realestate/search/byCriteria', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      districtName: estate.districtName,
      neighborhoodName: estate.neighborhoodName,
      minSalePrice: estate.minSalePrice,
      maxSalePrice: estate.maxSalePrice,
      realEstateId: estate.realEstateId
    })
  })
  .then(response => response.json())
  .then(data => console.log(data))
  .catch((error) => console.error('Error:', error));
}

	// 해당하는 총 데이터 개수를 가지고 오는 함수
	function getEstateCount(searchCriteria) {
	  return new Promise((resolve, reject) => {
	    $.ajax({
	      url: '/realestate/count/byCriteria',
	      type: 'POST',
	      contentType: 'application/json',
	      data: JSON.stringify(searchCriteria),
	      success: function(count) {
	        resolve(count);
	      },
	      error: function(error) {
	        reject(error);
	      }
	    });
	  });
	}







  document.addEventListener('DOMContentLoaded', function () {
	
    const citySelect = document.getElementById('city');
    const districtSelect = document.getElementById('district');
    const neighborhoodSelect = document.getElementById('neighborhood');

    // 초기 가격대 선택을 비활성화합니다.
    document.getElementById('min-price').selectedIndex = 0; // "0" 옵션을 선택합니다.
    document.getElementById('max-price').innerHTML = ''; // 최대 가격 옵션을 비웁니다.
    // 페이지 로드 시 최대 가격 옵션 초기화 및 선택된 가격대 표시 업데이트
    updatePriceDisplay(); // 선택된 가격대 표시를 업데이트합니다.
	updateMaxPriceOptions();
    updateMaxAreaOptions();
    
    // 면적대 선택 버튼 클릭 이벤트 리스너
    document.getElementById('area-select-btn').addEventListener('click', function () {
      var popup = document.getElementById('area-range-popup');
      popup.style.display = popup.style.display === 'block' ? 'none' : 'block';
    });

    // Fetch cities and populate city select box
    fetch('/admindivison/cities')
    .then(response => response.json())
    .then(data => {
      data.forEach(city => {
        const option = document.createElement('option');
        option.value = city.cityId;
        option.text = city.cityName;
        citySelect.appendChild(option);
      });
    });

    // 행정구역 선택 시 이벤트 리스너
    citySelect.addEventListener('change', function () {
      const selectedCityId = this.value;
      districtSelect.innerHTML = '<option value="" disabled selected>자치구</option>'; // 기본 옵션 초기화

      // "전체" 옵션을 다시 생성합니다.
      const allOption = document.createElement('option');
      allOption.value = 'all';
      allOption.text = '전체';
      districtSelect.appendChild(allOption);

      if (selectedCityId && selectedCityId !== 'all') {
        // AJAX 요청을 통해 서버에서 자치구 목록을 가져옵니다.
        fetch(`/admindivison/cities/districts?cityId=${selectedCityId}`)
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(districts => {
          districts.forEach(district => {
            const option = document.createElement('option');
            option.value = district.districtId;
            option.text = district.districtName;
            districtSelect.appendChild(option);
          });
        })
        .catch(error => {
          console.error('Error fetching districts:', error);
          // 에러 처리를 위한 추가적인 사용자 피드백을 여기에 구현할 수 있습니다.
        });
      }
    });

// 자치구 선택 시 이벤트 리스너
districtSelect.addEventListener('change', function () {
  const selectedDistrictName = this.value;
  neighborhoodSelect.innerHTML = '<option value="" disabled selected>읍/면/동</option>'; // 기본 옵션 초기화

  // "전체" 옵션을 다시 생성합니다.
  const allOption = document.createElement('option');
  allOption.value = 'all';
  allOption.text = '전체';
  neighborhoodSelect.appendChild(allOption);

  if (selectedDistrictName && selectedDistrictName !== 'all') {
    // AJAX 요청을 통해 서버에서 동 목록을 가져옵니다.
    fetch(`/admindivison/districts/neighborhoods?districtId=${selectedDistrictName}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(neighborhoods => {
      neighborhoods.forEach(neighborhood => {
        const option = document.createElement('option');
        option.value = neighborhood.neighborhoodName;
        option.text = neighborhood.neighborhoodName;
        neighborhoodSelect.appendChild(option);
      });
    })
    .catch(error => {
      console.error('Error fetching neighborhoods:', error);
    });
  }
});

//각 셀렉트 박스의 변경 이벤트에 함수를 연결합니다.
$('#district, #neighborhood').change(function () {
  sendSearchCriteria();  // 조건에 따른 매물 검색 함수 실행.
});

    

//상위 요소인 .district-list-container에 클릭 이벤트 리스너를 추가
document.querySelector('.district-list-container').addEventListener('click', function(event) {
let buildingElement = event.target.closest('.building');
if (buildingElement) {
    // 데이터 추출 최적화
    const estateIdElement = buildingElement.querySelector('.building-id');
    const estateInfoSpans = buildingElement.querySelectorAll('.building-info span');
    const estateSaleInfoSpans = buildingElement.querySelectorAll('.sale-info span');
	
	
    const estate = {
        realEstateId: parseInt(estateIdElement.dataset.realEstateId, 10),
        complexName: buildingElement.querySelector('.building-name').textContent,
        cityName: estateInfoSpans[0].textContent,
        districtName: estateInfoSpans[1].textContent.replace(/ \/ /g, '').trim(),
        neighborhoodName: estateInfoSpans[2].textContent.replace(/ \/ /g, '').trim(),
        constructionYear: estateInfoSpans[3].textContent,
        address: estateInfoSpans[4].textContent,
        addressStreet: estateInfoSpans[5].textContent,
        latelySalePrice: parseFloat(buildingElement.querySelector('.lately-price').textContent),
        latelyContractDate: buildingElement.querySelector('.lately-contract-date').textContent,
		latelyFloor: buildingElement.querySelector('.lately-floor').textContent,
		latelyExclusiveArea: parseFloat(buildingElement.querySelector('.lately-exclusive-area').textContent),
        minSalePrice: parseFloat(estateSaleInfoSpans[1].textContent.replace(/[^0-9.]/g, '')),
        maxSalePrice: parseFloat(estateSaleInfoSpans[2].textContent.replace(/[^0-9.]/g, '')),
    };
	
    onBuildingClick(estate);
    }
});

      
      
  	// #homeIcon 클릭 시 데이터를 가져오는 함수와 전체 게시물 수를 가져오는 함수를 호출합니다.
	$('#toggle-menu-btn').click(function () {
		sendSearchCriteria();
	});

  });
  
//아파트 정보를 클릭했을 때 실행될 리스너 함수
function onBuildingClick(estate) {
   // getEstateCount 함수를 실행하여 총 데이터 개수를 가져옵니다.
   getEstateCount({
       districtName: estate.districtName,
       neighborhoodName: estate.neighborhoodName,
       minSalePrice: estate.minSalePrice,
       maxSalePrice: estate.maxSalePrice,
       realEstateId: estate.realEstateId
   }).then(totalCount => {
       // 총 데이터 개수를 바탕으로 페이지네이션을 생성합니다.
       const pageSize = 6;
       estateTotalPages = Math.ceil(totalCount / pageSize); // 전역 변수에 totalPages를 할당합니다
       const currentPage = 1; // 현재 페이지 번호 초기화
	   console.log(estateTotalPages);
/*       generatePagination(currentPage, estate);*/
       loadPage(currentPage, estate);
   }).catch(error => {
       console.error('데이터 개수를 가져오는 데 실패했습니다:', error);
   });
}
	
// 페이지네이션 생성하기
function generatePagination(currentPage, estate) {
    const paginationContainer = document.getElementById('customPagination');
    paginationContainer.innerHTML = ''; // 기존 페이지네이션을 초기화합니다.

    // 페이지네이션에 표시할 페이지 범위 계산
    const pageGroupSize = 5; // 한 번에 표시할 페이지 수
    const startPage = Math.floor((currentPage - 1) / pageGroupSize) * pageGroupSize + 1;
    const endPage = Math.min(startPage + pageGroupSize - 1, estateTotalPages);

    // 이전 페이지 그룹으로 이동하는 버튼 생성
    if (startPage > 1) {
        const prevButton = document.createElement('button');
        prevButton.className = 'page-link';
        prevButton.innerText = '이전';
        prevButton.addEventListener('click', function() {
            const newCurrentPage = startPage - pageGroupSize;
            loadPage(newCurrentPage, estate);
        });
        paginationContainer.appendChild(prevButton);
    }

    // 페이지 번호 버튼 생성
    for (let i = startPage; i <= endPage; i++) {
        const pageButton = document.createElement('button');
        pageButton.className = 'page-link';
        pageButton.innerText = i;
        pageButton.addEventListener('click', function() {
            loadPage(i, estate);
        });
        paginationContainer.appendChild(pageButton);
    }

    // 다음 페이지 그룹으로 이동하는 버튼 생성
    if (endPage < estateTotalPages) {
        const nextButton = document.createElement('button');
        nextButton.className = 'page-link';
        nextButton.innerText = '다음';
        nextButton.addEventListener('click', function() {
            const newCurrentPage = endPage + 1;
            loadPage(newCurrentPage, estate);
        });
        paginationContainer.appendChild(nextButton);
    }
}
	
// 페이지 로드 함수
function loadPage(pageNumber, estate) {
    // 페이지네이션을 업데이트합니다.
    generatePagination(pageNumber, estate);
    // AJAX 요청을 사용하여 페이지 번호에 해당하는 데이터를 불러옵니다.
    $.ajax({
        url: 'realestate/search/byCriteria', // 서버의 엔드포인트
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            districtName: estate.districtName,
            neighborhoodName: estate.neighborhoodName,
           	minSalePrice: estate.minSalePrice,
       		maxSalePrice: estate.maxSalePrice,
            page: pageNumber,
            realEstateId: estate.realEstateId
        }),
        success: function(data) {
            // 데이터를 성공적으로 불러왔을 때의 처리 로직
            console.log('Loaded data for page:', pageNumber, data);
            // 여기에 데이터를 페이지에 뿌려주는 로직을 구현합니다.
        },
        error: function(error) {
            console.error('페이지 데이터를 불러오는 데 실패했습니다:', error);
        }
    });
}