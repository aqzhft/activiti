package cc.powind.activiti.client.service;

import cc.powind.activiti.core.model.EventMessage;
import com.rabbitmq.client.Channel;

public interface CallbackService {

    void callback(Channel channel, Long deliveryTag, EventMessage message);
}
