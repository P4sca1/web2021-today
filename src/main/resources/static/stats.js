const statisticsRequest = new XMLHttpRequest()
statisticsRequest.open("GET", "/api/v1/statistics", true)
statisticsRequest.onreadystatechange = function() {
  if (statisticsRequest.readyState === XMLHttpRequest.DONE) {
    const status = statisticsRequest.status
    if (status === 200) {
      const statistics = JSON.parse(statisticsRequest.responseText)
      console.dir(statistics)

      const ctx = document.getElementById('chart').getContext('2d');
      const chart = new Chart(ctx, {
        type: 'line',
        data: {
          datasets: [{
            label: 'Sonnenstunden',
            backgroundColor: '#fa7705',
            borderColor: '#ffec14',
            data: statistics.map(stat => ({ x: new Date(stat.date), y: stat.sunshineDuration })),
          }],
        },
        options: {
          scales: {
            x: {
              type: 'time',
              time: {
                unit: 'day',
                displayFormats: {
                  day: 'dd.MM.yyyy'
                },
                tooltipFormat: 'dd.MM.yyyy',
              },
            },
            y: {
              beginAtZero: true,
            },
          }
        }
      });
    } else {
      alert("Es gab einen Fehler beim Laden der Statistiken")
    }
  }
}
statisticsRequest.send()
