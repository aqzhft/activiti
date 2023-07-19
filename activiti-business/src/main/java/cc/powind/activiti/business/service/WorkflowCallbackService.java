package cc.powind.activiti.business.service;

import cc.powind.activiti.client.service.CallbackService;
import cc.powind.activiti.core.model.EventMessage;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkflowCallbackService implements CallbackService {

    @Autowired
    private List<? extends WorkflowHandler> handlers;

    @Override
    @Transactional
    public void callback(Channel channel, Long deliveryTag, EventMessage message) {
        try {
            handle(message);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handle(EventMessage message) {

        if (handlers == null || handlers.isEmpty()) {
            return;
        }

        for (WorkflowHandler handler : handlers) {
            if (handler.supportProcessKey() != null && handler.supportProcessKey().equalsIgnoreCase(message.getProcessKey())) {
                handler.handle(message.getBusinessKey());
            }
        }
    }
}
