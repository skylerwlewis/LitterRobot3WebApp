package io.skylerlewis.litterrobot.litterrobot3.webapp.api.user;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.model.User;
import org.springframework.http.ResponseEntity;

public interface UserDelegate {

    ResponseEntity<User> getUser();

}
