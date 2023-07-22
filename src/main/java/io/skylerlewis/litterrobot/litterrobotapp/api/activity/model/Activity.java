package io.skylerlewis.litterrobot.litterrobotapp.api.activity.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class Activity {

    private String unitStatus;
    private String litterRobotId;
    private LocalDateTime timestamp;

}
