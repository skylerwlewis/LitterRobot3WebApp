import React from 'react';
import './App.css';
import RobotProvider from "./robot/RobotProvider";
import RobotView from "./robot/RobotView";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import ActivityHistoryView from "./activityhistory/ActivityHistoryView";
import ActivityHistoryProvider from "./activityhistory/ActivityHistoryProvider";
import ResponsiveAppBar from "./menu/ResponsiveAppBar";
import {createTheme, ThemeProvider} from "@mui/material";
import SettingsView from "./settings/SettingsView";
import SettingsProvider from "./settings/SettingsProvider";
import InsightsView from "./insights/InsightsView";
import InsightsProvider from "./insights/InsightsProvider";

const theme = createTheme({
  palette: {
    primary: {
      main: '#f7b04d'
    },
    secondary: {
      main: '#383838'
    }
  }
});

const App = () => {
  return (

    <div className="App">
      <ThemeProvider theme={theme}>
        <Router>
          <ResponsiveAppBar/>
          <SettingsProvider>
            <RobotProvider>
              <ActivityHistoryProvider>
                <InsightsProvider>
                  <Routes>
                    <Route path="/" element={<RobotView/>}/>
                    <Route path="/activity" element={<ActivityHistoryView/>}/>
                    <Route path="/insights" element={<InsightsView/>}/>
                    <Route path="/settings" element={<SettingsView/>}/>
                  </Routes>
                </InsightsProvider>
              </ActivityHistoryProvider>
            </RobotProvider>
          </SettingsProvider>
        </Router>
      </ThemeProvider>
    </div>
  );
}

export default App;
