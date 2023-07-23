import React, {useContext, useMemo} from 'react';
import '../App.css';
import {Container, Paper, Typography} from "@mui/material";
import {InsightsContext} from "./InsightsProvider";
import {BarElement, CategoryScale, Chart as ChartJS, LinearScale} from "chart.js";
import {Bar} from "react-chartjs-2";
import {blue} from "@mui/material/colors";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement
);

function createData(
  date: string,
  cyclesCompleted: number
) {
  return {date, cyclesCompleted};
}

const options = {
  indexAxis: 'x' as 'x',
  // Elements options apply to all of the options unless overridden in a dataset
  // In this case, we are setting the border of each horizontal bar to be 2px wide
  elements: {
    bar: {
      borderWidth: 2,
    },
    line: {
      tension: 0.1
    }
  },
  responsive: true,
  plugins: {
    legend: {
      display: true
    }
  }
};

const InsightsView = () => {

  const {insights} = useContext(InsightsContext);

  const rows = insights.cycleHistory.map(item => createData(item.date, item.cyclesCompleted));

  const accountBalanceData = useMemo(() => {
    const sortedHistory = insights.cycleHistory.sort((a, b) => { return a.date.localeCompare(b.date) })

    return {
      labels: sortedHistory.map(item => item.date),
      datasets: [
        {
          label: 'Cycles Completed',
          data: sortedHistory.map(item => item.cyclesCompleted),
          backgroundColor: blue[300],
          borderWidth: 1
        }
      ]
    };
  }, [insights.cycleHistory]);

  return (
    <>
      <Container component={Paper}>
        <Typography variant="body1" color="text.primary">
          There were a total of {insights.totalCycles} cycles over {insights.cycleHistory.length} days, an average of {insights.averageCycles.toFixed(3)} cycles per day.
        </Typography>
        <Bar data={accountBalanceData} options={options}/>

      </Container>
    </>
  );
}

export default InsightsView;
