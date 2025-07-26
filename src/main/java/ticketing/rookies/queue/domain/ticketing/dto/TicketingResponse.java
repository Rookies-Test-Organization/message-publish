// src/main/java/com/example/ticketingqueue/model/TicketingResponse.java
package ticketing.rookies.queue.domain.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketingResponse {
    private String requestId;
    private String userId;
    private String status;
    private Integer queueNumber;
    private String message;

    @Override
    public String toString() {
        return "TicketingResponse{" +
               "requestId='" + requestId + '\'' +
               ", userId='" + userId + '\'' +
               ", status='" + status + '\'' +
               ", queueNumber=" + queueNumber +
               ", message='" + message + '\'' +
               '}';
    }
}