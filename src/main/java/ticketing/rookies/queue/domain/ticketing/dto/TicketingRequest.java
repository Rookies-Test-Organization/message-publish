package ticketing.rookies.queue.domain.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketingRequest {
    private String requestId;
    private String userId;
    private LocalDateTime requestTime;

    @Override
    public String toString() {
        return "TicketingRequest{" +
                "requestId='" + requestId + '\'' +
                ", userId='" + userId + '\'' +
                ", requestTime=" + requestTime +
                '}';
    }
}