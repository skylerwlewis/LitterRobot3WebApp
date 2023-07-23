import React, {useContext} from 'react';
import '../App.css';
import {
  Alert,
  Button,
  Chip, CircularProgress,
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
import {statusMap, timeFormatString} from "../StatusMap";
import moment from "moment";
import {green, grey, orange, red, yellow} from "@mui/material/colors";
import {useNavigate} from "react-router-dom";

function createData(
  unitStatus: string,
  unitStatusText: string,
  timestamp: string
) {
  return {unitStatus, unitStatusText, timestamp};
}

const ActivityHistoryView = () => {

  const navigate = useNavigate();

  const {activityHistory, activityHistoryLoading, activityHistoryError} = useContext(ActivityHistoryContext);

  const rows = activityHistory ? activityHistory.activities.map(activity => createData(activity.unitStatus, statusMap[activity.unitStatus], moment(activity.timestamp + 'Z').format(timeFormatString))) : [];

  const getSx = (unitStatus: string) => {
    let sx = {};
    switch (unitStatus) {
      case 'RDY':
      case 'CCC':
        sx = {backgroundColor: green[300], color: 'white'};
        break;
      case 'CCP':
      case 'CST':
        sx = {backgroundColor: yellow[600], color: 'black'};
        break;
      case 'CSI':
      case 'CSF':
      case 'SDF':
      case 'DFS':
        sx = {backgroundColor: red[800], color: 'white'};
        break;
      case 'DF1':
        sx = {backgroundColor: orange[500], color: 'black'};
        break;
      case 'DF2':
        sx = {backgroundColor: orange[900], color: 'white'};
        break;
      case 'BR':
      case 'P':
      case 'OFF':
        sx = {backgroundColor: grey[600], color: 'white'};
        break;
    }
    return sx;
  }

  return (
    <>
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
            navigate('/settings')
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
