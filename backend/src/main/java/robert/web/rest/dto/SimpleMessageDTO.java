package robert.web.rest.dto;

public final class SimpleMessageDTO {

	private String message;

	public String getMessage() {
		return message;
	}

	public SimpleMessageDTO() {
	}

	public SimpleMessageDTO(String message) {
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
