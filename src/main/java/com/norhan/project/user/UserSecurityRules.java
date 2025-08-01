package com.norhan.project.user;

import com.norhan.project.common.SecurityRules;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class UserSecurityRules implements SecurityRules {
    @Override
    public void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {

//       registry.requestMatchers("/auth/login").permitAll()
//                .requestMatchers("/users").permitAll();

       registry.requestMatchers(HttpMethod.POST, "/users").permitAll();
    }
}
