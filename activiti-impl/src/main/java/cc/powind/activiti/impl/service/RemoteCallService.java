package cc.powind.activiti.impl.service;

import cc.powind.activiti.core.model.RequestMessage;
import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Method;

public class RemoteCallService {

    private BeanFactory factory;

    public void handle(RequestMessage message) {

        try {
            Object service = factory.getBean(message.getInterfaceName());
            Method method = service.getClass().getMethod(message.getMethodName(), message.getParameterTypes());
            method.invoke(service, message.getParameterValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BeanFactory getFactory() {
        return factory;
    }

    public void setFactory(BeanFactory factory) {
        this.factory = factory;
    }

    @Transactional
    public void call(Channel channel, long deliveryTag, RequestMessage message) throws IOException {
        handle(message);
        channel.basicAck(deliveryTag, false);
    }
}
