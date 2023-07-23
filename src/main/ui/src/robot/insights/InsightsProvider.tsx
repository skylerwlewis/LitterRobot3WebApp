import React, {createContext, PropsWithChildren, useContext, useEffect, useState} from "react";
import axios from "axios";
import {SettingsContext} from "../settings/SettingsProvider";
import {useParams} from "react-router-dom";

interface CycleHistoryItem {
  date: string,
  cyclesCompleted: number
}

interface Insights {
  totalCycles: number,
  averageCycles: number,
  cycleHistory: CycleHistoryItem[]
}

type InsightsContextState = {
  insights?: Insights,
  insightsLoading: boolean,
  insightsError: boolean
}

const initialInsightsContextState = {
  insightsLoading: false,
  insightsError: false
}

export const InsightsContext = createContext<InsightsContextState>(initialInsightsContextState);

const InsightsProvider = ({children}: PropsWithChildren<{}>) => {

  const {robotId} = useParams();

  const {insightsDays} = useContext(SettingsContext);

  const [insights, setInsights] = useState<Insights>();
  const [insightsLoading, setInsightsLoading] = useState<boolean>(false);
  const [insightsError, setInsightsError] = useState<boolean>(false);

  const refreshInsights = () => {
    setInsightsLoading(true);
    axios.get(`/api/robot/${robotId}/insights/${insightsDays}`)
      .then(response => {
        setInsights(response.data);
        setInsightsError(false);
      })
      .catch(error => {
        console.error(error);
        setInsightsError(true);
      })
      .finally(() => {
        setInsightsLoading(false);
      });

  }

  useEffect(() => {
    refreshInsights();
  }, [insightsDays]);

  return (
    <InsightsContext.Provider value={{
      insights,
      insightsLoading,
      insightsError
    }}>
      {children}
    </InsightsContext.Provider>
  );
}

export default InsightsProvider;
