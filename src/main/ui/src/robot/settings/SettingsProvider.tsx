import React, {createContext, Dispatch, PropsWithChildren, useState} from "react";

type SettingsContextState = {
  activityHistoryLimit: number,
  insightsDays: number,
  setActivityHistoryLimit: Dispatch<React.SetStateAction<number>>,
  setInsightsDays: Dispatch<React.SetStateAction<number>>
}

const initialSettingsContextState = {
  activityHistoryLimit: 10,
  insightsDays: 14,
  setActivityHistoryLimit: () => {
  },
  setInsightsDays: () => {
  }
}

export const SettingsContext = createContext<SettingsContextState>(initialSettingsContextState);

const SettingsContextProvider = ({children}: PropsWithChildren<{}>) => {

  const [activityHistoryLimit, setActivityHistoryLimit] = useState<number>(initialSettingsContextState.activityHistoryLimit);
  const [insightsDays, setInsightsDays] = useState<number>(initialSettingsContextState.insightsDays);

  return (
    <SettingsContext.Provider value={{
      activityHistoryLimit, setActivityHistoryLimit,
      insightsDays, setInsightsDays
    }}>
      {children}
    </SettingsContext.Provider>
  );
}

export default SettingsContextProvider;
