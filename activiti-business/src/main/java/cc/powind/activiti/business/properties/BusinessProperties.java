package cc.powind.activiti.business.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "business")
public class BusinessProperties {

    // 消息队列
    private Rabbitmq rabbitmq = new Rabbitmq();

    @Getter
    @Setter
    public static class Rabbitmq {

        private String virtualHost = "/";

        private String host = "localhost";

        private int port = 5672;

        private String username;

        private String password;
    }
}
