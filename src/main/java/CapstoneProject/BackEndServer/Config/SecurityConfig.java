package CapstoneProject.BackEndServer.Config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        httpRequest -> httpRequest
                                .requestMatchers("/user/getUserNickName").authenticated()
                                .requestMatchers("/ping/getTestResult").authenticated()
                                .requestMatchers("/ping/storeResult").authenticated()


                                .anyRequest().permitAll()
                )
                .addFilterAfter(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
