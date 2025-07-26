package ticketing.rookies.queue.config.kafka;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaTopicProperties kafkaTopicProperties;

    @Bean
    public NewTopic ticketingRequestTopic() {
        return TopicBuilder.name(kafkaTopicProperties.getRequestTopic())
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ticketingResponseTopic() {
        return TopicBuilder.name(kafkaTopicProperties.getResponseTopic())
                .partitions(3)
                .replicas(1)
                .build();
    }

}
