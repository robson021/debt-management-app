package robert.web.rest.dto.asm;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import robert.db.entities.Asset;
import robert.web.rest.dto.PaymentDTO;

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
