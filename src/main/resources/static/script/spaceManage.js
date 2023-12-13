am5.ready(function () {
    // Create root element
    // https://www.amcharts.com/docs/v5/getting-started/#Root_element
    var root = am5.Root.new("chartdiv");
    root.dateFormatter.setAll({
        dateFormat: "yyyy-MM-dd",
        dateFields: ["valueX", "openValueX"]
    });

    // Set themes
    // https://www.amcharts.com/docs/v5/concepts/themes/
    root.setThemes([
        am5themes_Animated.new(root)
    ]);

    // Create chart
    // https://www.amcharts.com/docs/v5/charts/xy-chart/
    var chart = root.container.children.push(am5xy.XYChart.new(root, {
        panX: false,
        panY: false,
        wheelX: "panX",
        wheelY: "zoomX",
        paddingLeft: 0,
        layout: root.verticalLayout
    }));

    // Add legend
    // https://www.amcharts.com/docs/v5/charts/xy-chart/legend-xy-series/
    var legend = chart.children.push(am5.Legend.new(root, {
        centerX: am5.p50,
        x: am5.p50
    }));

    var colors = chart.get("colors");

    // Data
    let data = [];
    for (let i = 0; i < infos.length; i++) {
        for (let j = 0; j < infos[i].reservations.length; j++) {
            // 주석 추가: 데이터의 날짜를 getTime() 메서드를 사용하여 변환
            let object = {
                category: infos[i].space,
                start: new Date(infos[i].reservations[j].startDate).getTime(),
                end: new Date(infos[i].reservations[j].endDate).getTime(),
                task: infos[i].reservations[j].merchantName,
                id: infos[i].reservations[j].id,
                forms : infos[i].reservations[j].fromFlexible,
            };
            data.push(object);
        }
    }
    // Create axes
    // https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
    var yRenderer = am5xy.AxisRendererY.new(root, {
        minorGridEnabled: true
    });
    yRenderer.grid.template.set("location", 1);

    var yAxis = chart.yAxes.push(
        am5xy.CategoryAxis.new(root, {
            categoryField: "category",
            renderer: yRenderer,
            tooltip: am5.Tooltip.new(root, {})
        })
    );

    var categories = [];
    // 데이터 배열 순회
    for (var i = 0; i < spaces.length; i++) {
        // 주석 추가: 각 데이터에서 number 값을 추출하여 카테고리 배열에 추가
        categories.push({
            category: spaces[i].number
        });
    }

    yAxis.data.setAll(categories);

    var xAxis = chart.xAxes.push(
        am5xy.DateAxis.new(root, {
            baseInterval: { timeUnit: "minute", count: 1 },
            renderer: am5xy.AxisRendererX.new(root, {
                strokeOpacity: 0.1,
                minorGridEnabled: true,
                minGridDistance: 80
            })
        })
    );

    // Add series
    // https://www.amcharts.com/docs/v5/charts/xy-chart/series/
    var series = chart.series.push(am5xy.ColumnSeries.new(root, {
        xAxis: xAxis,
        yAxis: yAxis,
        openValueXField: "start",
        valueXField: "end",
        categoryYField: "category",
        sequencedInterpolation: true
    }));


    series.columns.template.setAll({
        templateField: "columnSettings",
        strokeOpacity: 0,
        tooltipText: "{task}:\n[bold]{openValueX}[/] - [bold]{valueX}[/]"
    });

    series.columns.template.events.on("click", function (ev) {
        let id = ev.target.dataItem.dataContext.id;
        let startDate = new Date(ev.target.dataItem.dataContext.start);
        let endDate = new Date(ev.target.dataItem.dataContext.end);
        let spaceNum = ev.target.dataItem.dataContext.category;
        let forms = ev.target.dataItem.dataContext.forms;
        console.log(forms);

        console.log(forms);
        let formattedStartDate = formatDate(startDate);
        let formattedEndDate = formatDate(endDate);

        // 폼 필드에 값을 설정
        document.getElementById('company-number').value = id;
        document.getElementById('start-date').value = formattedStartDate;
        document.getElementById('end-date').value = formattedEndDate;
        // document.getElementById('spaceNumber').value = spaceNum;
        // document.getElementById('fromFlex').value = Boolean(forms);
        document.getElementById('hidden_company-number').value = id;
        document.getElementById('hidden_fromFlex').value = Boolean(forms);
        // document.getElementById('hidden-from-flexible').value = Boolean(forms);
        document.getElementById('hidden-from-flexible').value = Boolean(forms);
        console.log(document.getElementById('hidden-from-flexible').value);
    });

    function formatDate(date) {
        var year = date.getFullYear();
        var month = ('0' + (date.getMonth() + 1)).slice(-2);
        var day = ('0' + date.getDate()).slice(-2);

        return year + '-' + month + '-' + day;
    }
    const table = document.getElementById('flexibleMerchant');
    const rows = table.getElementsByTagName('tr');

    Array.from(rows).forEach((row,index) => {
        row.addEventListener('click',() => {
            const cells = row.getElementsByTagName('td');
            const companyName = cells[0].innerText;

            document.getElementById('flexible-company-number').value = companyName;
            document.getElementById('hidden-from-flexible').value = Boolean(forms);
        });
    });

    series.data.setAll(data);

    // Add scrollbars
    chart.set("scrollbarX", am5.Scrollbar.new(root, { orientation: "horizontal" }));

    // Make stuff animate on load
    // https://www.amcharts.com/docs/v5/concepts/animations/
    series.appear();
    chart.appear(1000, 100);

}); // end am5.ready()
