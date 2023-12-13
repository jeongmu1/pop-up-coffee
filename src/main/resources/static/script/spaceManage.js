am5.ready(function () {
    // Create root element
    var root = am5.Root.new("chartdiv");
    root.dateFormatter.setAll({
        dateFormat: "yyyy-MM-dd",
        dateFields: ["valueX", "openValueX"]
    });

    // Set themes
    root.setThemes([
        am5themes_Animated.new(root)
    ]);

    // Create chart
    var chart = root.container.children.push(am5xy.XYChart.new(root, {
        panX: false,
        panY: false,
        wheelX: "panX",
        wheelY: "zoomX",
        paddingLeft: 0,
        layout: root.verticalLayout
    }));

    // Add legend
    var legend = chart.children.push(am5.Legend.new(root, {
        centerX: am5.p50,
        x: am5.p50
    }));

    var colors = chart.get("colors");

    // Data
    let data = [];
    for (let i = 0; i < infos.length; i++) {
        for (let j = 0; j < infos[i].reservations.length; j++) {
            const flexibleColor = "#70d4ff";
            const fixedColor = "#9eff62";
            let color = "";

            if (infos[i].reservations[j].fromFlexible) {
                color = flexibleColor;
            } else {
                color = fixedColor;
            }
            let object = {
                category: infos[i].space,
                start: new Date(infos[i].reservations[j].startDate).getTime(),
                end: new Date(infos[i].reservations[j].endDate).getTime(),
                task: infos[i].reservations[j].merchantName,
                id: infos[i].reservations[j].id,
                forms: infos[i].reservations[j].fromFlexible,
                picName: infos[i].reservations[j].contactManager,
                picPhone: infos[i].reservations[j].contactPhone,
                business : infos[i].reservations[j].businessType,
                stat : infos[i].reservations[j].status,
                columnSettings: {
                    fill: am5.color(color)
                }
            };
            data.push(object);
        }
    }
    console.log(data)

    // Create axes
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
    for (var i = 0; i < spaces.length; i++) {
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
        tooltipText: "업체명 : [bold]{task}:\n 대여 날짜 : [bold]{openValueX}[/] - [bold]{valueX}[/] \n 담당자명 : [bold]{picName} \n 담당자 전화번호 : [bold]{picPhone}\n 업종 : [bold]{business}\n 상태 : [bold]{stat}\n"
    });

    series.columns.template.events.on("click", function (ev) {
        let id = ev.target.dataItem.dataContext.id;
        let startDate = new Date(ev.target.dataItem.dataContext.start);
        let endDate = new Date(ev.target.dataItem.dataContext.end);
        let spaceNum = ev.target.dataItem.dataContext.category;
        let forms = ev.target.dataItem.dataContext.forms;
        let staut = ev.target.dataItem.dataContext.stat;

        let formattedStartDate = formatDate(startDate);
        let formattedEndDate = formatDate(endDate);

        document.getElementById('company-number').value = id;
        document.getElementById('start-date').value = formattedStartDate;
        document.getElementById('end-date').value = formattedEndDate;
        document.getElementById('hidden_company-number').value = id;
        document.getElementById('hidden_fromFlex').value = Boolean(forms);
        document.getElementById('hidden-from-flexible').value = Boolean(forms);
        document.getElementById('hidden-status').value = staut;
        console.log(document.getElementById('hidden-status').value);
    });

    $(function () {
        $('#unAssign').click(function () {
            var hiddenStatus = document.getElementById('hidden-status').value;

            if (hiddenStatus === "확정") {
                var confirmed = confirm("확정된 예약을 삭제하면 복구할 수 없습니다. \n 정말로 삭제하시겠습니까?");
                if (!confirmed) {
                    return false;
                } else {
                    alert("정상적으로 삭제되었습니다.");
                }
            }
        });
    });


    function formatDate(date) {
        var year = date.getFullYear();
        var month = ('0' + (date.getMonth() + 1)).slice(-2);
        var day = ('0' + date.getDate()).slice(-2);

        return year + '-' + month + '-' + day;
    }

    const table = document.getElementById('flexibleMerchant');
    const rows = table.getElementsByTagName('tr');

    Array.from(rows).forEach((row, index) => {
        row.addEventListener('click', () => {
            const cells = row.getElementsByTagName('td');
            const companyName = cells[0].innerText;
            const flexStartDate = cells[4].innerText;
            const flexEndDate = cells[5].innerText;

            document.getElementById('flexible-company-number').value = companyName;
            document.getElementById('flex-start-date').value = flexStartDate;
            document.getElementById('flex-end-date').value = flexEndDate;


            document.getElementById('hidden-flexible-company-number').value = companyName;
            document.getElementById('hidden-flex-start-date').value = flexStartDate;
            document.getElementById('hidden-flex-end-date').value = flexEndDate;

            console.log(document.getElementById('flexible-company-number').value)
            console.log(document.getElementById('hidden-flexible-company-number').value)

        });
    });

    series.data.setAll(data);

    chart.set("scrollbarX", am5.Scrollbar.new(root, { orientation: "horizontal" }));

    series.appear();
    chart.appear(1000, 100);

});
