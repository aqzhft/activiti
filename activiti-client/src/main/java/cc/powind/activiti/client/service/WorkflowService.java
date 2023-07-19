package cc.powind.activiti.client.service;

import cc.powind.activiti.core.model.RequestMessage;
import cc.powind.activiti.core.model.UserTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class WorkflowService {

    @Autowired
    private ConnectionFactory factory;

    @Autowired
    private ObjectMapper mapper;

    private Channel channel;

    @Autowired
    private RestTemplate restTemplate;

    public Channel getChannel() throws IOException, TimeoutException {
        if (channel != null && channel.isOpen()) {
            return channel;
        }
        return factory.newConnection().createChannel();
    }

    // 开启流程并且完成一个指定的任务
    public void startProcessAndComplete(String processKey, String businessKey, String name, String userId, String taskName) {

        RequestMessage message = new RequestMessage();
        message.setInterfaceName("userTaskService");
        message.setMethodName("complete");
        message.setParameterTypes(new Class[] {String.class, String.class, String.class, String.class, String.class});
        message.setParameterValue(new Object[] {processKey, businessKey, name, userId, taskName});

        remote(message);
    }

    // 删除掉一个正在运行重的流程
    public void deleteProcess(String processKey, String businessKey, String reason) {

        RequestMessage message = new RequestMessage();
        message.setInterfaceName("instanceService");
        message.setMethodName("delete");
        message.setParameterTypes(new Class[] {String.class, String.class, String.class});
        message.setParameterValue(new Object[] {processKey, businessKey, reason});

        remote(message);
    }

    // 完成任务
    public void complete(String taskId, String comment) {
        RequestMessage message = new RequestMessage();
        message.setInterfaceName("userTaskService");
        message.setMethodName("complete");
        message.setParameterTypes(new Class[] {String.class, String.class});
        message.setParameterValue(new Object[] {taskId, comment});

        remote(message);
    }

    private void remote(RequestMessage message) {
        try {

            Channel channel = getChannel();

            String m = mapper.writeValueAsString(message);

            channel.confirmSelect();
            channel.queueDeclare("remote_call", true, false, false, null);
            channel.basicPublish("", "remote_call", null, m.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserTask> findProcessKeyAndBusinessKey(String processKey, String businessKey) {

        ResponseEntity<List<UserTask>> exchange = restTemplate.exchange("http://localhost:8080/task?processKey=" + processKey + "&businessKey=" + businessKey, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserTask>>() {
        });

        return exchange.getBody();
    }
}
