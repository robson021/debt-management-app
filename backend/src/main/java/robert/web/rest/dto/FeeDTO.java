package robert.web.rest.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeDTO {

    private String user;

    private BigDecimal amount;

}
