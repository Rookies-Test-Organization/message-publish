package ticketing.rookies.queue.domain.ticketing.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ticketing.rookies.queue.config.kafka.KafkaTopicConfig;
import ticketing.rookies.queue.config.kafka.KafkaTopicProperties;
import ticketing.rookies.queue.domain.ticketing.dto.TicketingRequest;
import ticketing.rookies.queue.domain.ticketing.dto.TicketingResponse;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicProperties topicProperties;

    public void sendTicketingRequest(TicketingRequest request) {
        logger.info("Sending ticketing request: {}", request);
        kafkaTemplate.send(KafkaTopicProperties.REQUEST_TOPIC, request.getRequestId(), request);
    }

    public void sendTicketingResponse(TicketingResponse response) {
        logger.info("Sending ticketing response: {}", response);
        kafkaTemplate.send(KafkaTopicProperties.RESPONSE_TOPIC, response.getUserId(), response);
    }
}