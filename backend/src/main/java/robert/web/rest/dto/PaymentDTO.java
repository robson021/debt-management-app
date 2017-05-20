package robert.web.rest.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

	private long id;

	private long borrowerId;

	private String borrowerName;

	private String borrowerSurname;

	private String owner;

	private double amount;

	private String description;

	private List<FeeDTO> fees;
}
