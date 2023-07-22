package io.skylerlewis.litterrobot.litterrobotapp.api.user.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UsersResponse {

    private User user;
    private List<LitterRobot> litterRobots;
    private List<MobileDevice> mobileDevices;
    private Settings settings;

}
