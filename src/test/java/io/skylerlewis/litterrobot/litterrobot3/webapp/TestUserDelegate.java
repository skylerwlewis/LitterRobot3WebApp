package io.skylerlewis.litterrobot.litterrobot3.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.UserDelegate;
import io.skylerlewis.litterrobot.litterrobot3.webapp.api.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Primary
@Component
public class TestUserDelegate implements UserDelegate {

    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    public TestUserDelegate(@Autowired ObjectMapper mapper,
                            @Autowired ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResponseEntity<User> getUser() {
        ResponseEntity<User> response = null;
        String usersJsonPath = "unitTestJsonFiles/users.json";

        Resource resource = resourceLoader.getResource("classpath:" + usersJsonPath);
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no user content");
        }

        User user = null;
        try {
            user = mapper.readValue(resource.getInputStream(), User.class);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "there was a problem reading the users json file");
        }

        response = ResponseEntity.ok(user);

        return response;
    }
}
