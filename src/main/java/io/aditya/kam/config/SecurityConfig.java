package io.aditya.kam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${io.aditya.kam.api-key.key}")
  private String principalRequestHeader;

  @Value("${io.aditya.kam.api-key.value}")
  private String principalRequestValue;

  //Creates in memory user
  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withDefaultPasswordEncoder()
        .username("user")
        .password("password")
        .roles("USER")
        .build());
    return manager;
  }

  // Here the API key wont be generated on its own, we need to provide it.
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    final ApiKeyAuthFilter filter = new ApiKeyAuthFilter(principalRequestHeader);
    filter.setAuthenticationManager((Authentication authentication) -> {
          final String principal = (String) authentication.getPrincipal();
          if (!principalRequestValue.equals(principal)) {
            throw new BadCredentialsException("User did not provide valid API Key");
          }
          authentication.setAuthenticated(true);
          return authentication;
        }
    );

//    "/v3/**","/v1/**", "/swagger-ui.html"
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/v3/**","/swagger**", "/h2-console/**")
        .permitAll()
        .and()
        .addFilter(filter).authorizeHttpRequests()
        .anyRequest().authenticated();

    return http.build();
  }


}
