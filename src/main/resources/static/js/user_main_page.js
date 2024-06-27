$(document).ready(function() {
	// 통계 데이터 API 엔드포인트들
	const apiEndpoints = [
		"/statistics/districts/avg-sales-prices",
		"/statistics/neighborhoods/most-favorite",
		"/statistics/real-estates/changes/five-years?orderBy=desc",
		"/statistics/real-estates/changes/five-years?orderBy=asc"
	];

	// 각 API로부터 데이터를 가져오는 함수
	function fetchData(endpoint, chartId, chartType, functionName, datasetLabel) {
		$.ajax({
			url: endpoint,
			method: 'GET',
			contentType: 'application/json',

			success: function(data) {

				const { labels, datasets } = functionName(data, datasetLabel);

				console.log(data);

				renderChart(chartId, chartType, labels, datasets);

			},
			error: function(error) {
				// 오류가 발생하면 오류 메시지 표시
				$('#' + elementId).text(`통계 ${elementId.slice(-1)}: 데이터 가져오기 실패`);
				console.error(`Error fetching ${elementId}:`, error);
			}
		});
	}




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


	function avgSaleDistrict(data, datasetLabel) {
		const labels = data.map(item => item.districtName);
		const values = data.map(item => item.avgPrice);
		return {
			labels,
			datasets: [{
				label: datasetLabel,
				data: values,
				backgroundColor: 'rgba(75, 192, 192, 0.2)',
				borderColor: 'rgba(75, 192, 192, 1)',
				borderWidth: 1
			}]
		};
	}

	function mostFavoriteNeighborhood(data, datasetLabel) {
		const labels = data.map(item => item.neighborhoodName);
		const values = data.map(item => item.favoriteCnt);
		return {
			labels,
			datasets: [{
				label: datasetLabel,
				data: values,
				backgroundColor: 'rgba(153, 102, 255, 0.2)',
				borderColor: 'rgba(153, 102, 255, 1)',
				borderWidth: 1
			}]
		};
	}


	function realEstateChangeFiveYear(data, datasetLabel) {
		const labels = data.map(item => item.complexName);
		const maxPrices = data.map(item => item.maxPrice);
		const minPrices = data.map(item => item.minPrice);
		const diffPrices = data.map(item => item.diffPrice);
		return {
			labels,
			datasets: [
				{
					label: `${datasetLabel} - Min Price`,
					data: minPrices,
					backgroundColor: 'rgba(255, 206, 86, 0.2)',
					borderColor: 'rgba(255, 206, 86, 1)',
					borderWidth: 1
				},
				{
					label: `${datasetLabel} - Max Price`,
					data: maxPrices,
					backgroundColor: 'rgba(75, 192, 192, 0.2)',
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				},
				{
					label: `${datasetLabel} - Diff Price`,
					data: diffPrices,
					backgroundColor: 'rgba(54, 162, 235, 0.2)',
					borderColor: 'rgba(54, 162, 235, 1)',
					borderWidth: 1
				},
			]
		};
	}




	fetchData(apiEndpoints[0], 'chart1', 'bar', avgSaleDistrict, '각 지역구 기준으로 전체 매매 평균 순위');
	fetchData(apiEndpoints[1], 'chart2', 'bar', mostFavoriteNeighborhood, '즐겨찾기가 많이 된 동 순위');
	fetchData(apiEndpoints[2], 'chart3', 'bar', realEstateChangeFiveYear, '최근 5년간 최고 상승');
	fetchData(apiEndpoints[3], 'chart4', 'bar', realEstateChangeFiveYear, '최근 5년간 최저 상승');

});