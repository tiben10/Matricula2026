package com.depazsotelo.matricula.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // IMPORTANTE
import org.springframework.web.cors.CorsConfigurationSource; // IMPORTANTE
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // IMPORTANTE
import java.util.Arrays; // IMPORTANTE

@EnableMethodSecurity // MEJORA: habilita @PreAuthorize a nivel de metodo
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. ¡ACTIVAMOS EL CORS AQUÍ!
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        // MEJORA: Director = solo consulta (GET). Antes cualquier rol autenticado
                        // podía hacer cualquier operación con solo estar logueado.
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/**")
                        .hasAnyRole("SUPERUSUARIO", "DIRECTOR", "SECRETARIA")
                        // MEJORA: escritura (crear/editar/eliminar) solo para Superusuario y Secretaria
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/**")
                        .hasAnyRole("SUPERUSUARIO", "SECRETARIA")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/**")
                        .hasAnyRole("SUPERUSUARIO", "SECRETARIA")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/**")
                        .hasAnyRole("SUPERUSUARIO", "SECRETARIA")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 2. LE DECIMOS EXACTAMENTE A QUIÉN DEJAR PASAR
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Le damos permiso al puerto de tu Vite (React)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}