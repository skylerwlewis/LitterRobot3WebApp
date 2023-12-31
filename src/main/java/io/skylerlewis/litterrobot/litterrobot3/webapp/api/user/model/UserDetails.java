package io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDetails {
    private String userId;
    private String userEmail;
    private String firstName;
    private String lastName;
}
