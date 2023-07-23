package io.skylerlewis.litterrobot.litterrobotapp.api.user;

import io.skylerlewis.litterrobot.litterrobotapp.api.user.model.UsersResponse;
import org.springframework.http.ResponseEntity;

public interface UserDelegate {

    ResponseEntity<UsersResponse> getUser();

}
