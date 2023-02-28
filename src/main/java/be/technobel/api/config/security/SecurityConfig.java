package be.technobel.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Scanner;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {

        http.csrf().disable();

        http.httpBasic().disable();

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(
            registry -> registry
                    .requestMatchers("/security/test/permit-all" ).permitAll()
                    .requestMatchers("/security/test/non-existent" ).denyAll()

                    .requestMatchers("/security/test/connected" ).authenticated()
                    .requestMatchers("/security/test/not-connected" ).anonymous()
                    // role USER => authority ROLE_USER
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
//                    .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                    //
//                    .requestMatchers(HttpMethod.GET, "/store/**").authenticated()
                    //
                    .requestMatchers( request -> request.getRequestURI().length() > 500 ).denyAll()

                    .requestMatchers(HttpMethod.POST, "/product/**").hasAnyRole("ADMIN", "GERANT")
                    .requestMatchers(HttpMethod.GET, "/store/**").authenticated()
                    .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN", "GERANT")

//                    .requestMatchers("/auth/**").permitAll()

                    .anyRequest().permitAll()

        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
