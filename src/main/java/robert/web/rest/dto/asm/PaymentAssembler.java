package robert.web.rest.dto.asm;

import org.springframework.util.CollectionUtils;
import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.web.rest.dto.PaymentDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentAssembler {

	public static Asset addDebtorToTheUser(PaymentDTO paymentDTO, User user) {
		Asset asset = new Asset();
		asset.setDescription(paymentDTO.getDescription());
		asset.setAmount(paymentDTO.getAmount());
		asset.setBorrowerId(paymentDTO.getBorrowerId());
		asset.setBorrowerName(paymentDTO.getBorrowerName());
		asset.setBorrowerSurname(paymentDTO.getBorrowerSurname());

		asset.setUser(user);
		//user.addAsset(asset);

		return asset;
	}

	public static PaymentDTO convertToPaymentDTO(Asset asset) {
		PaymentDTO dto = new PaymentDTO();
		dto.setBorrowerName(asset.getBorrowerName());
		dto.setBorrowerSurname(asset.getBorrowerSurname());
		dto.setBorrowerId(asset.getBorrowerId());
		dto.setAmount(asset.getAmount());
		dto.setDescription(asset.getDescription());
		return dto;
	}

	public static List<PaymentDTO> convertToPaymentDTOs(Collection<Asset> assets) {
		if (CollectionUtils.isEmpty(assets))
			return Collections.emptyList();

		return assets.stream()
				.map(PaymentAssembler::convertToPaymentDTO)
				.collect(Collectors.toList());
	}
}
