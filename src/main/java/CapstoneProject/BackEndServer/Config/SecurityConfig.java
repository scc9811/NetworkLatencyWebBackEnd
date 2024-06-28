package CapstoneProject.BackEndServer.Config;


import CapstoneProject.BackEndServer.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
//                .cors(CorsConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        httpRequest -> httpRequest
                                .requestMatchers("/user/getUserNickName").authenticated()
                                .requestMatchers("/ping/getTestResult").authenticated()
                                .requestMatchers("/ping/storeResult").authenticated()


                                .anyRequest().permitAll()
                )
//                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
                .addFilterAfter(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
