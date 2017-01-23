package robert.web.rest.dto.asm;

import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.web.rest.dto.PaymentDTO;

public class PaymentAssembler {

    public static Asset addDebtorToTheUser(PaymentDTO paymentDTO, User user) {
        Asset asset = new Asset();
        asset.setDescription(paymentDTO.getDescription());
        asset.setAmount(paymentDTO.getAmount());
        asset.setBorrowerId(paymentDTO.getBorrowerId());

        asset.setUser(user);
        //user.addAsset(asset);

        return asset;
    }
}
