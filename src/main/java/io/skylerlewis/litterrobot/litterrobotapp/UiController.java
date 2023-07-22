package io.skylerlewis.litterrobot.litterrobotapp;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class UiController {

    @RequestMapping(value = { "/", "/activity","/insights","/settings"  })
    public String getIndex(HttpServletRequest inRequest) {
        return "/index.html";
    }

}
