import React, {createContext, PropsWithChildren, useContext, useEffect, useState} from "react";
import axios from "axios";
import {SettingsContext} from "../settings/SettingsProvider";

interface CycleHistoryItem {
  date: string,
  cyclesCompleted: number
}

interface Insights {
  totalCycles: number,
  averageCycles: number,
  cycleHistory: CycleHistoryItem[]
}

const initialInsights: Insights = {
  totalCycles: 100,
  averageCycles: 3.5,
  cycleHistory: [
    {
      date: '2023-01-05',
      cyclesCompleted: 3
    },
    {
      date: '2023-01-04',
      cyclesCompleted: 4
    },
    {
      date: '2023-01-03',
      cyclesCompleted: 4
    },
    {
      date: '2023-01-02',
      cyclesCompleted: 3
    }
  ]
}

type InsightsContextState = {
  insights: Insights,
  refreshInsights: () => void
}

const initialInsightsContextState = {
  insights: initialInsights,
  refreshInsights: () => {}
}

export const InsightsContext = createContext<InsightsContextState>(initialInsightsContextState);

const InsightsProvider = ({children}: PropsWithChildren<{}>) => {

  const { insightsDays } = useContext(SettingsContext);

  const [insights, setInsights] = useState<Insights>(initialInsightsContextState.insights);

  const refreshInsights = () => {
    axios.get(`api/insights/${insightsDays}`)
      .then(response => {
        setInsights(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }

  useEffect(() => {
    refreshInsights();
  }, [insightsDays]);

  return (
    <InsightsContext.Provider value={{
      insights,
      refreshInsights
    }}>
      {children}
    </InsightsContext.Provider>
  );
}

export default InsightsProvider;
