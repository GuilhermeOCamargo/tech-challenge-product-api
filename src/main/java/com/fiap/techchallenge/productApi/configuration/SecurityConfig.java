//package com.fiap.techchallenge.productApi.configuration;
//
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;
//import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
//
//@Configuration
//@EnableWebSecurity(debug = true)
//public class SecurityConfig {
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .logout(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(antMatcher( "/health")).permitAll()
//                        .requestMatchers(antMatcher( "/swagger-ui/**")).permitAll()
//                        .requestMatchers(antMatcher( "/v3/api-docs/**")).permitAll()
//                        .requestMatchers(antMatcher( HttpMethod.PUT,"/order/**")).hasAnyRole("grp_admin")
//                        .requestMatchers(antMatcher( "/order/**")).permitAll()
//                        .requestMatchers(antMatcher( HttpMethod.POST,"/products/**")).hasAnyRole("grp_admin")
//                        .requestMatchers(antMatcher( HttpMethod.PUT,"/products/**")).hasAnyRole("grp_admin")
//                        .requestMatchers(antMatcher( HttpMethod.DELETE,"/products/**")).hasAnyRole("grp_admin")
//                        .requestMatchers(antMatcher( HttpMethod.GET, "/products/**")).permitAll()
//                        .requestMatchers(PathRequest.toH2Console()).permitAll()
//                        .requestMatchers(antMatcher("/customer/**")).permitAll()
//                        .requestMatchers(antMatcher("/**")).authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//                )
//                .build();
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("cognito:groups");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }
//
//}