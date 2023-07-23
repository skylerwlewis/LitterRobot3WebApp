import React, {createContext, PropsWithChildren, useContext, useEffect, useState} from "react";
import axios from "axios";
import {SettingsContext} from "../settings/SettingsProvider";

interface Activity {
  unitStatus: string,
  litterRobotId: string,
  timestamp: string
}

interface ActivityHistory {
  activities: Activity[]
}

const initialActivityHistory: ActivityHistory = {
  activities: []
}

type ActivityHistoryContextState = {
  activityHistory: ActivityHistory,
  refreshActivityHistory: () => void
}

const initialActivityHistoryContextState = {
  activityHistory: initialActivityHistory,
  refreshActivityHistory: () => {}
}

export const ActivityHistoryContext = createContext<ActivityHistoryContextState>(initialActivityHistoryContextState);

const ActivityHistoryProvider = ({children}: PropsWithChildren<{}>) => {

  const { activityHistoryLimit } = useContext(SettingsContext);

  const [activityHistory, setActivityHistory] = useState<ActivityHistory>(initialActivityHistoryContextState.activityHistory);

  const refreshActivityHistory = () => {
    axios.get(`api/activity/${activityHistoryLimit}`)
      .then(response => {
        setActivityHistory(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }

  useEffect(() => {
    refreshActivityHistory();
  }, [activityHistoryLimit]);

  return (
    <ActivityHistoryContext.Provider value={{
      activityHistory,
      refreshActivityHistory
    }}>
      {children}
    </ActivityHistoryContext.Provider>
  );
}

export default ActivityHistoryProvider;
