package com.he.engelund.api;

import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
public class AuthenticationController {

    @GetMapping("/api/auth")
    public String login(HttpServletRequest request,
                        @Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId) {


        // Build the authorisation request URI
        var authRequestUri = UriComponentsBuilder.fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", getRedirectUri(request))
                .queryParam("response_type", "code")
                .queryParam("scope", "openid email profile") // this has already been defined in properties
                .queryParam("state", getState())
                .build().toUriString();

        return "redirect:" + authRequestUri;
    }

    private String getRedirectUri(HttpServletRequest request) {
        // Get the base URL of the application
        var baseUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
        return baseUrl + "/login/oauth2/code/google";
    }

    private String getState() {
        // Generate a random state value for CSRF protection
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    @PostMapping("/login/oauth2/code/google")
    public String oauth2Callback(HttpServletRequest request, @RequestParam("code") String code,
                                 @Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId,
                                 @Value("spring.security.oauth2.client.registration.google.client-secret}") String clientSecret) {

        // Build the request body for the token request
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", getRedirectUri(request));

        // Send the token request to Google's token endpoint
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<GoogleTokenResponse> responseEntity = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token", entity, GoogleTokenResponse.class);

        // Handle the token response
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            GoogleTokenResponse tokenResponse = responseEntity.getBody();
            // Use the access token and refresh token as needed
        } else {
            // Handle the error response
        }

        // Redirect the user to the home page or another page as needed
        return "redirect:/";
    }
}
