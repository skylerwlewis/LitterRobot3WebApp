package io.skylerlewis.litterrobot.litterrobotapp;

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
