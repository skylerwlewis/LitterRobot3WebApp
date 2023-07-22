import React, {useContext, useState} from "react";
import {Button, Container, Paper, Stack, TextField, Typography} from "@mui/material";
import {SettingsContext} from "./SettingsProvider";

const isInteger = (value: string) => {
  return !!value && !isNaN(Number(value)) && Number.isInteger(Number(value));
};

const SettingsView = () => {

  const {
    activityHistoryLimit,
    setActivityHistoryLimit,
    insightsDays,
    setInsightsDays
  } = useContext(SettingsContext);

  const [activityHistoryLimitString, setActivityHistoryLimitString] = useState<string>(activityHistoryLimit.toString());
  const [insightsDaysString, setInsightsDaysString] = useState<string>(insightsDays.toString());

  const saveValues = () => {
    if(isInteger(activityHistoryLimitString) && isInteger(insightsDaysString)) {
      setActivityHistoryLimit(Number(activityHistoryLimitString));
      setInsightsDays(Number(insightsDaysString));
    }
  }

  return (
    <>
      <Container>
        <Typography>Enter new values to change the limits.</Typography>
        <TextField
          label="Activity History Limit"
          value={activityHistoryLimitString}
          onChange={(event => {
            setActivityHistoryLimitString(event.target.value)
          })}
          sx={{margin: '1em'}}
          error={!isInteger(activityHistoryLimitString)}
        />
        <TextField
          label="Insights Days"
          value={insightsDaysString}
          onChange={(event => {
            setInsightsDaysString(event.target.value)
          })}
          sx={{margin: '1em'}}
          error={!isInteger(insightsDaysString)}
        />
        <div>
          <Button variant="contained" sx={{ marginBottom: '1em' }} onClick={() => { saveValues() }}>Save</Button>
        </div>
      </Container>
    </>
  );

}

export default SettingsView;