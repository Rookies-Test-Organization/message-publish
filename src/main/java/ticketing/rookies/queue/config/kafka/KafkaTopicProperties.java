package ticketing.rookies.queue.config.kafka;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.kafka.ticketing")
public class KafkaTopicProperties {
    private String requestTopic;
    private String responseTopic;

    public static String REQUEST_TOPIC;
    public static String RESPONSE_TOPIC;

    @PostConstruct
    public void init() {
        REQUEST_TOPIC = this.requestTopic;
        RESPONSE_TOPIC = this.responseTopic;
        System.out.println("KafkaTopicProperties initialized. Request Topic: " + REQUEST_TOPIC);
        System.out.println("KafkaTopicProperties initialized. Response Topic: " + RESPONSE_TOPIC);
    }


}
