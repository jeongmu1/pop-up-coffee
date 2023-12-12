const DATA_COUNT = 5;

const Utils = {
    // Generate an array of random numbers
    numbers: function(config) {
        let cfg = config || {};
        let min = cfg.min || 0;
        let max = cfg.max || 1;
        let count = cfg.count || 8;
        let decimals = cfg.decimals || 8;
        let continuity = cfg.continuity || 1;
        let dfactor = Math.pow(10, decimals) || 0;
        let data = [];
        let i, value;

        for (i = 0; i < count; ++i) {
            value = (Math.random() * (max - min)) + min;
            if (Math.random() < continuity) {
                data.push(Math.round(dfactor * value) / dfactor);
            } else {
                data.push(null);
            }
        }

        return data;
    }
};

function generateData() {
    return Utils.numbers({
        count: DATA_COUNT,
        min: -100,
        max: 100
    });
}

const data = {
    datasets: [{
        data: generateData()
    }]
};

var myChart = new Chart(document.getElementById('myChart').getContext('2d'), {
    type: 'doughnut',
    data: data,
    options: {
        cutout: '50%'
    }
});

const actions = [
    {
        name: 'Randomize',
        handler(chart) {
            chart.data.datasets.forEach(dataset => {
                dataset.data = generateData();
            });
            chart.update();
        }
    },
    {
        name: 'Toggle Doughnut View',
        handler(chart) {
            if (chart.options.cutout) {
                chart.options.cutout = 0;
            } else {
                chart.options.cutout = '50%';
            }
            chart.update();
        }
    }
];