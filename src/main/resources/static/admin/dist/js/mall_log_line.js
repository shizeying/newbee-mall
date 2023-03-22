$(function () {
	// 异步请求接口数据
	$.ajax({
		type: 'GET', url: '/mall_log/bar', success: function (result) {
			// 填充数据到echarts中
			var myChart = echarts.init(document.getElementById('chart'), 'dark', {
				useDirtyRect: true
			});
			myChart.setOption({
				title: {
					text: result.title
				},
				tooltip: {},
				xAxis: {
					type: 'category',
					data: result.xaxis
				},
				yAxis: {
					type: 'value'
				},
				series: [{
					data: result.series,
					type: 'bar',
					showBackground: true
				}]
			});
		}
	});
});
$(function () {
	// 异步请求接口数据
	$.ajax({
		type: 'GET', url: '/mall_log/line', success: function (result) {
			// 填充数据到echarts中
			var myChart = echarts.init(document.getElementById('main'),'dark', 'dark', {
				useDirtyRect: true
			});
			myChart.setOption({
				title: {
					text: result.title
				},
				tooltip: {},
				xAxis: {
					type: 'category',
					data: result.xaxis
				},
				yAxis: {
					type: 'value'
				},
				series: [{
					data: result.series,
					type: 'line',
					smooth: true
				}]
			});
		}
	});
});
$(function () {
	// 异步请求接口数据
	$.ajax({
		type: 'GET', url: '/mall_log/pie', success: function (result) {
			// 填充数据到echarts中
			var myChart = echarts.init(document.getElementById('pie'), 'dark', {
				useDirtyRect: true
			});
			myChart.setOption({
				title: {
					text: result.title
				},
				tooltip: {
					trigger: 'item'
				},
				legend: {
					orient: 'Video',
					left: 'right'
				},
				series: [
    {
      name: '结果',
      type: 'pie',
	    radius: ['40%', '70%'],
	    avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      labelLine: {
        show: false
      },
	    // data: result.xaxis
      data: result.result,
	    emphasis: {
		    itemStyle: {
			    shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)'
		    }
	    }
    }]
			
			});
		}
	});
});