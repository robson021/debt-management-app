package robert.web.rest.dto.asm;

import org.springframework.util.CollectionUtils;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.entities.User;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.MutualPaymentDTO;
import robert.web.rest.dto.PaymentDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentAssembler {

	public static Asset paymentDtoToAsset(PaymentDTO paymentDTO) {
		Asset asset = new Asset();
		asset.setDescription(paymentDTO.getDescription());
		asset.setAmount(paymentDTO.getAmount());
		asset.setBorrowerId(paymentDTO.getBorrowerId());
		asset.setBorrowerName(paymentDTO.getBorrowerName());
		asset.setBorrowerSurname(paymentDTO.getBorrowerSurname());

		return asset;
	}

	public static PaymentDTO convertToPaymentDTO(Asset asset) {
		PaymentDTO dto = new PaymentDTO();
		dto.setId(asset.getId());
		dto.setBorrowerName(asset.getBorrowerName());
		dto.setBorrowerSurname(asset.getBorrowerSurname());
		dto.setBorrowerId(asset.getBorrowerId());
		dto.setAmount(asset.getAmount());
		dto.setDescription(asset.getDescription());
		User user = asset.getUser();
		dto.setOwner(user.getName() + " " + user.getSurname());

		return dto;
	}

	public static List<PaymentDTO> convertToPaymentDTOs(Collection<Asset> assets) {
		if (CollectionUtils.isEmpty(assets))
			return Collections.emptyList();

		return assets.stream()
				.map(PaymentAssembler::convertToPaymentDTO)
				.collect(Collectors.toList());
	}

	public static MutualPayment convertMutualPaymentDTO(MutualPaymentDTO dto) {
		MutualPayment mutualPayment = new MutualPayment();
		mutualPayment.setAmount(dto.getAmount());
		mutualPayment.setDescription(dto.getDescription());

		return mutualPayment;
	}

	public static FeeDTO convertFeeToDTO(Fee fee) {
		FeeDTO dto = new FeeDTO();
		dto.setAmount(fee.getPayedFee());
		String name = fee.getUser().getName();
		String surname = fee.getUser().getSurname();
		dto.setUser(name + " " + surname);

		return dto;
	}

	public static List<FeeDTO> convertFeesToDTOs(Collection<Fee> payedFees) {
		return payedFees.stream()
				.map(PaymentAssembler::convertFeeToDTO)
				.collect(Collectors.toList());
	}
}
