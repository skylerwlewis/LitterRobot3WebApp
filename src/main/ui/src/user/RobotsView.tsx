import React, {useContext} from "react";
import {
  Card, CardActionArea,
  CardContent,
  Container,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow
} from "@mui/material";
import {UserContext} from "./UserProvider";
import {useNavigate} from "react-router-dom";

function createData(
  name: string,
  value: string
) {
  return {name, value};
}

const RobotsView = () => {

  const navigate = useNavigate();

  const {user, userLoading, userError} = useContext(UserContext);

  const litterRobotRows = user ? user.litterRobots ? user.litterRobots.map(robot => [
    createData('Litter Robot ID', robot.litterRobotId),
    createData('Serial Number', robot.litterRobotSerial),
    createData('Nickname', robot.litterRobotNickname)
  ]) : [] : [];

  return (
    <>
      <Container>
        {litterRobotRows.length > 0 ?
          litterRobotRows.map((rows, index) =>
            <Card sx={{maxWidth: 345}} key={index} onClick={() => {
              navigate(`/robot/${rows[0].value}`)
            }}>
              <CardActionArea>
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
              </CardActionArea>
            </Card>
          ) : null
        }
      </Container>
    </>
  );

}

export default RobotsView;