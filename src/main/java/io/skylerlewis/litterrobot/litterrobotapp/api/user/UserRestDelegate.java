package io.skylerlewis.litterrobot.litterrobotapp.api.user;

import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerDelegate;
import io.skylerlewis.litterrobot.litterrobotapp.api.user.model.UsersResponse;
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

    public ResponseEntity<UsersResponse> getUser() {
        return restTemplate.exchange(whiskerApiProperties.getUsersEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), UsersResponse.class);
    }

}
