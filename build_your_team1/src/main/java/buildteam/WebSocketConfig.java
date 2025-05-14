package buildteam;

import org.hibernate.internal.build.AllowSysOut;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
    	
    	System.out.println("inside of configuremessagebroker");
        config.enableSimpleBroker("/topic"); // Topic for receiving messages
        config.setApplicationDestinationPrefixes("/app"); // Prefix for sending messages
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	System.out.println("inside of registersgompendpoints");
        registry.addEndpoint("/websocket")
        .withSockJS(); // Endpoint to establish WebSocket connection
    }
}


