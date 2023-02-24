package be.technobel.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Scanner;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.httpBasic();

        http.authorizeHttpRequests(
            registry -> registry
                    .requestMatchers("/security/test/permit-all" ).permitAll()
                    .requestMatchers("/security/test/non-existent" ).denyAll()

                    .requestMatchers("/security/test/connected" ).authenticated()
                    .requestMatchers("/security/test/not-connected" ).anonymous()

                    .requestMatchers("/security/test/role_user" ).hasRole("USER")
                    .requestMatchers("/security/test/any_role" ).hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/security/test/has_authority_role_user" ).hasAuthority("ROLE_USER")
                    .requestMatchers("/security/test/has_any_authority").hasAnyAuthority("ROLE_ADMIN", "TEST", "READ")
                    // /** se met à la fin et correspond à de 0 à N segments
                    //security/test/truc
                    //security/test/truc/machin
                    //security/test/truc/machin/bidule
                    .requestMatchers("/security/test/**").denyAll()
                    // /* représente n'importe qu'elle valeur pour 1 segment
                    // ? représente 1 caractère
                    .requestMatchers("/security/test/*", "/security/t?st").denyAll()

                    //
                    .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                    //
                    .requestMatchers(HttpMethod.GET, "/store/**").authenticated()
                    //
                    .requestMatchers( request -> request.getRequestURI().length() > 50 ).denyAll()
                    .anyRequest().permitAll()
                // role USER => authority ROLE_USER

        );

        return http.build();
    }

}
