import React, {createContext, PropsWithChildren, useEffect, useState} from "react";
import axios from "axios";

interface Robot {
  litterRobotId: string,
  litterRobotSerial: string,
  litterRobotNickname: string,
  deviceType: string,
  cycleCount: string,
  totalCycleCount: string,
  cycleCapacity: string,
  newCycleCapacity: string,
  savedCycleCapacity: string,
  isDFITriggered: string,
  isDf1Triggered: string,
  isDf2Triggered: string,
  isDfsTriggered: string,
  isManualReset: string,
  savedIsManualReset: string,
  previousDFITriggered: string,
  DFICycleCount: string,
  savedCycleCount: string,
  cleanCycleWaitTimeMinutes: string,
  cyclesAfterDrawerFull: number,
  nightLightActive: string,
  panelLockActive: string,
  sleepModeActive: string,
  sleepModeTime: string | null,
  powerStatus: string,
  unitStatus: string,
  sleepModeEndTime: string,
  sleepModeStartTime: string,
  lastSeen: string,
  setupDate: string,
  isOnboarded: boolean,
  didNotifyOffline: boolean,
  autoOfflineDisabled: boolean
}

type RobotContextState = {
  robot?: Robot,
  refreshRobot: () => void
}

const initialRobotContextState = {
  refreshRobot: () => {}
}

export const RobotContext = createContext<RobotContextState>(initialRobotContextState);

const RobotProvider = ({children}: PropsWithChildren<{}>) => {

  const [robot, setRobot] = useState<Robot>();

  const refreshRobot = () => {
    axios.get(`api/robot`)
      .then(response => {
        setRobot(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }

  useEffect(() => {
    refreshRobot();
  }, []);

  return (
    <RobotContext.Provider value={{
      robot,
      refreshRobot
    }}>
      {children}
    </RobotContext.Provider>
  );
}

export default RobotProvider;
