const chart1 = document.getElementById("chart-1").getContext("2d");
const myChart = new Chart(chart1, {
  type: "polarArea",
  data: {
    labels: ["뭘봐", "졸려", "짜증나"],
    datasets: [
      {
        label: "# of Votes",
        data: [600, 800, 1000],
        backgroundColor: [
          "rgba(54, 162, 235, 1)",
          "rgba(255, 99, 132, 1)",
          "rgba(255, 206, 86, 1)",
        ],
      },
    ],
  },
  options: {
    responsive: true,
  },
});

const chart2 = document.getElementById("chart-2").getContext("2d");
const myChart2 = new Chart(chart2, {
  type: "bar",
  data: {
    labels: ["뭘봐", "졸려", "짜증나"],
    datasets: [
      {
        label: "그래프",
        data: [600, 800, 1000],
        backgroundColor: [
          "rgba(54, 162, 235, 1)",
          "rgba(255, 99, 132, 1)",
          "rgba(255, 206, 86, 1)",
        ],
      },
    ],
  },
  options: {
    responsive: true,
  },
});
