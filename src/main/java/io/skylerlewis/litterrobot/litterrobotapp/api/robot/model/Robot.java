package io.skylerlewis.litterrobot.litterrobotapp.api.robot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
public class Robot {

    private String litterRobotId;
    private String litterRobotSerial;
    private String litterRobotNickname;
    private String deviceType;
    private String cycleCount;
    private String totalCycleCount;
    private String cycleCapacity;
    private String newCycleCapacity;
    private String savedCycleCapacity;
    private String isDFITriggered;
    private String isDf1Triggered;
    private String isDf2Triggered;
    private String isDfsTriggered;
    private String isManualReset;
    private String savedIsManualReset;
    private String previousDFITriggered;
    @JsonProperty("DFICycleCount")
    private String DFICycleCount;
    private String savedCycleCount;
    private String cleanCycleWaitTimeMinutes;
    private Long cyclesAfterDrawerFull;
    private String nightLightActive;
    private String panelLockActive;
    private String sleepModeActive;
    private String sleepModeTime;
    private String powerStatus;
    private String unitStatus;
    private String sleepModeEndTime;
    private String sleepModeStartTime;
    private LocalDateTime lastSeen;
    private LocalDateTime setupDate;
    private Boolean isOnboarded;
    private Boolean didNotifyOffline;
    private Boolean autoOfflineDisabled;

}
