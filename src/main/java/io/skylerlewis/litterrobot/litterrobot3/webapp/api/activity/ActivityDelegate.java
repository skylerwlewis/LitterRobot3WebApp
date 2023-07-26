package io.skylerlewis.litterrobot.litterrobot3.webapp.api.activity;

import io.skylerlewis.litterrobot.litterrobot3.webapp.api.activity.model.ActivityHistory;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ActivityDelegate {

    ResponseEntity<ActivityHistory> getActivityHistory(Map<String, String> params);

}
