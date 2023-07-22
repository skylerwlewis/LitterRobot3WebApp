package io.skylerlewis.litterrobot.litterrobotapp.api.activity;

import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerService;
import io.skylerlewis.litterrobot.litterrobotapp.api.activity.model.ActivityHistory;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ActivityService extends WhiskerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WhiskerApiProperties whiskerApiProperties;

    @Autowired
    private TokenService tokenService;

    public ActivityHistory getActivityHistory(String robotId, Integer limit) {
        ActivityHistory activityHistory = null;

        Map<String, Object> params = new HashMap<>();
        params.put("userId", tokenService.getUserId());
        params.put("robotId", robotId);
        params.put("limit", limit);

        ResponseEntity<ActivityHistory> response = restTemplate.exchange(whiskerApiProperties.getActivityEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), ActivityHistory.class, params);
        if(response != null && response.getBody() != null) {
            log.info("Successfully retrieved robot activity history: {}", response.getBody());
            activityHistory = response.getBody();
        }
        return activityHistory;
    }

}
