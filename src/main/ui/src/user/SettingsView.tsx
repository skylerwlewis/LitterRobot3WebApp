import React, {useContext} from "react";
import {Alert, CircularProgress, Paper, Table, TableBody, TableCell, TableContainer, TableRow} from "@mui/material";
import {UserContext} from "./UserProvider";
import {statusMap} from "../StatusMap";

const createData = (
  name: string,
  value: string
) => {
  return {name, value};
}

const SettingsView = () => {

  const {user, userLoading, userError} = useContext(UserContext);

  const settingsRows = user ? user.settings ? [
    createData('All Notifications?', user.settings.all_notifications === '1' ? 'Yes' : 'No'),
    createData('General Notifications?', user.settings.general_notifications === '1' ? 'Yes' : 'No'),
    createData('Fault Notifications?', user.settings.fault_notifications === '1' ? 'Yes' : 'No'),
    createData('Drawer Full Indicator Notifications?', user.settings.DFI_notifications === '1' ? 'Yes' : 'No'),
    createData(`"${statusMap['CCC']}" Notifications?`, user.settings.CCC_notifications === '1' ? 'Yes' : 'No'),
    createData(`"${statusMap['CSI']}" Notifications?`, user.settings.CSI_notifications === '1' ? 'Yes' : 'No'),
    createData(`"${statusMap['CSF']}" Notifications?`, user.settings.CSF_notifications === '1' ? 'Yes' : 'No'),
    createData(`"${statusMap['PD']}" Notifications?`, user.settings.PD_notifications === '1' ? 'Yes' : 'No'),
    createData(`"${statusMap['OTF']}" Notifications?`, user.settings.OTF_notifications === '1' ? 'Yes' : 'No'),
    createData(`"${statusMap['DHF']}" Notifications?`, user.settings.DHF_notifications === '1' ? 'Yes' : 'No'),
    createData(`"${statusMap['BR']}" Notifications?`, user.settings.BR_notifications === '1' ? 'Yes' : 'No'),
    createData(`"${statusMap['OFFLINE']}" Notifications?`, user.settings.offline_notifications === '1' ? 'Yes' : 'No')
  ] : [] : [];

  return (
    <>
      {settingsRows.length > 0 ?
        <TableContainer component={Paper} sx={{maxWidth: 'sm', margin: 'auto'}}>
          <Table>
            <TableBody>
              {settingsRows.map((row) => (
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
        userLoading ?
          <CircularProgress/>
          :
          userError ?
            <Alert severity="error">There was a problem retrieving the settings data.</Alert>
            : null}
    </>
  );

}

export default SettingsView;