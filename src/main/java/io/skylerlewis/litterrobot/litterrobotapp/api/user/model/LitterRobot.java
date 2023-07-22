package io.skylerlewis.litterrobot.litterrobotapp.api.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LitterRobot {
    private String userId;
    private String litterRobotId;
    private String litterRobotSerial;
    private String litterRobotNickname;
}
