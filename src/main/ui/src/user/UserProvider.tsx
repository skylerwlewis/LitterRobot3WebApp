import React, {createContext, PropsWithChildren, useContext, useEffect, useState} from "react";
import axios from "axios";

interface Settings {
  all_notifications: string,
  general_notifications: string,
  fault_notifications: string,
  DFI_notifications: string,
  CCC_notifications: string,
  CSI_notifications: string,
  CSF_notifications: string,
  PD_notifications: string,
  OTF_notifications: string,
  DHF_notifications: string,
  BR_notifications: string,
  offline_notifications: string,
  userId: string
}

interface MobileDevice {
  userId: string,
  oneSignalPlayerId: string,
  deviceId: string,
  version: string
}

interface LitterRobot {
  userId: string,
  litterRobotId: string,
  litterRobotSerial: string,
  litterRobotNickname: string
}

interface UserDetails {
  userId: string,
  userEmail: string,
  firstName: string,
  lastName: string
}

interface User {
  user: UserDetails,
  litterRobots: LitterRobot[],
  mobileDevices: MobileDevice[],
  settings: Settings
}

type UserContextState = {
  user?: User,
  userLoading: boolean,
  userError: boolean
}

const initialUserContextState = {
  userLoading: false,
  userError: false
}

export const UserContext = createContext<UserContextState>(initialUserContextState);

const UserProvider = ({children}: PropsWithChildren<{}>) => {

  const [user, setUser] = useState<User>();
  const [userLoading, setUserLoading] = useState<boolean>(false);
  const [userError, setUserError] = useState<boolean>(false);

  const refreshUser = () => {
    setUserLoading(true);
    axios.get(`/api/user`)
      .then(response => {
        setUser(response.data);
        setUserError(false);
      })
      .catch(error => {
        console.error(error);
        setUserError(true);
      })
      .finally(() => {
        setUserLoading(false);
      });;
  }

  useEffect(() => {
    refreshUser();
  }, []);

  return (
    <UserContext.Provider value={{
      user,
      userLoading,
      userError
    }}>
      {children}
    </UserContext.Provider>
  );
}

export default UserProvider;
