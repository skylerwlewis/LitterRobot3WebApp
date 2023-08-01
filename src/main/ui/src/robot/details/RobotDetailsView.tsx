import React, {useContext, useMemo} from "react";
import {RobotContext} from "./RobotDetailsProvider";
import {
  Alert,
  Button,
  ButtonGroup,
  CircularProgress,
  Container,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow, Typography
} from "@mui/material";
import moment from "moment";
import {statusMap, timeFormatString} from "../../StatusMap";
import {useNavigate, useParams} from "react-router-dom";
import {ArcElement, Chart as ChartJS} from 'chart.js';
import {Pie} from 'react-chartjs-2';
import {green, grey, orange, red, yellow} from "@mui/material/colors";

ChartJS.register(ArcElement);

const createData = (
  name: string,
  value: string
) => {
  return {name, value};
}

const RobotDetailsView = () => {


  const {robotId} = useParams();
  const navigate = useNavigate();

  const {robot, robotLoading, robotError} = useContext(RobotContext);

  const rows = robot ? [
    createData('Litter Robot ID', robot.litterRobotId),
    createData('Serial Number', robot.litterRobotSerial),
    createData('Nickname', robot.litterRobotNickname),
    createData('Device Type', robot.deviceType),
    createData('Cycle Count', robot.cycleCount),
    createData('Total Cycle Count', robot.totalCycleCount),
    createData('Cycle Capacity', robot.cycleCapacity),
    createData('New Cycle Capacity', robot.newCycleCapacity),
    createData('Saved Cycle Capacity', robot.savedCycleCapacity),
    createData('Is DFI Triggered?', robot.isDFITriggered === "1" ? 'Yes' : 'No'),
    createData('Is DF1 Triggered?', robot.isDf1Triggered === "1" ? 'Yes' : 'No'),
    createData('Is DF2 Triggered?', robot.isDf2Triggered === "1" ? 'Yes' : 'No'),
    createData('Is DFS Triggered?', robot.isDfsTriggered === "1" ? 'Yes' : 'No'),
    createData('Is Manual Reset?', robot.isManualReset === "1" ? 'Yes' : 'No'),
    createData('Saved Is Manual Reset?', robot.savedIsManualReset === "1" ? 'Yes' : 'No'),
    createData('Previous DFI Triggered?', robot.previousDFITriggered === "1" ? 'Yes' : 'No'),
    createData('DFI Cycle Count', robot.DFICycleCount),
    createData('Saved Cycle Count', robot.savedCycleCount),
    createData('Clean Cycle Wait Time Minutes', robot.cleanCycleWaitTimeMinutes),
    createData('Cycles After Drawer Full', robot.cyclesAfterDrawerFull.toString()),
    createData('Night Light Active?', robot.nightLightActive === "1" ? 'Yes' : 'No'),
    createData('Panel Lock Active?', robot.panelLockActive === "1" ? 'Yes' : 'No'),
    createData('Sleep Mode Active?', robot.sleepModeActive === "1" ? 'Yes' : 'No'),
    createData('Sleep Mode Time', robot.sleepModeTime ? robot.sleepModeTime : 'N/A'),
    createData('Sleep Mode Start Time', robot.sleepModeStartTime === '0' ? 'N/A' : robot.sleepModeStartTime),
    createData('Sleep Mode End Time', robot.sleepModeEndTime === '0' ? 'N/A' : robot.sleepModeEndTime),
    createData('Power Status', robot.powerStatus),
    createData('Unit Status', statusMap[robot.unitStatus]),
    createData('Last Seen', moment(robot.lastSeen + 'Z').format(timeFormatString)),
    createData('Setup Date', moment(robot.setupDate + 'Z').format(timeFormatString)),
    createData('Is Onboarded?', robot.isOnboarded ? 'Yes' : 'No'),
    createData('Did Notify Offline?', robot.didNotifyOffline ? 'Yes' : 'No'),
    createData('Auto Offline Disabled?', robot.autoOfflineDisabled ? 'Yes' : 'No')
  ] : [];

  const cycleCountNum = useMemo(() => robot ? Number(robot.cycleCount) : undefined, [robot]);
  const cycleCapacityNum = useMemo(() => robot ? Number(robot.cycleCapacity) : undefined, [robot]);
  const percentFull = useMemo(() => !cycleCountNum || !cycleCapacityNum ? undefined : 100 * cycleCountNum / cycleCapacityNum, [cycleCountNum, cycleCapacityNum]);

  const data = {
    labels: cycleCountNum && cycleCapacityNum ? ['Full', 'Remaining'] : [],
    datasets: cycleCountNum && cycleCapacityNum ? [
      {
        data: [cycleCountNum],
        circumference: cycleCountNum && cycleCapacityNum ? 360 * cycleCountNum / cycleCapacityNum : 0,
        backgroundColor: [
          percentFull ? (percentFull < 50 ? green[100] : percentFull < 70 ? yellow[400] : percentFull < 90 ? orange[400] : red[400]) : grey[500]
        ],
        borderColor: [
          percentFull ? (percentFull < 50 ? green[500] : percentFull < 70 ? yellow[700] : percentFull < 90 ? orange[900] : red[900]) : grey[900]
        ],
        borderWidth: 1
      },
    ] : [],
  };

  return (
    <>
      <Container>
        <ButtonGroup variant="contained">
          <Button disabled>Details</Button>
          <Button onClick={() => {
            navigate(`/robot/${robotId}/activity`)
          }}>Activity</Button>
          <Button onClick={() => {
            navigate(`/robot/${robotId}/insights`)
          }}>Insights</Button>
          <Button onClick={() => {
            navigate(`/robot/${robotId}/settings`)
          }}>Settings</Button>
        </ButtonGroup>
      </Container>
      {robot ?
        <>
          <Container sx={{
            maxHeight: '10em',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            marginTop: '1em',
            marginBottom: '2em',
            height: 'fit-content'
          }}>
            {cycleCountNum && cycleCapacityNum ? <Typography>{cycleCountNum} cycles complete since last reset, {cycleCapacityNum - cycleCountNum} cycles remaining</Typography> : null}
            <Pie data={data}/>
          </Container>
          <TableContainer component={Paper} sx={{maxWidth: 'sm', margin: 'auto'}}>
            <Table>
              <TableBody>
                {rows.map((row) => (
                  <TableRow
                    key={row.name}
                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                  >
                    <TableCell component="th" scope="row">
                      {row.name}
                    </TableCell>
                    <TableCell align="right">{row.value}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </>
        :
        robotLoading ?
          <CircularProgress/>
          :
          robotError ?
            <Alert severity="error">There was a problem retrieving the robot details data.</Alert>
            : null
      }

    </>
  );

}

export default RobotDetailsView;