package CapstoneProject.BackEndServer.Config;

import CapstoneProject.BackEndServer.Handler.NetworkLatencyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {


    private final NetworkLatencyWebSocketHandler networkLatencyWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(networkLatencyWebSocketHandler, "/networkLatencyWebSocketConnection").setAllowedOrigins("http://scc9811.site:3000");
    }
}
