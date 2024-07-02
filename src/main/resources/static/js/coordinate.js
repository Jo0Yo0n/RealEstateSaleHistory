document.addEventListener("DOMContentLoaded", () => {

	let dataList = [];
	// 캐시 유효 기간: 24시간
	const CACHE_EXPIRY_TIME = 24 * 60 * 60 * 1000;

	// 캐시된 데이터 확인
	const cachedData = localStorage.getItem('autoCompleteData');
	const cacheTimestamp = localStorage.getItem('autoCompleteDataTimestamp');
	const now = Date.now();

	if (cachedData && cacheTimestamp && (now - cacheTimestamp < CACHE_EXPIRY_TIME)) {
		dataList = JSON.parse(cachedData);
	} else {
		// 데이터가 캐시되지 않았거나 유효 기간이 지난 경우 서버에서 가져옴*/
		newFetchData({ url: "/admindivison/auto/search", dataFunction: autoSearchData });
	}

	// 자동 완성 데이터
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

	/* 자동 완성 데이터 */
	function autoSearchData(data) {
		data.forEach(item => {
			dataList.push(item);
		});
		// 데이터 로컬스토리지에 저장
		localStorage.setItem('autoCompleteData', JSON.stringify(dataList));
		localStorage.setItem('autoCompleteDataTimestamp', Date.now());
	}



	const urlParams = new URLSearchParams(window.location.search);
	const lo = urlParams.get('lo');
	const ms = urlParams.get('ms');


	if (lo !== null) {
		// 페이지 이동시 입력값
		document.querySelector('.search-box').value = lo;
	}


	// 경도 위도가 없을 경우
	if (ms !== "" && ms !== null) {

		const [lat, lng] = ms.split(',');
		mapCoordinate(`${lat}`, `${lng}`);
	} else if (lo !== "" && lo !== null) {

		geoCoder(document.querySelector(".search-box").value);
	}


	// 좌표 검색
	function mapCoordinate(lat, lng) {

		map.setZoom(18);
		map.panTo(new naver.maps.LatLng(lat, lng));
	}


	// GeoCoder Api 위치 찾기
	function geoCoder(address) {

		$.ajax({
			url: "https://api.vworld.kr/req/address?",
			type: "GET",
			dataType: "jsonp",
			data: {
				service: "address",
				request: "GetCoord",
				version: "2.0",
				crs: "EPSG:4326",
				type: "ROAD",
				address: address,
				format: "json",
				errorformat: "json",
				key: "DADFF505-6B19-3A9B-9BC8-BCF702ABA59C"
			},
			success: function(result) {

				const lat = result.response.result.point.y;
				const lng = result.response.result.point.x;

				mapCoordinate(lat, lng);
				updateUrlWithoutReload(address, lat, lng);

			}
		});
	}


	function updateUrlWithoutReload(lo, lat, lng) {

		const urlChange = new URL(window.location);
		urlChange.searchParams.set('lo', lo || '');
		urlChange.searchParams.set('ms', lat && lng ? `${lat},${lng}` : '');

		history.replaceState(null, '', decodeURIComponent(urlChange.toString()));
	}



	/* 자동 완성 기능 */
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
			? dataList.map(item => item.name).filter((label) =>
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


	const searchBox = document.querySelector('.search-box');
	const searchEnter = document.querySelector('.search-enter');
	const autoComplete = document.querySelector('.auto-complete');

	// 엔터 키 눌렀을 때 이벤트
	searchBox.addEventListener('keypress', function(event) {
		if (event.key === 'Enter' && !autoComplete.innerHTML.trim()) {
			performSearch();
		}
	});


	// search-enter 클릭 시 이벤트
	searchEnter.addEventListener('click', function() {
		if (!autoComplete.innerHTML.trim()) {
			performSearch();
		}
	});


	// 검색 실행 함수
	function performSearch() {
		const searchContext = searchBox.value.trim();
		if (searchContext) {
			const matchedItems = dataList.filter(item => item.name === searchContext);

			matchedItems.forEach(item => {

				if (item.lng === null || item.lat === null) {
					geoCoder(document.querySelector(".search-box").value);
				} else {
					mapCoordinate(item.lat, item.lng);
				}
			});
		}
	}
});
