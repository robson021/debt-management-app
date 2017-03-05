package robert.web.rest.dto;

public class SimpleMessageDTO {

    private final String message;

    public SimpleMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
