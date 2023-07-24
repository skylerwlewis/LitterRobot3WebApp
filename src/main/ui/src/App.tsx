import React from 'react';
import './App.css';
import RobotProvider from "./robot/RobotProvider";
import RobotView from "./robot/RobotView";
import {BrowserRouter as Router, Outlet, Route, Routes} from "react-router-dom";
import ActivityHistoryView from "./robot/activityhistory/ActivityHistoryView";
import ActivityHistoryProvider from "./robot/activityhistory/ActivityHistoryProvider";
import ResponsiveAppBar from "./menu/ResponsiveAppBar";
import {createTheme, ThemeProvider} from "@mui/material";
import SettingsView from "./robot/settings/SettingsView";
import SettingsProvider from "./robot/settings/SettingsProvider";
import InsightsView from "./robot/insights/InsightsView";
import InsightsProvider from "./robot/insights/InsightsProvider";
import UserProvider from "./user/UserProvider";
import UserView from "./user/UserView";
import RobotsView from "./user/RobotsView";
import DevicesView from "./user/DevicesView";
import UserSettingsView from "./user/UserSettingsView";

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
          <UserProvider>
          <ResponsiveAppBar/>
            <Routes>
              <Route path="/" element={<RobotsView/>}/>
              <Route path="/robot/:robotId" element={
                <SettingsProvider>
                  <RobotProvider>
                    <ActivityHistoryProvider>
                      <InsightsProvider>
                        <Outlet/>
                      </InsightsProvider>
                    </ActivityHistoryProvider>
                  </RobotProvider>
                </SettingsProvider>
              }>
                <Route index element={<RobotView/>}/>
                <Route path="activity" element={<ActivityHistoryView/>}/>
                <Route path="insights" element={<InsightsView/>}/>
                <Route path="settings" element={<SettingsView/>}/>
              </Route>
              <Route path="/user" element={<UserView/>}/>
              <Route path="/devices" element={<DevicesView/>}/>
              <Route path="/settings" element={<UserSettingsView/>}/>
            </Routes>
          </UserProvider>
        </Router>
      </ThemeProvider>
    </div>
  );
}

export default App;
