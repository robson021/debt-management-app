package robert.web.rest.dto.asm;

import org.springframework.util.CollectionUtils;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.entities.User;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.PaymentDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentAssembler {

    private PaymentAssembler() {
    }

    public static Asset paymentDtoToAsset(PaymentDTO paymentDTO) {
        Asset asset = new Asset();
        asset.setDescription(paymentDTO.getDescription());
        asset.setAmount(paymentDTO.getAmount());
        asset.setBorrowerId(paymentDTO.getBorrowerId());
        asset.setBorrowerName(paymentDTO.getBorrowerName());
        asset.setBorrowerSurname(paymentDTO.getBorrowerSurname());

        return asset;
    }

    public static List<PaymentDTO> convertToPaymentDTOs(Collection<Asset> assets) {
        if (CollectionUtils.isEmpty(assets))
            return Collections.emptyList();

        return assets.stream()
                .map(PaymentAssembler::convertToPaymentDTO)
                .collect(Collectors.toList());
    }

    private static PaymentDTO convertToPaymentDTO(Asset asset) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(asset.getId());
        dto.setBorrowerName(asset.getBorrowerName());
        dto.setBorrowerSurname(asset.getBorrowerSurname());
        dto.setBorrowerId(asset.getBorrowerId());
        dto.setAmount(asset.getAmount());
        dto.setDescription(asset.getDescription());
        dto.setCreationDate(asset.getCreationDate());
        User user = asset.getUser();
        dto.setOwner(user.getName() + " " + user.getSurname());

        return dto;
    }

    public static MutualPayment convertMutualPaymentDTO(PaymentDTO dto) {
        MutualPayment mutualPayment = new MutualPayment();
        mutualPayment.setAmount(dto.getAmount());
        mutualPayment.setDescription(dto.getDescription());

        return mutualPayment;
    }

    public static List<PaymentDTO> convertToMutualPaymentDTO(Collection<MutualPayment> mutualPayments) {
        if (CollectionUtils.isEmpty(mutualPayments)) {
            return Collections.emptyList();
        }
        return mutualPayments.stream()
                .map(PaymentAssembler::convertMutualPaymentToDTO)
                .collect(Collectors.toList());

    }

    private static PaymentDTO convertMutualPaymentToDTO(MutualPayment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setDescription(payment.getDescription());

        List<FeeDTO> fees = convertFeesToDTOs(payment.getPayedFees());
        dto.setFees(fees);

        return dto;
    }

    public static List<FeeDTO> convertFeesToDTOs(Collection<Fee> payedFees) {
        if (CollectionUtils.isEmpty(payedFees))
            return Collections.emptyList();

        return payedFees.stream()
                .map(PaymentAssembler::convertFeeToDTO)
                .collect(Collectors.toList());
    }

    private static FeeDTO convertFeeToDTO(Fee fee) {
        FeeDTO dto = new FeeDTO();
        dto.setAmount(fee.getPayedFee());
        String name = fee.getUser().getName();
        String surname = fee.getUser().getSurname();
        dto.setUser(name + " " + surname);

        return dto;
    }
}
