package io.skylerlewis.litterrobot.litterrobotapp.api.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Settings {

    @JsonProperty("all_notifications")
    private String allNotifications;
    @JsonProperty("general_notifications")
    private String generalNotifications;
    @JsonProperty("fault_notifications")
    private String faultNotifications;
    @JsonProperty("DFI_notifications")
    private String DFINotifications;
    @JsonProperty("CCC_notifications")
    private String CCCNotifications;
    @JsonProperty("CSI_notifications")
    private String CSINotifications;
    @JsonProperty("CSF_notifications")
    private String CSFNotifications;
    @JsonProperty("PD_notifications")
    private String PDNotifications;
    @JsonProperty("OTF_notifications")
    private String OTFNotifications;
    @JsonProperty("DHF_notifications")
    private String DHFNotifications;
    @JsonProperty("BR_notifications")
    private String BRNotifications;
    @JsonProperty("offline_notifications")
    private String offlineNotifications;
    private String userId;
}
