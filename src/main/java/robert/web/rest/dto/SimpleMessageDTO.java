package robert.web.rest.dto;

public class SimpleMessageDTO {

    private String message;

    public SimpleMessageDTO() {
    }

    public SimpleMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
