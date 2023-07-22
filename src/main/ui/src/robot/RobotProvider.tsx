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

const initialRobot: Robot = {
  litterRobotId: "1f559f3137ec8a",
  litterRobotSerial: "LR3CI488589",
  litterRobotNickname: "Luna's Litterbox",
  deviceType: "iot",
  cycleCount: "19",
  totalCycleCount: "847",
  cycleCapacity: "23",
  newCycleCapacity: "20",
  savedCycleCapacity: "24",
  isDFITriggered: "1",
  isDf1Triggered: "1",
  isDf2Triggered: "1",
  isDfsTriggered: "1",
  isManualReset: "0",
  savedIsManualReset: "0",
  previousDFITriggered: "0",
  DFICycleCount: "847",
  savedCycleCount: "18",
  cleanCycleWaitTimeMinutes: "7",
  cyclesAfterDrawerFull: 20,
  nightLightActive: "1",
  panelLockActive: "0",
  sleepModeActive: "0",
  sleepModeTime: null,
  powerStatus: "AC",
  unitStatus: "RDY",
  sleepModeEndTime: "0",
  sleepModeStartTime: "0",
  lastSeen: "2023-07-16T13:25:41.338128",
  setupDate: "2022-02-03T22:14:18.858069",
  isOnboarded: true,
  didNotifyOffline: false,
  autoOfflineDisabled: true
}

type RobotContextState = {
  robot: Robot,
  refreshRobot: () => void
}

const initialRobotContextState = {
  robot: initialRobot,
  refreshRobot: () => {}
}

export const RobotContext = createContext<RobotContextState>(initialRobotContextState);

const RobotProvider = ({children}: PropsWithChildren<{}>) => {

  const [robot, setRobot] = useState<Robot>(initialRobot);

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
