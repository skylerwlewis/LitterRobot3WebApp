package io.skylerlewis.litterrobot.litterrobot3.webapp.api.activity;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerDelegate;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.activity.model.ActivityHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ActivityRestDelegate extends WhiskerDelegate implements ActivityDelegate {

    private final RestTemplate restTemplate;
    private final WhiskerApiProperties whiskerApiProperties;

    public ActivityRestDelegate(@Autowired RestTemplate restTemplate,
                                @Autowired WhiskerApiProperties whiskerApiProperties) {
        this.restTemplate = restTemplate;
        this.whiskerApiProperties = whiskerApiProperties;
    }

    public ResponseEntity<ActivityHistory> getActivityHistory(Map<String, String> params) {
        return restTemplate.exchange(whiskerApiProperties.getActivityEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), ActivityHistory.class, params);
    }
}
