package io.skylerlewis.litterrobot.litterrobot3.webapp.api.user;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerDelegate;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRestDelegate extends WhiskerDelegate implements UserDelegate {

    private final RestTemplate restTemplate;
    private final WhiskerApiProperties whiskerApiProperties;

    public UserRestDelegate(@Autowired RestTemplate restTemplate,
                            @Autowired WhiskerApiProperties whiskerApiProperties) {
        this.restTemplate = restTemplate;
        this.whiskerApiProperties = whiskerApiProperties;
    }

    public ResponseEntity<User> getUser() {
        return restTemplate.exchange(whiskerApiProperties.getUsersEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), User.class);
    }

}
