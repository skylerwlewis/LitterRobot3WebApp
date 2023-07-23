import React, {useContext} from "react";
import {
  Alert,
  Card,
  CardContent,
  CircularProgress,
  Container,
  Table,
  TableBody,
  TableCell,
  TableRow
} from "@mui/material";
import {UserContext} from "./UserProvider";

function createData(
  name: string,
  value: string
) {
  return {name, value};
}

const DevicesView = () => {

  const {user, userLoading, userError} = useContext(UserContext);

  const mobileDeviceRows = user ? user.mobileDevices ? user.mobileDevices.map(device => [
    createData('One Signal Player ID', device.oneSignalPlayerId),
    createData('Device ID', device.deviceId),
    createData('Version', device.version)
  ]) : [] : [];

  return (
    <>
      {mobileDeviceRows.length > 0 ?
        <Container>
          {mobileDeviceRows.map((rows, index) =>
            <Card sx={{maxWidth: 345}} key={index}>
              <CardContent>
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
              </CardContent>
            </Card>
          )}
        </Container>
        :
        userLoading ?
          <CircularProgress/>
          :
          userError ?
            <Alert severity="error">There was a problem retrieving the devices data.</Alert>
            : null}
    </>
  );

}

export default DevicesView;