package ticketing.rookies.queue.domain.ticketing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketing.rookies.queue.domain.ticketing.dto.TicketingRequest;
import ticketing.rookies.queue.domain.ticketing.service.KafkaProducerService;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/ticketing")
@RequiredArgsConstructor
public class TicketingController {

    private final KafkaProducerService kafkaProducerService;


    @PostMapping("/request-entry")
    public ResponseEntity<String> requestEntry(@RequestBody String userId) {
        String requestId = UUID.randomUUID().toString();
        TicketingRequest request = new TicketingRequest(requestId, userId, LocalDateTime.now());

        kafkaProducerService.sendTicketingRequest(request);

        return ResponseEntity.ok("Ticketing entry request submitted for user " + userId + ". Request ID: " + requestId);
    }
}