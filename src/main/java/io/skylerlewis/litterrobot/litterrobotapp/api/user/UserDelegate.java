package io.skylerlewis.litterrobot.litterrobotapp.api.user;

import io.skylerlewis.litterrobot.litterrobotapp.api.user.model.User;
import org.springframework.http.ResponseEntity;

public interface UserDelegate {

    ResponseEntity<User> getUser();

}
