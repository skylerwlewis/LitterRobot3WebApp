package io.skylerlewis.litterrobot.litterrobot3.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class UserInfo {
    private String userId;
    private String password;
}
