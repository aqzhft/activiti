package cc.powind.activiti.impl.config;

import cc.powind.activiti.core.model.EventMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyConfigurator implements ProcessEngineConfigurator {

    @Autowired
    private ConnectionFactory factory;

    @Autowired
    private ObjectMapper mapper;

    private Channel channel;

    public Channel getChannel() throws Exception {
        if (channel != null && channel.isOpen()) {
            return channel;
        }
        return factory.newConnection().createChannel();
    }

    @PostConstruct
    public void init() {

        try {

            Channel channel = getChannel();

            // 创建远程调用queue
            channel.queueDeclare("remote_call", true, false, false, null);

            // 事件监听服务 延迟队列
            Map<String, Object> args = new HashMap<>();
            args.put("x-dead-letter-exchange", "workflow");
            args.put("x-dead-letter-routing-key", "workflow_event");
            args.put("x-message-ttl", 5000);
            channel.exchangeDeclare("workflow", "fanout");
            channel.queueDeclare("workflow_event_delay", true, false, false, args);
            channel.queueBind("workflow_event", "workflow", "workflow_event");

            // 死信队列 普通队列
            channel.queueDeclare("workflow_event", true, false, false, null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void beforeInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void configure(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.getEventDispatcher().addEventListener(new ActivitiEventListener() {

            @Override
            public void onEvent(ActivitiEvent event) {

                System.out.println("任务事件发生器 : " + event.getType());

                // todo 流程刚启动需要将默认的任务配置信息填入流程
                config(event);
            }

            @Override
            public boolean isFailOnException() {
                return false;
            }
        });
    }

    private void config(ActivitiEvent event) {
        if (ActivitiEventType.TASK_CREATED.equals(event.getType())) {
            ActivitiEntityEventImpl impl = (ActivitiEntityEventImpl) event;
            TaskEntityImpl entity = (TaskEntityImpl) impl.getEntity();

            EventMessage message = new EventMessage();
            message.setBusinessKey(entity.getBusinessKey());
            message.setProcessKey(entity.getExecution().getProcessDefinitionKey());
            message.setEventKey("TASK_CREATED");

            notify(message);
        } else if (ActivitiEventType.PROCESS_COMPLETED.equals(event.getType())) {
            ActivitiEntityEventImpl impl = (ActivitiEntityEventImpl) event;
            TaskEntityImpl entity = (TaskEntityImpl) impl.getEntity();

            EventMessage message = new EventMessage();
            message.setBusinessKey(entity.getBusinessKey());
            message.setProcessKey(entity.getExecution().getProcessDefinitionKey());
            message.setEventKey("PROCESS_COMPLETED");

            notify(message);
        }
    }

    private void notify(EventMessage message) {
        try {
            Channel channel = getChannel();
            String m = mapper.writeValueAsString(message);
            channel.confirmSelect();
            channel.basicPublish("", "workflow_event_delay", null, m.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
