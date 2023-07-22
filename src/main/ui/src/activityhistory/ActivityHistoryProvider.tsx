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
  activities: [
    {
      unitStatus: "RDY",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-17T01:33:36.767785"
    },
    {
      unitStatus: "CCC",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T17:33:32.048452"
    },
    {
      unitStatus: "CCP",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T17:31:14.646954"
    },
    {
      unitStatus: "CST",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T17:22:25.615030"
    },
    {
      unitStatus: "CSF",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T16:50:10.504299"
    },
    {
      unitStatus: "DF1",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T13:50:05.015212"
    },
    {
      unitStatus: "DF2",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T13:47:45.957400"
    },
    {
      unitStatus: "CSI",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T13:40:15.608550"
    },
    {
      unitStatus: "BR",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T13:39:15.608549"
    },
    {
      unitStatus: "P",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T13:38:15.608548"
    },
    {
      unitStatus: "OFF",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T13:37:15.608547"
    },
    {
      unitStatus: "SDF",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T13:36:15.608546"
    },
    {
      unitStatus: "DFS",
      litterRobotId: "1f559f3137ec8a",
      timestamp: "2023-07-16T13:35:15.608545"
    }
  ]
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
