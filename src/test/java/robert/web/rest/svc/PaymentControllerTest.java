package robert.web.rest.svc;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import robert.SpringWebMvcTest;
import robert.TestConfig;
import robert.TestUtils;
import robert.db.dao.MainDao;
import robert.db.entities.User;
import robert.web.rest.dto.PaymentDTO;
import robert.web.session.api.UserDataProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestConfig.class)
public class PaymentControllerTest extends SpringWebMvcTest {

    @Autowired
    private MainDao dao;

    @Autowired
    private UserDataProvider userDataProvider;

    @Before
    public void init() throws Exception {
        super.initMockMvc();
    }

    @Test
    public void addAssetToTheUser() throws Exception {

        User user = dao.saveUser(TestUtils.getTestUser());
        User borrower = dao.saveUser(TestUtils.getTestUser());

        Mockito.when(userDataProvider.getEmail())
                .thenReturn(user.getEmail());
        Mockito.when(userDataProvider.getId())
                .thenReturn(user.getId());

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setBorrowerId(borrower.getId());
        paymentDTO.setDescription("Test test");
        paymentDTO.setAmount(55.);
        paymentDTO.setBorrowerName(borrower.getName());
        paymentDTO.setBorrowerSurname(borrower.getSurname());

        mockMvc.perform(post(PaymentController.ADD_ASSET_TO_THE_USER_URL).content(TestUtils.asJsonString(paymentDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        user = dao.findUser(user.getId());

        Assertions.assertThat(user.getAssets())
                .hasSize(1);
    }

}