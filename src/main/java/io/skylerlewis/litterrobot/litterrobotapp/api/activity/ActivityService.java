package io.skylerlewis.litterrobot.litterrobotapp.api.activity;

import io.skylerlewis.litterrobot.litterrobotapp.api.activity.model.ActivityHistory;
import io.skylerlewis.litterrobot.litterrobotapp.api.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ActivityService {

    private final ActivityDelegate activityDelegate;
    private final TokenService tokenService;

    public ActivityService(@Autowired ActivityDelegate activityDelegate,
                           @Autowired TokenService tokenService) {
        this.activityDelegate = activityDelegate;
        this.tokenService = tokenService;
    }

    public ActivityHistory getActivityHistory(String robotId, Integer limit) {
        ActivityHistory activityHistory = null;

        Map<String, String> params = new HashMap<>();
        params.put("userId", tokenService.getUserId());
        params.put("robotId", robotId);
        params.put("limit", limit.toString());

        ResponseEntity<ActivityHistory> response = activityDelegate.getActivityHistory(params);
        if (response != null && response.getBody() != null) {
            log.info("Successfully retrieved robot activity history: {}", response.getBody());
            activityHistory = response.getBody();
        }
        return activityHistory;
    }

}
