const ctx = document.getElementById('graph').getContext('2d');

//  проверка на галочку?
// document
//   .getElementById("calculate")
//   .addEventListener("click", function () {
//
//   }

const myChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн'],
    datasets: [
      {
        label: '',
        data: [12, 19, 3, 5, 2, 3],
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1,
      },
    ],
  },
  options: {
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true,
          },
        },
      ],
    },
  },
});

function getData(xvalues, yvalues) {
  let arr = [];
  for (let i = 0; i < xvalues.length; i++) {
    arr.push({x:xvalues[i], y: yvalues[i]})
  }
  return arr;
}