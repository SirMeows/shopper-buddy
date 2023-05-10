package com.he.engelund.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuth2LoginSecurityConfig {

/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login(oauth2 -> oauth2
                        .clientRegistrationRepository(this.clientRegistrationRepository())
                        .authorizedClientRepository(this.authorizedClientRepository())
                        .authorizedClientService(this.authorizedClientService())
                        .loginPage("/login")
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri(this.authorizationRequestBaseUri())
                                .authorizationRequestRepository(this.authorizationRequestRepository())
                                .authorizationRequestResolver(this.authorizationRequestResolver())
                        )
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri(this.authorizationResponseBaseUri())
                        )
                        .tokenEndpoint(token -> token
                                .accessTokenResponseClient(this.accessTokenResponseClient())
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userAuthoritiesMapper(this.userAuthoritiesMapper())
                                .userService(this.oauth2UserService())
                                .oidcUserService(this.oidcUserService())
                        )
                );
        return http.build();
    }*/
}
