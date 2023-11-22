const ctx = document.getElementById('graph').getContext('2d');
let myChart = new Chart(ctx, null);

function printGraph(minX, maxX, xvalues, yvalues) {
  const field = document.getElementById("expression");
  myChart.clear();
  let data = getData(xvalues, yvalues);
  myChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: xvalues,
      datasets: [{
        label: field.value,
        data: yvalues,
        backgroundColor: 'rgba(4,170,109,0.16)',
        borderColor: 'rgb(4,170,109)',
        borderWidth: 1,
      },
      ],
    },
    options: {
      legend: {
        display: false
      },
      plugins: {
        tooltip: {
          enabled: false
        }
      },
      scales: {
        yAxes: [
          {
            ticks: {
              beginAtZero: false,
            },
          },
        ],
      },
    },
  });
}

function getData(xvalues, yvalues) {
  let arr = [];
  for (let i = 0; i < xvalues.length; i++) {
    arr.push({x:xvalues[i], y:yvalues[i]})
  }
  return arr;
}