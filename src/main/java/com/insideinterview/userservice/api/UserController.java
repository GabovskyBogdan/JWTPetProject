package com.insideinterview.userservice.api;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insideinterview.userservice.domain.AppUser;
import com.insideinterview.userservice.domain.Message;
import com.insideinterview.userservice.domain.Role;
import com.insideinterview.userservice.service.MessageService;
import com.insideinterview.userservice.service.UserService;
import com.insideinterview.userservice.utility.TokenDecoder;
import com.insideinterview.userservice.utility.TokenGenerator;
import com.insideinterview.userservice.utility.TokensMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer_")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer_".length());
                DecodedJWT decodedJWT = TokenDecoder.getDecodedJWT(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);
                String access_token = new TokenGenerator().refreshAccessToken(user, request);
                TokensMapper.mapTokens(access_token, refresh_token, response);

            } catch (Exception exception) {
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        return ResponseEntity.ok().body(messageService.getMessages());
    }

    @PostMapping("/messages/save")
    public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
        String users = Arrays.toString(userService.getUsers().toArray());
        if (!users.contains(message.getUsername())) {
            return ResponseEntity.badRequest().build();
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/messages/save").toUriString());
        return ResponseEntity.created(uri).body(messageService.saveMessage(message));
    }

    @GetMapping("/messages/save")
    public ResponseEntity<List<Message>> saveMessageWithHistory(@RequestBody Message message) {
        if (message.getMessage().contains("history ")) {
            int messagesToReturn = Integer.parseInt(message.getMessage().substring("history ".length()));
            List<Message> messages = messageService.getMessages();
            Collections.reverse(messages);
            List<Message> result = messages.subList(0, messagesToReturn);
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.badRequest().build();
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}