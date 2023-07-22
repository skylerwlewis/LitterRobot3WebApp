package io.skylerlewis.litterrobot.litterrobotapp.api.user;

import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerService;
import io.skylerlewis.litterrobot.litterrobotapp.api.user.model.UsersResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;

@Component
@Slf4j
public class UserService extends WhiskerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WhiskerApiProperties whiskerApiProperties;

    public UsersResponse getUser() {
        UsersResponse user = null;
        ResponseEntity<UsersResponse> response = restTemplate.exchange(whiskerApiProperties.getUsersEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), UsersResponse.class);
        if(response != null && response.getBody() != null) {
            log.info("Successfully retrieved users: {}", response.getBody());
            user = response.getBody();
        }
        return user;
    }

}
