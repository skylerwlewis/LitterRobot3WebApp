import React, {createContext, Dispatch, PropsWithChildren, useState} from "react";

type UiSettingsContextState = {
  activityHistoryLimit: number,
  insightsDays: number,
  setActivityHistoryLimit: Dispatch<React.SetStateAction<number>>,
  setInsightsDays: Dispatch<React.SetStateAction<number>>
}

const initialUiSettingsContextState = {
  activityHistoryLimit: 10,
  insightsDays: 14,
  setActivityHistoryLimit: () => {
  },
  setInsightsDays: () => {
  }
}

export const UiSettingsContext = createContext<UiSettingsContextState>(initialUiSettingsContextState);

const UiSettingsContextProvider = ({children}: PropsWithChildren<{}>) => {

  const [activityHistoryLimit, setActivityHistoryLimit] = useState<number>(initialUiSettingsContextState.activityHistoryLimit);
  const [insightsDays, setInsightsDays] = useState<number>(initialUiSettingsContextState.insightsDays);

  return (
    <UiSettingsContext.Provider value={{
      activityHistoryLimit, setActivityHistoryLimit,
      insightsDays, setInsightsDays
    }}>
      {children}
    </UiSettingsContext.Provider>
  );
}

export default UiSettingsContextProvider;
