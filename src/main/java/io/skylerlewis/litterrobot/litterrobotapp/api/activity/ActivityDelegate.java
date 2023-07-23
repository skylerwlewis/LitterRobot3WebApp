package io.skylerlewis.litterrobot.litterrobotapp.api.activity;

import io.skylerlewis.litterrobot.litterrobotapp.api.activity.model.ActivityHistory;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ActivityDelegate {

    ResponseEntity<ActivityHistory> getActivityHistory(Map<String, String> params);

}
