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

type ActivityHistoryContextState = {
  activityHistory?: ActivityHistory,
  activityHistoryLoading: boolean,
  activityHistoryError: boolean
}

const initialActivityHistoryContextState = {
  activityHistoryLoading: false,
  activityHistoryError: false
}

export const ActivityHistoryContext = createContext<ActivityHistoryContextState>(initialActivityHistoryContextState);

const ActivityHistoryProvider = ({children}: PropsWithChildren<{}>) => {

  const {activityHistoryLimit} = useContext(SettingsContext);

  const [activityHistory, setActivityHistory] = useState<ActivityHistory>();
  const [activityHistoryLoading, setActivityHistoryLoading] = useState<boolean>(false);
  const [activityHistoryError, setActivityHistoryError] = useState<boolean>(false);

  const refreshActivityHistory = () => {
    setActivityHistoryLoading(true);
    axios.get(`api/activity/${activityHistoryLimit}`)
      .then(response => {
        setActivityHistory(response.data);
        setActivityHistoryError(false);
      })
      .catch(error => {
        console.error(error);
        setActivityHistoryError(true);
      })
      .finally(() => {
        setActivityHistoryLoading(false);
      });;
  }

  useEffect(() => {
    refreshActivityHistory();
  }, [activityHistoryLimit]);

  return (
    <ActivityHistoryContext.Provider value={{
      activityHistory,
      activityHistoryLoading,
      activityHistoryError
    }}>
      {children}
    </ActivityHistoryContext.Provider>
  );
}

export default ActivityHistoryProvider;
