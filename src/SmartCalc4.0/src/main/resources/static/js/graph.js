const ctx = document.getElementById('graph').getContext('2d');
let myChart = new Chart(ctx, null);

function printGraph(minX, maxX, xvalues, yvalues) {
  const field = document.getElementById("expression");
  myChart.destroy();
  myChart = new Chart(ctx, {
    type: 'line',
    data: {
      labels: xvalues,
      datasets: [{
        label: '',
        data: yvalues,
        backgroundColor: 'rgba(4,170,109,0.16)',
        borderColor: 'rgb(4,170,109)',
        borderWidth: 1,
      }],
    },
    options: {
      legend: {
        display: false
      }
    }
  });
}