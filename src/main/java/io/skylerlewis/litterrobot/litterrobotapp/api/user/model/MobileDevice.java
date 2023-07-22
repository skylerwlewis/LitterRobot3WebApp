package io.skylerlewis.litterrobot.litterrobotapp.api.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MobileDevice {
    private String userId;
    private String oneSignalPlayerId;
    private String deviceId;
    private String version;
}
