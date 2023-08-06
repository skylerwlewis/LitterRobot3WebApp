import React, {useContext} from 'react';
import {
  Alert,
  Button,
  ButtonGroup,
  Chip,
  CircularProgress,
  Container,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography
} from "@mui/material";
import {ActivityHistoryContext} from "./ActivityHistoryProvider";
import {statusMap, timeFormatString} from "../../StatusMap";
import moment from "moment";
import {common, green, grey, orange, red, yellow} from "@mui/material/colors";
import {useNavigate, useParams} from "react-router-dom";

const createData = (
  unitStatus: string,
  unitStatusText: string,
  timestamp: string
) => {
  return {unitStatus, unitStatusText, timestamp};
}

const ActivityHistoryView = () => {

  const {robotId} = useParams();
  const navigate = useNavigate();

  const {activityHistory, activityHistoryLoading, activityHistoryError} = useContext(ActivityHistoryContext);

  const rows = activityHistory ? activityHistory.activities.map(activity => createData(activity.unitStatus, statusMap[activity.unitStatus], moment(activity.timestamp + 'Z').format(timeFormatString))) : [];

  const getSx = (unitStatus: string) => {
    let sx = {};
    switch (unitStatus) {
      case 'RDY':
      case 'CCC':
        sx = {backgroundColor: green[300], color: common.white};
        break;
      case 'CCP':
      case 'CST':
      case 'EC':
        sx = {backgroundColor: yellow[600], color: common.black};
        break;
      case 'CSI':
      case 'CSF':
      case 'SDF':
      case 'DFS':
      case 'DHF':
      case 'DPF':
      case 'HPF':
      case 'OTF':
      case 'PD':
      case 'SCF':
      case 'SPF':
        sx = {backgroundColor: red[800], color: common.white};
        break;
      case 'DF1':
        sx = {backgroundColor: orange[500], color: common.black};
        break;
      case 'DF2':
        sx = {backgroundColor: orange[900], color: common.white};
        break;
      case 'BR':
      case 'P':
      case 'OFF':
      case 'OFFLINE':
        sx = {backgroundColor: grey[600], color: common.white};
        break;
    }
    return sx;
  }

  return (
    <>
      <Container>
        <ButtonGroup variant="contained">
          <Button onClick={() => {
            navigate(`/robot/${robotId}`)
          }}>Details</Button>
          <Button disabled>Activity</Button>
          <Button onClick={() => {
            navigate(`/robot/${robotId}/insights`)
          }}>Insights</Button>
          <Button onClick={() => {
            navigate(`/robot/${robotId}/settings`)
          }}>Settings</Button>
        </ButtonGroup>
      </Container>
      {rows.length > 0 ?
        <>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Timestamp</TableCell>
                  <TableCell align='center'>Status Code</TableCell>
                  <TableCell>Description</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {rows.map((row) => (
                  <TableRow
                    key={row.timestamp}
                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                  >
                    <TableCell component="th" scope="row">
                      {row.timestamp}
                    </TableCell>
                    <TableCell align='center'>
                      <Chip
                        label={row.unitStatus}
                        sx={getSx(row.unitStatus)}
                      />
                    </TableCell>
                    <TableCell>
                      {row.unitStatusText}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <Typography>
            Displaying the {rows.length} most recent activity items
          </Typography>
          <Button size='small' onClick={() => {
            navigate(`/robot/${robotId}/settings`)
          }}>View more</Button>
        </>
        :
        activityHistoryLoading ?
          <CircularProgress/>
          :
          activityHistoryError ?
            <Alert severity="error">There was a problem retrieving the activity history data.</Alert>
            : null
      }
    </>
  );

}

export default ActivityHistoryView;
