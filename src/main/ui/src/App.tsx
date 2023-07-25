import React from 'react';
import './App.css';
import RobotDetailsProvider from "./robot/details/RobotDetailsProvider";
import RobotDetailsView from "./robot/details/RobotDetailsView";
import {BrowserRouter as Router, Outlet, Route, Routes} from "react-router-dom";
import ActivityHistoryView from "./robot/activity/ActivityHistoryView";
import ActivityHistoryProvider from "./robot/activity/ActivityHistoryProvider";
import ResponsiveAppBar from "./menu/ResponsiveAppBar";
import {createTheme, ThemeProvider} from "@mui/material";
import UiSettingsView from "./robot/settings/UiSettingsView";
import UiSettingsProvider from "./robot/settings/UiSettingsProvider";
import InsightsView from "./robot/insights/InsightsView";
import InsightsProvider from "./robot/insights/InsightsProvider";
import UserProvider from "./user/UserProvider";
import UserView from "./user/UserView";
import RobotsView from "./user/RobotsView";
import DevicesView from "./user/DevicesView";
import SettingsView from "./user/SettingsView";

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
                <UiSettingsProvider>
                  <RobotDetailsProvider>
                    <ActivityHistoryProvider>
                      <InsightsProvider>
                        <Outlet/>
                      </InsightsProvider>
                    </ActivityHistoryProvider>
                  </RobotDetailsProvider>
                </UiSettingsProvider>
              }>
                <Route index element={<RobotDetailsView/>}/>
                <Route path="activity" element={<ActivityHistoryView/>}/>
                <Route path="insights" element={<InsightsView/>}/>
                <Route path="settings" element={<UiSettingsView/>}/>
              </Route>
              <Route path="/user" element={<UserView/>}/>
              <Route path="/devices" element={<DevicesView/>}/>
              <Route path="/settings" element={<SettingsView/>}/>
            </Routes>
          </UserProvider>
        </Router>
      </ThemeProvider>
    </div>
  );
}

export default App;
