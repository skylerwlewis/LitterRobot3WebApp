package io.skylerlewis.litterrobot.litterrobotapp.api.user;

import io.skylerlewis.litterrobot.litterrobotapp.api.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserService {

    private final UserDelegate userDelegate;

    public UserService(@Autowired UserDelegate userDelegate) {
        this.userDelegate = userDelegate;
    }

    public User getUser() {
        User user = null;
        ResponseEntity<User> response = userDelegate.getUser();
        if (response != null && response.getBody() != null) {
            log.info("Successfully retrieved users: {}", response.getBody());
            user = response.getBody();
        }
        return user;
    }

}
