package cc.powind.activiti.client.service;

import cc.powind.activiti.core.model.EventMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WorkflowListener {

    @Autowired
    private ConnectionFactory factory;

    private Channel channel;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CallbackService callbackService;

    public Channel getChannel() throws IOException, TimeoutException {
        if (channel != null && channel.isOpen()) {
            return channel;
        }
        return factory.newConnection().createChannel();
    }

    @PostConstruct
    public void init() {
        new Thread(this::listen).start();
    }

    public void listen() {

        System.out.println("开始监听");

        try {

            Channel channel = getChannel();

            channel.basicConsume("workflow_event", false, new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    EventMessage message = mapper.readValue(body, EventMessage.class);
                    callbackService.callback(channel, envelope.getDeliveryTag(), message);
                }
            });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
