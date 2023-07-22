package io.skylerlewis.litterrobot.litterrobotapp.api.activity.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ActivityHistory {

    List<Activity> activities;

}
