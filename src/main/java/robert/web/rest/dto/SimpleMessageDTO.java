package robert.web.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleMessageDTO {

    private String message;

    public SimpleMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
