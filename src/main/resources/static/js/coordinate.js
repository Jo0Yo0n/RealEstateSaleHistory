document.addEventListener("DOMContentLoaded", () => {
	
	
	// 시작 위치 - 서울 시청
	var map = new naver.maps.Map("map", {
		zoom: 11,
		center: new naver.maps.LatLng(37.5664056, 126.9778222),
		zoomControl: true,
		zoomControlOptions: {
			position: naver.maps.Position.TOP_RIGHT,
			style: naver.maps.ZoomControlStyle.SMALL,
		},
	});
	

	let dataList = [];
	// 캐시 유효 기간: 24시간
	const CACHE_EXPIRY_TIME = 24 * 60 * 60 * 1000;

	// 캐시된 데이터 확인
	const cachedData = localStorage.getItem('autoCompleteData');
	const cacheTimestamp = localStorage.getItem('autoCompleteDataTimestamp');
	const now = Date.now();

	const urlParams = new URLSearchParams(window.location.search);
	const lo = urlParams.get('lo');
	const ms = urlParams.get('ms');

	if (cachedData && cacheTimestamp && (now - cacheTimestamp < CACHE_EXPIRY_TIME)) {

		dataList = JSON.parse(cachedData);
		markerCoordinates()
		setTimeout(() => { changeUrlCoordinate() }, 100);
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
		markerCoordinates();
		setTimeout(() => { changeUrlCoordinate() }, 100);
	}


	// URL 확인 후 URL 변경 밑 좌표 검색
	function changeUrlCoordinate() {

		if (lo !== null) {
			// 페이지 이동시 입력값
			document.querySelector('.search-box').value = lo;
		}

		// 경도 위도가 있는경우
		if (ms !== "" && ms !== null) {

			const [lat, lng] = ms.split(',');
			updateUrlWithoutReload(lo, `${lat}`, `${lng}`);
			mapCoordinate(`${lat}`, `${lng}`);

		} else if (lo !== "" && lo !== null) {

			geoCoder(document.querySelector(".search-box").value);
		}
	}

	// 좌표 검색
	function mapCoordinate(lat, lng) {

		map.setZoom(18);
		map.panTo(new naver.maps.LatLng(lat, lng));

		// 검색된 마커 찾기
		const markerId = `${lat}-${lng}`;
		const markerElement = document.getElementById(markerId);

		if (markerElement) {
			// 기존 스타일 저장
			markerElement.classList.add('searched-marker');

			// 일정 시간 후 또는 특정 이벤트 후 원래대로 돌아가도록 설정
			setTimeout(() => {
				markerElement.classList.remove('searched-marker');
			}, 10000); // 10초 후 원래대로 돌아가도록 설정
		}
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


	// URL 맞춰주기 
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
					updateUrlWithoutReload(item.name, item.lat, item.lng);
				}
			});
		}
	}

	// 마커 설정
	function markerCoordinates() {

		// 좌표 데이터
		var markers = [];

		for (var i = 0, ii = dataList.length; i < ii; i++) {
			var spot = dataList[i];

			if (spot.seq === 3 && spot.grd_la !== null && spot.grd_lo !== null) {

				var latlng = new naver.maps.LatLng(spot.lat, spot.lng);
				var apt = spot.name;
				var price = spot.salePrice;
				var estateId = spot.realEstateId;

				// 가격에 따라 클래스 결정
				var priceClass;
				if (price < 5) {
					priceClass = 'price-default';
				} else if (price < 10) {
					priceClass = 'price-5';
				} else if (price < 15) {
					priceClass = 'price-10';
				} else if (price < 20) {
					priceClass = 'price-15';
				} else {
					priceClass = 'price-20';
				}

				var marker = new naver.maps.Marker({
					position: latlng,
					draggable: false,
					clickable: true,
					icon: {
						content: [`<div id="${spot.lat}-${spot.lng}" class="marker-container">
              						<div class="marker-name">${apt}</div>
              						<div class="marker-price ${priceClass}">${price}억</div>
              						<div class="disabled" style="display:none;">${estateId}</div>
              		 		   </div>`].join(''),
						size: new naver.maps.Size(40, 40),
						anchor: new naver.maps.Point(20, 20)
					}
				});

				markers.push(marker);

				// 마커 클릭 이벤트 핸들러 추가
				naver.maps.Event.addListener(marker, 'click', function(e) {
					var clickedMarker = e.overlay;
					var clickedApt = $(clickedMarker.getIcon().content).find('.marker-name').text();
					var clickedId = $(clickedMarker.getIcon().content).find('.disabled').text();
					getFilteredEstateCount();


					/*					// 메뉴 내용을 클릭된 마커 정보로 업데이트 (clickedId를 사용하도록 수정)
										$('#apt-info').html(
											`<p>Clicked Marker Information</p><p>Apt: ${clickedApt}</p><p>Real Estate ID: ${clickedId}</p>`);
					
										// 모든 메뉴를 닫고 apt-info 메뉴를 열기
										$('.menu').removeClass('active');
										$('#apt-info').addClass('active');
					
										// 닫기 버튼 추가
										var closeBtn = $('#close-btn-template').html();
										$('#apt-info').prepend(closeBtn);
					
										// 닫기 버튼에 클릭 이벤트 추가
										$('.close-menu-img').click(function() {
											$('#apt-info').removeClass('active');
											$(this).remove(); // 닫기 버튼 제거
										});*/
				});
			}
		}

		// 마커 클러스터링 html
		var htmlMarker1 = {
			content: `<div style="cursor:pointer;width:50px;height:50px;line-height:50px;font-size:10px;color:white;text-align:center;font-weight:bold;background-image:url(${clusterMarkerImagePath}cluster-marker-1.png);background-size:contain;"></div>`,
			size: N.Size(50, 50),
			anchor: N.Point(25, 25)
		}, htmlMarker2 = {
			content: `<div style="cursor:pointer;width:50px;height:50px;line-height:50px;font-size:10px;color:white;text-align:center;font-weight:bold;background-image:url(${clusterMarkerImagePath}cluster-marker-2.png);background-size:contain;"></div>`,
			size: N.Size(50, 50),
			anchor: N.Point(25, 25)
		}, htmlMarker3 = {
			content: `<div style="cursor:pointer;width:50px;height:50px;line-height:50px;font-size:10px;color:white;text-align:center;font-weight:bold;background-image:url(${clusterMarkerImagePath}cluster-marker-3.png);background-size:contain;"></div>`,
			size: N.Size(50, 50),
			anchor: N.Point(25, 25)
		}, htmlMarker4 = {
			content: `<div style="cursor:pointer;width:50px;height:50px;line-height:50px;font-size:10px;color:white;text-align:center;font-weight:bold;background-image:url(${clusterMarkerImagePath}cluster-marker-4.png);background-size:contain;"></div>`,
			size: N.Size(50, 50),
			anchor: N.Point(25, 25)
		}, htmlMarker5 = {
			content: `<div style="cursor:pointer;width:50px;height:50px;line-height:50px;font-size:10px;color:white;text-align:center;font-weight:bold;background-image:url(${clusterMarkerImagePath}cluster-marker-5.png);background-size:contain;"></div>`,
			size: N.Size(50, 50),
			anchor: N.Point(25, 25)
		};

		// 클러스터링 함수 -> markerClustering.js
		var markerClustering = new MarkerClustering({
			minClusterSize: 10,
			maxZoom: 18,
			map: map,
			markers: markers,
			disableClickZoom: false,
			gridSize: 250,
			icons: [htmlMarker1, htmlMarker2, htmlMarker3, htmlMarker4,
				htmlMarker5],
			indexGenerator: [50, 100, 200, 500, 1000],
			stylingFunction: function(clusterMarker, count) {
				$(clusterMarker.getElement()).find("div:first-child").text(count);
			}
		});
	}
});
