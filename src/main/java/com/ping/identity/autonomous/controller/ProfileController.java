package com.ping.identity.autonomous.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProfileController {

    @GetMapping("/myprofile")
    public ResponseEntity<Map<String, Object>> getProfile(@AuthenticationPrincipal OidcUser user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Unauthorized"));
        }

        Map<String, Object> profileData = new HashMap<>();
        profileData.put("name", user.getFullName());
        profileData.put("email", user.getEmail());
        profileData.put("picture", user.getPicture());

        return ResponseEntity.ok(profileData);

    }
}
