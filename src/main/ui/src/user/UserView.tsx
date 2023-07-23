import React, {useContext} from "react";
import {Alert, CircularProgress, Paper, Table, TableBody, TableCell, TableContainer, TableRow} from "@mui/material";
import {UserContext} from "./UserProvider";

function createData(
  name: string,
  value: string
) {
  return {name, value};
}

const UserView = () => {

  const {user, userLoading, userError} = useContext(UserContext);

  const userDetailRows = user ? user.user ? [
    createData('User ID', user.user.userId),
    createData('User Email', user.user.userEmail),
    createData('First Name', user.user.firstName),
    createData('Last Name', user.user.lastName)
  ] : [] : [];

  return (
    <>
      {userDetailRows.length > 0 ?
        <TableContainer component={Paper} sx={{maxWidth: 'sm', margin: 'auto'}}>
          <Table>
            <TableBody>
              {userDetailRows.map((row) => (
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
            <Alert severity="error">There was a problem retrieving the user data.</Alert>
            : null}
    </>
  );

}

export default UserView;