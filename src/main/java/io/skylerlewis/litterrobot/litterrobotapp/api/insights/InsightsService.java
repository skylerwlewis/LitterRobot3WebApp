package io.skylerlewis.litterrobot.litterrobotapp.api.insights;

import io.skylerlewis.litterrobot.litterrobotapp.api.insights.model.Insights;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InsightsService {

    private final InsightsDelegate insightsDelegate;
    private final TokenService tokenService;

    public InsightsService(@Autowired InsightsDelegate insightsDelegate,
                           @Autowired TokenService tokenService) {
        this.insightsDelegate = insightsDelegate;
        this.tokenService = tokenService;
    }

    public Insights getRobotInsights(String robotId, Integer days) {
        Insights insights = null;

        Map<String, String> params = new HashMap<>();
        params.put("userId", tokenService.getUserId());
        params.put("robotId", robotId);
        params.put("days", days.toString());

        ResponseEntity<Insights> response = insightsDelegate.getRobotInsights(params);
        if (response != null && response.getBody() != null) {
            log.info("Successfully retrieved robot robot.insights: {}", response.getBody());
            insights = response.getBody();
        }
        return insights;
    }


}
