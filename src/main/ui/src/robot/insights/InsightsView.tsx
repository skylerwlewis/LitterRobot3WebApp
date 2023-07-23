import React, {useContext, useMemo} from 'react';
import {Alert, Button, ButtonGroup, CircularProgress, Container, Paper, Typography} from "@mui/material";
import {InsightsContext} from "./InsightsProvider";
import {BarElement, CategoryScale, Chart as ChartJS, LinearScale} from "chart.js";
import {Bar} from "react-chartjs-2";
import {blue} from "@mui/material/colors";
import {useNavigate, useParams} from "react-router-dom";

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

  const { robotId } = useParams();
  const navigate = useNavigate();

  const {insights, insightsLoading, insightsError} = useContext(InsightsContext);

  const accountBalanceData = useMemo(() => {
    const sortedHistory = insights ? insights.cycleHistory.sort((a, b) => {
      return a.date.localeCompare(b.date)
    }) : [];

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
  }, [insights]);

  return (
    <>
      <Container>
        <ButtonGroup variant="contained">
          <Button onClick={() => {
            navigate(`/robot/${robotId}`)
          }}>Details</Button>
          <Button onClick={() => {
            navigate(`/robot/${robotId}/activity`)
          }}>Activity</Button>
          <Button disabled>Insights</Button>
          <Button onClick={() => {
            navigate(`/robot/${robotId}/settings`)
          }}>Settings</Button>
        </ButtonGroup>
      </Container>
      {insights || accountBalanceData.labels.length > 0 ?
        <Container component={Paper}>
          {insights ?
            <Typography variant="body1" color="text.primary">
              There were a total of {insights.totalCycles} cycles over {insights.cycleHistory.length} days, an average
              of {insights.averageCycles.toFixed(3)} cycles per day.
            </Typography>
            : null}
          {accountBalanceData.labels.length > 0 ?
            <Bar data={accountBalanceData} options={options}/>
            : null
          }
        </Container>
        :
        insightsLoading ?
          <CircularProgress/>
          :
          insightsError ?
            <Alert severity="error">There was a problem retrieving the insights data.</Alert>
            : null
      }
    </>
  );
}

export default InsightsView;
