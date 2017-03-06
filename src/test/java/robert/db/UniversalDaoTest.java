package robert.db;

import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import robert.SpringTest;
import robert.TestUtils;
import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.web.rest.dto.PaymentDTO;

public class UniversalDaoTest extends SpringTest {

    @Autowired
    private UniversalDao dao;

    @Test
    public void cancelDebt() throws Exception {
        User lender = dao.saveEntity(TestUtils.generateNewUser(), User.class);
        User borrower = dao.saveEntity(TestUtils.generateNewUser(), User.class);

        dao.addDebtor(lender.getId(), generatePayment(borrower));

        Set<Asset> assets = dao.findUserDebtors(lender.getId());
        Assertions.assertThat(assets)
                .hasSize(1);

        long assetId = assets.iterator()
                .next()
                .getId();

        dao.cancelDebt(assetId, lender.getId());

        // after delete
        assets = dao.findUserDebtors(lender.getId());
        Assertions.assertThat(assets)
                .hasSize(0);
    }

    @Test
    public void addDebtor() throws Exception {
        User lender = dao.saveEntity(TestUtils.generateNewUser(), User.class);
        User borrower = dao.saveEntity(TestUtils.generateNewUser(), User.class);

        dao.addDebtor(lender.getId(), generatePayment(borrower));
        dao.addDebtor(lender.getId(), generatePayment(borrower));

        Set<Asset> debtors = dao.findUserDebtors(lender.getId());

        Assertions.assertThat(debtors)
                .hasSize(2);

    }

    private PaymentDTO generatePayment(User borrower) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(RandomUtils.nextDouble(1, 9999));
        paymentDTO.setDescription("test payment");
        paymentDTO.setBorrowerId(borrower.getId());
        paymentDTO.setBorrowerName(borrower.getName());
        paymentDTO.setBorrowerSurname(borrower.getSurname());

        return paymentDTO;
    }

}