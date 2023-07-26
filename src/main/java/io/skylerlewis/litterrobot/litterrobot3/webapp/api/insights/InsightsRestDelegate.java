package io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.WhiskerDelegate;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.insights.model.Insights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class InsightsRestDelegate extends WhiskerDelegate implements InsightsDelegate {

    private final RestTemplate restTemplate;
    private final WhiskerApiProperties whiskerApiProperties;

    public InsightsRestDelegate(@Autowired RestTemplate restTemplate,
                                @Autowired WhiskerApiProperties whiskerApiProperties) {
        this.restTemplate = restTemplate;
        this.whiskerApiProperties = whiskerApiProperties;
    }

    public ResponseEntity<Insights> getRobotInsights(Map<String, String> params) {
        return restTemplate.exchange(whiskerApiProperties.getInsightsEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), Insights.class, params);
    }
}
