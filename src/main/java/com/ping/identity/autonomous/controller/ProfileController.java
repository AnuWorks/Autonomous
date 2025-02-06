package com.ping.identity.autonomous.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProfileController {
    Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @GetMapping("/myprofile")
    public ResponseEntity<Map<String, Object>> getProfile(
            @AuthenticationPrincipal OAuth2User user) {
        logger.info("**** Profile*** {}", user);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Unauthorized"));
        }


        Map<String, Object> profileData = new HashMap<>();
        if (user instanceof OidcUser oidcUser) {

            profileData.put("name", oidcUser.getFullName());
            profileData.put("email", oidcUser.getEmail());
            profileData.put("picture", oidcUser.getPicture());


        } else {
            profileData.put("name", user.getAttribute("name"));
            profileData.put("company", user.getAttribute("company"));
            profileData.put("bio", user.getAttribute("bio"));
        }

        return ResponseEntity.ok(profileData);



    }
}
