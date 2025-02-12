package com.ping.identity.autonomous;

import com.ping.identity.autonomous.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void shouldReturnAccessToken() throws Exception {
        // Mock an OIDC ID token
        OidcIdToken idToken = new OidcIdToken(
                "mock-token-value",
                Instant.now(),
                Instant.now().plusSeconds(3600),
                Collections.singletonMap("sub", "test-user")
        );

        DefaultOidcUser oidcUser = new DefaultOidcUser(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                idToken
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/access-token")
                        .with(oidcLogin().oidcUser(oidcUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.access_token").value("mock-token-value"));
    }
}
