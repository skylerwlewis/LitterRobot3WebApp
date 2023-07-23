package io.skylerlewis.litterrobot.litterrobotapp.api.insights.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CycleHistoryItem {

    String date;
    Long cyclesCompleted;

}
