package cc.powind.activiti.business.config;

import cc.powind.activiti.business.properties.BusinessProperties;
import cc.powind.activiti.client.service.WorkflowListener;
import cc.powind.activiti.client.service.WorkflowService;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkflowConfig {

    @Autowired
    private BusinessProperties properties;

    @Bean
    public ConnectionFactory factory() {
        BusinessProperties.Rabbitmq rabbitmq = properties.getRabbitmq();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost(rabbitmq.getVirtualHost());
        factory.setHost(rabbitmq.getHost());
        factory.setPort(rabbitmq.getPort());
        factory.setUsername(rabbitmq.getUsername());
        factory.setPassword(rabbitmq.getPassword());
        return factory;
    }

    @Bean
    public WorkflowService workflowService() {
        return new WorkflowService();
    }

    @Bean
    public WorkflowListener workflowListener() {
        return new WorkflowListener();
    }
}
