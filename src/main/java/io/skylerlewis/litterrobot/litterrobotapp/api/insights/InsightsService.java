package io.skylerlewis.litterrobot.litterrobotapp.api.insights;

import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerApiProperties;
import io.skylerlewis.litterrobot.litterrobotapp.api.WhiskerService;
import io.skylerlewis.litterrobot.litterrobotapp.api.insights.model.Insights;
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
public class InsightsService extends WhiskerService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WhiskerApiProperties whiskerApiProperties;

    @Autowired
    private TokenService tokenService;

    public Insights getRobotInsights(String robotId, Integer days) {
        Insights insights = null;

        Map<String, Object> params = new HashMap<>();
        params.put("userId", tokenService.getUserId());
        params.put("robotId", robotId);
        params.put("days", days);

        ResponseEntity<Insights> response = restTemplate.exchange(whiskerApiProperties.getInsightsEndpoint(), HttpMethod.GET, new HttpEntity(getRequestHeaders()), Insights.class, params);
        if(response != null && response.getBody() != null) {
            log.info("Successfully retrieved robot insights: {}", response.getBody());
            insights = response.getBody();
        }
        return insights;
    }



}
