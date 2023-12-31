package io.skylerlewis.litterrobot.litterrobot3.webapp;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class UiController {

    @RequestMapping(value = {
            "/",
            "robot/{robotId}",
            "robot/{robotId}/activity",
            "robot/{robotId}/insights",
            "robot/{robotId}/settings",
            "/user",
            "/devices",
            "/settings",
    })
    public String getIndex(HttpServletRequest inRequest) {
        return "/index.html";
    }

}
