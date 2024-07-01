let dataList = [];
const CACHE_EXPIRY_TIME = 24 * 60 * 60 * 1000; // 캐시 유효 기간: 24시간

$(document).ready(function() {
	// 캐시된 데이터 확인
	const cachedData = localStorage.getItem('autoCompleteData');
	const cacheTimestamp = localStorage.getItem('autoCompleteDataTimestamp');
	const now = Date.now();

	if (cachedData && cacheTimestamp && (now - cacheTimestamp < CACHE_EXPIRY_TIME)) {
		dataList = JSON.parse(cachedData);
	} else {
		// 데이터가 캐시되지 않았거나 유효 기간이 지난 경우 서버에서 가져옴
		newFetchData({ url: "/admindivison/auto/search", dataFunction: autoSearchData });
	}

	const chartApiEndpoints = [
		{ url: "/statistics/districts/avg-sales-prices", chartId: 'chart1', chartType: 'bar', dataFunction: avgSaleDistrict, label: '지역구 전체 매매 평균 순위' },
		{ url: "/statistics/neighborhoods/most-favorite", chartId: 'chart2', chartType: 'bar', dataFunction: mostFavoriteNeighborhood, label: '즐겨찾기 많이 된 동 순위' },
		{ url: "/statistics/real-estates/changes/five-years?orderBy=desc", chartId: 'chart3', chartType: 'bar', dataFunction: realEstateChangeFiveYear, label: '최근 5년간 최고 상승' },
		{ url: "/statistics/real-estates/changes/five-years?orderBy=asc", chartId: 'chart4', chartType: 'bar', dataFunction: realEstateChangeFiveYear, label: '최근 5년간 최저 상승' }
	];

	const newInfoApiEndpoints = [
		{ url: "/community/new", dataFunction: newCommunityData },
		{ url: "/realestate/new", dataFunction: newRealestateData }
	];

	// 각 API로부터 데이터를 가져오는 함수 (차트)
	function chartFetchData({ url, chartId, chartType, dataFunction, label }) {
		$.ajax({
			url: url,
			method: 'GET',
			contentType: 'application/json',
			success: function(data) {
				const { labels, datasets } = dataFunction(data, label);
				renderChart(chartId, chartType, labels, datasets);
			},
			error: function(error) {
				$('#' + chartId).text(`통계 ${chartId.slice(-1)}: 데이터 가져오기 실패`);
				console.error(`Error fetching ${chartId}:`, error);
			}
		});
	}

	/* 차트 */
	function renderChart(chartId, chartType, labels, datasets) {
		const ctx = document.getElementById(chartId).getContext('2d');
		new Chart(ctx, {
			type: chartType,
			data: {
				labels: labels,
				datasets: datasets
			},
			options: {
				scales: {
					y: {
						beginAtZero: true
					}
				}
			}
		});
	}

	/* 지역구 전체 매매 평균 순위 */
	function avgSaleDistrict(data, datasetLabel) {
		const labels = data.map(item => item.districtName);
		const values = data.map(item => item.avgPrice);
		return {
			labels,
			datasets: [{
				label: datasetLabel,
				data: values,
				backgroundColor: 'rgba(249, 222, 123)',
				borderColor: 'rgba(234, 227, 161)',
				borderWidth: 1
			}]
		};
	}

	/* 즐겨찾기 많이 된 동 순위 */
	function mostFavoriteNeighborhood(data, datasetLabel) {
		const labels = data.map(item => item.neighborhoodName);
		const values = data.map(item => item.favoriteCnt);
		return {
			labels,
			datasets: [{
				label: datasetLabel,
				data: values,
				backgroundColor: 'rgba(249, 222, 123)',
				borderColor: 'rgba(234, 227, 161)',
				borderWidth: 1
			}]
		};
	}

	/* 최근 5년간 차이 데이터 */
	function realEstateChangeFiveYear(data) {
		const labels = data.map(item => item.complexName);
		const maxPrices = data.map(item => item.maxPrice);
		const minPrices = data.map(item => item.minPrice);
		const diffPrices = data.map(item => item.diffPrice);
		return {
			labels,
			datasets: [
				{
					label: `가장 작은 매매가`,
					data: minPrices,
					backgroundColor: 'rgba(249, 222, 123)',
					borderColor: 'rgba(234, 227, 161)',
					borderWidth: 1
				},
				{
					label: `가장 큰 매매가`,
					data: maxPrices,
					backgroundColor: 'rgba(190, 190, 129)',
					borderColor: 'rgba(234, 227, 161)',
					borderWidth: 1
				},
				{
					label: `차이`,
					data: diffPrices,
					backgroundColor: 'rgba(255, 0, 0, 0.7)',
					borderColor: 'rgba(234, 227, 161)',
					borderWidth: 1
				},
			]
		};
	}

	// 새로운 정보 조회 (게시글, 매물, 자동 완성)
	function newFetchData({ url, dataFunction }) {
		$.ajax({
			url: url,
			method: 'GET',
			contentType: 'application/json',
			success: function(data) {
				dataFunction(data);
			},
			error: function(error) {
				console.error(`Error fetching ${url}:`, error);
			}
		});
	}

	// 새로운 커뮤니티 데이터 처리 함수
	function newCommunityData(data) {
		const contentDiv = $('#new-post');
		data.forEach(item => {
			const postLink = $('<a></a>').attr('href', `/communityCard/${item.postId}`).addClass('post-link');
			const postTitle = $('<span></span>').addClass('post-title').text(item.title);
			const postCreatedAt = $('<span></span>').addClass('post-create-date').text(item.createdAt);

			postLink.append(postTitle).append(postCreatedAt);
			contentDiv.append(postLink);
		});
	}

	// 새로운 매물 데이터 처리 함수
	function newRealestateData(data) {
		var count = 1;
		data.forEach(item => {
			const salePrice = item.salePrice + "억"; // 가격 정보
			const info = `${item.floor}층, ${item.buildingName}동, ${item.exclusiveArea}㎡`;

			const buildingInfo = `
                <div class="item" style="--position: ${count}">
                    <div class="building">
                        <div class="building-header">
                            <div class="building-label">${item.buildingTypeName}</div>
                            <div class="building-name">${item.complexName}</div>
                        </div>	
                        
                        <div class="building-info">
                            <div>
                                <span>${item.cityName}</span>
                                <span>${item.districtName}</span>
                                <span>${item.neighborhoodName}</span>
                            </div>
                            <div>
                                <span>번지: ${item.address}</span>
                                <span>/ 도로명: ${item.addressStreet}</span>
                            </div>
                        </div>
                        
                        <div class="building-sale">
                            <div>
                                <div class="lately-price">${salePrice}</div>
                                <div class="lately-info">${info}</div>
                            </div>
                        </div>
                                     
                    </div>
                </div>
            `;
			document.querySelector('.estate-list').insertAdjacentHTML('beforeend', buildingInfo);

			count++;
		});
	}

	/* 자동 완성 데이터 */
	function autoSearchData(data) {
		data.forEach(item => {
			dataList.push(item.name);
		});
		// 데이터 로컬스토리지에 저장
		localStorage.setItem('autoCompleteData', JSON.stringify(dataList));
		localStorage.setItem('autoCompleteDataTimestamp', Date.now());
	}

	// 차트 통계 조회
	chartApiEndpoints.forEach(endpoint => chartFetchData(endpoint));

	// 새로운 정보 조회 (게시글, 매물)
	newInfoApiEndpoints.forEach(endpoint => newFetchData(endpoint));
});


/* 이미지 슬라이드 5초 간격으로 변경 */
var counter = 1;
var interval = setInterval(changeSlide, 10000);

function changeSlide() {
	counter++;
	if (counter > 2) {
		counter = 1;
	}
	document.getElementById('radio' + counter).checked = true;
}

function resetInterval() {
	clearInterval(interval);
	interval = setInterval(changeSlide, 10000);
}

document.addEventListener('DOMContentLoaded', function() {
	document.querySelectorAll('.manual-btn').forEach(function(button) {
		button.addEventListener('click', function() {
			counter = this.getAttribute('value');
			resetInterval();
		});
	});
});


/* 자동 완성 기능 */
document.addEventListener("DOMContentLoaded", () => {
	const $search = document.querySelector(".search-box");
	const $autoComplete = document.querySelector(".auto-complete");

	let nowIndex = 0;

	$search.onkeyup = (event) => {
		// 검색어
		const value = $search.value.trim();

		// 검색어를 공백으로 분할
		const searchWords = value.split(" ");

		// 자동완성 필터링
		let matchDataList = value
				? dataList.filter((label) =>
						searchWords.every(word => label.includes(word))
				)
				: [];

		matchDataList = matchDataList.slice(0, 5);

		switch (event.keyCode) {
				// UP KEY
			case 38:
				nowIndex = Math.max(nowIndex - 1, 0);
				break;

				// DOWN KEY
			case 40:
				nowIndex = Math.min(nowIndex + 1, matchDataList.length - 1);
				break;

				// ENTER KEY
			case 13:
				document.querySelector(".search-box").value = matchDataList[nowIndex] || "";

				// 초기화
				nowIndex = 0;
				matchDataList.length = 0;
				break;

				// 그외 다시 초기화
			default:
				nowIndex = 0;
				break;
		}

		// 리스트 보여주기
		showList(matchDataList, value, nowIndex);
	};

	const showList = (data, value, nowIndex) => {
		// 정규식으로 변환
		const regex = new RegExp(`(${value})`, "g");

		$autoComplete.innerHTML = data
		.map(
				(label, index) => `
                    <div class='${nowIndex === index ? "active" : ""}'>
                        ${label.replace(regex, "<mark>$1</mark>")}
                    </div>
                `
		)
		.join("");

		// 각 자동완성 항목에 클릭 및 마우스 오버 이벤트 리스너 추가
		document.querySelectorAll(".auto-complete > div").forEach((item, index) => {
			item.addEventListener("click", () => {
				$search.value = data[index];
				// 초기화
				nowIndex = 0;
				$autoComplete.innerHTML = '';
			});

			item.addEventListener("mouseover", () => {
				document.querySelectorAll(".auto-complete > div").forEach(div => {
					div.classList.remove("hover");
					div.classList.remove("active");
				});
				item.classList.add("hover");
				nowIndex = index;
			});
		});
	};
});