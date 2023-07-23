import React, {useContext} from "react";
import {RobotContext} from "./RobotProvider";
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
  TableRow
} from "@mui/material";
import moment from "moment";
import {statusMap, timeFormatString} from "../StatusMap";
import {useNavigate, useParams} from "react-router-dom";

function createData(
  name: string,
  value: string
) {
  return {name, value};
}

const RobotView = () => {


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
        :
        robotLoading ?
          <CircularProgress/>
          :
          robotError ?
            <Alert severity="error">There was a problem retrieving the insights data.</Alert>
            : null
      }

    </>
  );

}

export default RobotView;