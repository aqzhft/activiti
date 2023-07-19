package cc.powind.activiti.impl.config;

import cc.powind.activiti.impl.properties.ImplProperties;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Autowired
    private ImplProperties properties;

    @Bean
    public ConnectionFactory factory() {
        ImplProperties.Rabbitmq rabbitmq = properties.getRabbitmq();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost(rabbitmq.getVirtualHost());
        factory.setHost(rabbitmq.getHost());
        factory.setPort(rabbitmq.getPort());
        factory.setUsername(rabbitmq.getUsername());
        factory.setPassword(rabbitmq.getPassword());
        return factory;
    }
}
