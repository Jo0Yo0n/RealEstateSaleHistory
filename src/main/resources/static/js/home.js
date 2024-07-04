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
}); */

// 페이지네이션 생성 함수
function createPagination(currentPage, totalPages) {

   var paginationHtml = '<div class="pagination">';
   var startPage, endPage;
   var maxPage = 5;

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
   // if (startPage > 1) {
   //    paginationHtml += '<button class="page-item page-link" data-page="1">&laquo;</button>';
   // }

   // '이전 10페이지' 버튼 생성
   if (startPage > maxPage) {
      paginationHtml += '<span class="page-item page-link" data-page="' + (startPage - maxPage) + '">&laquo;</span>';
   }

   // 페이지 번호 버튼 생성
   for (var i = startPage; i <= endPage; i++) {
      paginationHtml += '<span class="page-item page-link' + (i === currentPage ? ' active' : '') + '" data-page="' + i + '">' + i + '</span>';
   }

   // '다음 10페이지' 버튼 생성
   if (endPage < totalPages) {
      paginationHtml += '<span class="page-item page-link" data-page="' + (endPage + 1) + '">&raquo;</span>';
   }

   // '마지막' 페이지로 이동하는 버튼
   // if (endPage < totalPages) {
   //    paginationHtml += '<button class="page-item page-link" data-page="' + totalPages + '">&raquo;</button>';
   // }

   paginationHtml += '</div>';
   $('.pagination').html(paginationHtml);
   
     // 페이지 번호 버튼 클릭 이벤트 리스너
    $('.page-link').on('click', function() {
        currentPageNumber = $(this).data('page'); // 클릭된 페이지 번호를 전역 변수에 저장합니다.
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
                    <div class="building-favorite"><i class="fa-regular fa-star"></i></div>
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
                            <span class="sale-min-price">${estate.minSalePrice}억</span> ~ <span class="sale-max-price">${estate.maxSalePrice}억</span>
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

    
// 현재 선택된 건물의 ID를 저장할 변수
let currentSelectedBuildingId = null;

//상위 요소인 .district-list-container에 클릭 이벤트 리스너를 추가
document.querySelector('.district-list-container').addEventListener('click', function(event) {
	 // 클릭된 요소가 별 모양인지 확인
  if (event.target.classList.contains('fa-regular') && event.target.classList.contains('fa-star')) {
    // .building-header 요소를 찾습니다.
    var buildingHeader = event.target.closest('.building-header');
    // .building-header 요소 내부에서 .building-id 요소를 찾습니다.
    var realEstateId = buildingHeader.querySelector('.building-id').getAttribute('data-real-estate-id');
    console.log(realEstateId);
    
    // AJAX 요청을 통해 서버에 즐겨찾기 추가
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'favorites/' + realEstateId, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    
    xhr.onreadystatechange = function() {
      if (this.readyState === XMLHttpRequest.DONE) {
        if (this.status === 200) {
          console.log(this.responseText); // 성공 메시지 출력
        } else {
          console.error('즐겨찾기 추가에 실패했습니다.'); // 오류 메시지 출력
        }
      }
    };
    
    xhr.send();
  }
  
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
    
 	
  		// 클릭된 건물의 ID 추출
        const clickedBuildingId = buildingElement.querySelector('.building-id').dataset.realEstateId;

		if(currentSelectedBuildingId === clickedBuildingId){
			// 같은 건물을 클릭했으므로, 함수를 호출하지 않고 컨테이너의 표시 상태를 토글합니다.
			var container = document.getElementById('same-districtid-container');
			container.style.display = (container.style.display === 'none' ? 'block' : 'none');
		} else {
	        // onBuildingClick 함수 내에서 Promise 처리
	        onBuildingClick(estate).then(() => {
	            // '.same-districtid-container' 요소 선택
	            var container = document.getElementById('same-districtid-container');
	
	            // 이전에 선택된 건물과 같은지 확인
	            if (currentSelectedBuildingId === clickedBuildingId) {
	                // 같은 건물을 클릭했으므로 숨김
	                container.style.display = 'none';
	                // 선택된 건물 ID 초기화
	                currentSelectedBuildingId = null;
	            } else {
	                // 다른 건물을 클릭했으므로 보임
	                container.style.display = 'block';
	                // 현재 선택된 건물 ID 업데이트
	                currentSelectedBuildingId = clickedBuildingId;
	            }
	        }).catch(error => {
	            console.error('데이터를 로드하는 데 실패했습니다:', error);
	        });
		}
		
     	// 순차적으로 나타나는 애니메이션 적용
 	    var items = document.querySelectorAll('.estate-item');
        let delay = 0;
        
        items.forEach(function(item) {
        	setTimeout(function() {
	            item.style.opacity = '1'; // 요소를 보이게 합니다
	            item.style.transform = 'translateY(0)'; // 요소를 위로 이동시킵니다
        	}, delay);
        	delay += 500; // 다음 요소의 애니메이션 딜레이를 0.5초씩 증가
    	});

    
    }
});

      
      
  	// #homeIcon 클릭 시 데이터를 가져오는 함수와 전체 게시물 수를 가져오는 함수를 호출합니다.
	$('#toggle-menu-btn').click(function () {
		sendSearchCriteria();
	  	$('#same-districtid-container').hide();
	});

  });
  
//아파트 정보를 클릭했을 때 실행될 리스너 함수
function onBuildingClick(estate) {
   // getEstateCount 함수를 실행하여 총 데이터 개수를 가져옵니다.
   return getEstateCount({
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
            //const newCurrentPage = startPage - pageGroupSize;
            loadPage(currentPage-1, estate);
        });
        paginationContainer.appendChild(prevButton);
    }

    // 페이지 번호 버튼 생성
    for (let i = startPage; i <= endPage; i++) {
        const pageButton = document.createElement('button');
        pageButton.className = 'page-item';
        pageButton.innerText = i;
        pageButton.addEventListener('click', function() {
            loadPage(i, estate);
        });
        // 현재 페이지에 해당하는 버튼에 'active' 클래스를 추가합니다.
        if (i === currentPage) {
            pageButton.classList.add('active');
        }
        paginationContainer.appendChild(pageButton);
    }

    // 다음 페이지 그룹으로 이동하는 버튼 생성
    if (endPage < estateTotalPages) {
        const nextButton = document.createElement('button');
        nextButton.className = 'page-link';
        nextButton.innerText = '다음';
        nextButton.addEventListener('click', function() {
            //const newCurrentPage = endPage + 1;
            loadPage(currentPage+1, estate);
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
            // 컨테이너 비우기
            $('#estate-list').empty();
	        
            // 중복되지 않는 부동산 이름을 저장할 Set 객체 생성
		    const estateNames = new Set();
		
		    // 부동산 이름을 Set에 추가
		    data.forEach(function(estate) {
		        estateNames.add(estate.realEstate.complexName);
		    });
		
		    // Set에 저장된 각 부동산 이름에 대해 h4 요소 추가
		    estateNames.forEach(function(name) {
		        $('#estate-list').append(`
        		<div class="estate-header">
		        	<h4>${name} 아파트 🏢</h4>
        		</div>
            	<div class="estate-info">
                    <div>
                        <span>${estate.cityName}</span>
                        <span> / ${estate.districtName}</span>
                        <span> / ${estate.neighborhoodName}</span>
                    </div>
                    <div>
                        <span>${estate.constructionYear}</span>
                        <span>${estate.address}</span>
                        <span>${estate.addressStreet}</span>
                    </div>
            	</div>
		        `);
		    });
	        


	        
            data.forEach(function(estate) {
				var formattedContractDate = formatDate(estate.realEstateSale.contractDate);
	        	//  부동산 이름과 가격을 리스트 아이템으로 추가
		        $('#estate-list').append(`
					<div class="estate-item">
		                 <div class="estate-sale">
 							<div class="estate-lately-info">
							    <span class="estate-contract-date">${formattedContractDate}</span>,
							    <span class="estate-floor">${estate.realEstateSale.floor}층</span>,
							    <span class="estate-exclusive-area">${estate.realEstateSale.exclusiveArea}</span>㎡
							</div>
		                    <div>
		                       <div class="lately-title">실거래가 : <span class="lately-price" style="color: red;">${estate.realEstateSale.salePrice}억</span></div>
				                <a class="btn btn-warning" href="/realestate/detail/${estate.realEstateSale.salesId}" role="button" style="color: white;">매매 기록 보기</a>
		                    </div>
		                </div>
	            	</div>
		        `);
	   	 	});
        },
        error: function(error) {
            console.error('페이지 데이터를 불러오는 데 실패했습니다:', error);
        }
    });
}

function formatDate(dateString) {
  var year = dateString.substring(0, 4);
  var month = dateString.substring(4, 6);
  var day = dateString.substring(6, 8);

  return year + '년 ' + month + '월 ' + day + '일';
}


/*// 별 클릭 이벤트 리스너
document.querySelector('.building-favorite').addEventListener('click', function() {
  var realEstateId = this.parentElement.querySelector('.building-id').getAttribute('data-real-estate-id');
  
  // AJAX 요청을 통해 서버에 즐겨찾기 추가
  var xhr = new XMLHttpRequest();
  xhr.open('POST', 'favorites/' + realEstateId, true);
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
  
  xhr.onreadystatechange = function() {
    if (this.readyState === XMLHttpRequest.DONE) {
      if (this.status === 200) {
        console.log(this.responseText); // 성공 메시지 출력
      } else {
        console.error('즐겨찾기 추가에 실패했습니다.'); // 오류 메시지 출력
      }
    }
  };
  
  xhr.send();
});*/