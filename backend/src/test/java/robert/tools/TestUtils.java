package robert.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.test.util.ReflectionTestUtils;
import robert.db.entities.User;
import robert.web.rest.dto.PaymentDTO;

import java.math.BigDecimal;

public class TestUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static long idCounter = 1;

    public static String asJsonString(final Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) throws Exception {
        return mapper.readValue(json, clazz);
    }

    public static User generateNewUser() {
        User user = new User();
        user.setEmail((RandomStringUtils.randomAlphabetic(10) + "@test.pl").toLowerCase());
        user.setName(RandomStringUtils.randomAlphabetic(8));
        user.setSurname(RandomStringUtils.randomAlphabetic(8));
        user.setPassword("Passwd.123");
        user.setAccountNo(RandomStringUtils.randomNumeric(12));
        return user;
    }

    public static User generateNewUserWithId() {
        User user = generateNewUser();
        ReflectionTestUtils.setField(user, "id", idCounter++);
        return user;
    }

    public static PaymentDTO generatePayment(User borrower) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(BigDecimal.valueOf(RandomUtils.nextDouble(1, 9999)));
        paymentDTO.setDescription("test payment");
        paymentDTO.setBorrowerId(borrower.getId());
        paymentDTO.setBorrowerName(borrower.getName());
        paymentDTO.setBorrowerSurname(borrower.getSurname());

        return paymentDTO;
    }

}
