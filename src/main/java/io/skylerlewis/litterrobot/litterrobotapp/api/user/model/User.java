package io.skylerlewis.litterrobot.litterrobotapp.api.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private String userId;
    private String userEmail;
    private String firstName;
    private String lastName;
}
