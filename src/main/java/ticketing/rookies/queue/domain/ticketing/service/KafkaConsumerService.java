// src/main/java/com/example/ticketingqueue/service/KafkaConsumerService.java
package ticketing.rookies.queue.domain.ticketing.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ticketing.rookies.queue.config.kafka.KafkaTopicConfig;
import ticketing.rookies.queue.config.kafka.KafkaTopicProperties;
import ticketing.rookies.queue.domain.ticketing.dto.TicketingRequest;
import ticketing.rookies.queue.domain.ticketing.dto.TicketingResponse;

import java.util.concurrent.atomic.AtomicInteger;

import static ticketing.rookies.queue.config.kafka.KafkaTopicProperties.RESPONSE_TOPIC;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final KafkaProducerService kafkaProducerService;

    private final AtomicInteger queueCounter = new AtomicInteger(0);
    private static final int MAX_ADMITTED_COUNT = 5; // 최대 입장 가능 인원수 (예시)
    private final AtomicInteger admittedCount = new AtomicInteger(0);
    private static final long PROCESSING_DELAY_MS = 10000;
    private final KafkaTopicProperties topicProperties;

    @KafkaListener(topics = "${app.kafka.ticketing.request-topic", groupId = "ticketing-queue-group")
    public void listenTicketingRequests(TicketingRequest request) {
        logger.info("Received ticketing request: {}", request);

        try {
            // 여기에 인위적인 지연을 추가합니다.
            // 이로 인해 각 메시지 처리가 지정된 시간만큼 지연됩니다.
            logger.info("Simulating processing delay for request {}. Sleeping for {}ms...", request.getRequestId(), PROCESSING_DELAY_MS);
            Thread.sleep(PROCESSING_DELAY_MS);
            logger.info("Finished simulating delay for request {}.", request.getRequestId());
        } catch (InterruptedException e) {
            // 스레드가 대기 중에 방해받았을 경우 예외 처리
            Thread.currentThread().interrupt(); // 인터럽트 상태를 다시 설정
            logger.warn("Processing delay interrupted for request {}: {}", request.getRequestId(), e.getMessage());
        }

        TicketingResponse response;
        int currentQueueNumber = queueCounter.incrementAndGet();

        if (admittedCount.get() < MAX_ADMITTED_COUNT) {
            admittedCount.incrementAndGet();
            response = new TicketingResponse(
                    request.getRequestId(),
                    request.getUserId(),
                    "ADMITTED",
                    currentQueueNumber,
                    "입장 가능합니다. 티켓 구매를 진행해주세요."
            );
            logger.info("User {} admitted. Current admitted: {}", request.getUserId(), admittedCount.get());
        } else {
            response = new TicketingResponse(
                    request.getRequestId(),
                    request.getUserId(),
                    "QUEUED",
                    currentQueueNumber,
                    "현재 대기열에 있습니다. 순서가 되면 알림이 갈 것입니다."
            );
            logger.info("User {} queued. Queue number: {}", request.getUserId(), currentQueueNumber);
        }

        kafkaProducerService.sendTicketingResponse(response);
    }

    @KafkaListener(topics = "${app.kafka.ticketing.request-topic}", groupId = "ticketing-response-monitor-group")
    public void listenTicketingResponses(TicketingResponse response) {
        logger.info("Received ticketing response for user {}: Status={}, QueueNumber={}, Message={}",
                response.getUserId(), response.getStatus(), response.getQueueNumber(), response.getMessage());
    }
}