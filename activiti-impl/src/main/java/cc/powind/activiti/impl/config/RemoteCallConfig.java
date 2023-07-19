package cc.powind.activiti.impl.config;

import cc.powind.activiti.core.model.RequestMessage;
import cc.powind.activiti.impl.service.RemoteCallService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Configuration
public class RemoteCallConfig {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ConnectionFactory factory;

    @Autowired
    private ObjectMapper mapper;

    private Channel channel;

    public RemoteCallService remoteCallService = new RemoteCallService();

    @PostConstruct
    public void init() {
        remoteCallService.setFactory(beanFactory);
        Executors.newScheduledThreadPool(1).schedule(this::consume, 10, TimeUnit.SECONDS);
    }

    public Channel getChannel() throws IOException, TimeoutException {
        if (channel != null && channel.isOpen()) {
            return channel;
        }
        channel = factory.newConnection().createChannel();
        return channel;
    }

    public void consume() {

        System.out.println("开始监听远程调用");

        try {
            Channel channel = getChannel();
            channel.basicConsume("remote_call", false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    RequestMessage message = mapper.readValue(body, RequestMessage.class);
                    remoteCallService.call(channel, envelope.getDeliveryTag(), message);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
