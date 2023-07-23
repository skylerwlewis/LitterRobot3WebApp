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
  robotLoading: boolean,
  robotError: boolean
}

const initialRobotContextState = {
  robotLoading: false,
  robotError: false
}

export const RobotContext = createContext<RobotContextState>(initialRobotContextState);

const RobotProvider = ({children}: PropsWithChildren<{}>) => {

  const [robot, setRobot] = useState<Robot>();
  const [robotLoading, setRobotLoading] = useState<boolean>(false);
  const [robotError, setRobotError] = useState<boolean>(false);

  const refreshRobot = async () => {
    setRobotLoading(true);
    axios.get(`api/robot`)
      .then(response => {
        setRobot(response.data);
        setRobotError(false);
      })
      .catch(error => {
        console.error(error);
        setRobotError(true);
      })
      .finally(() => {
        setRobotLoading(false);
      });
  }

  useEffect(() => {
    refreshRobot();
  }, []);

  return (
    <RobotContext.Provider value={{
      robot,
      robotLoading,
      robotError
    }}>
      {children}
    </RobotContext.Provider>
  );
}

export default RobotProvider;
