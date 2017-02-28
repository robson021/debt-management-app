package robert.web.controllers;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import robert.db.dao.MainDao;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.session.api.UserDataProvider;

@Controller
public class ViewsController {

	private static final Logger log = Logger.getLogger(ViewsController.class);

	private final UserDataProvider userDataProvider;

	private final MainDao dao;

	@Autowired
	public ViewsController(UserDataProvider userDataProvider, MainDao dao) {
		this.userDataProvider = userDataProvider;
		this.dao = dao;
	}

	@RequestMapping("/")
	public String getHomePage() {
		return "index";
	}

	@RequestMapping("/register")
	public String getRegisterPage() {
		return "register";
	}

	@RequestMapping("/my-debts")
	public String getMyDebts(Model model) {
		List<PaymentDTO> myDebts = PaymentAssembler.convertToPaymentDTOs(dao.getMyDebts());
		model.addAttribute("myDebts", Collections.singletonList(getExampleDTO()));
		//model.addAttribute("myDebts", myDebts);
		return "my-debts";
	}

	@RequestMapping("/my-debtors")
	public String getMyDebtors(Model model) {
		model.addAttribute("myDebtors", Collections.singletonList(getExampleDTO()));
		return "my-debtors";
	}

	private PaymentDTO getExampleDTO() {
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setAmount(33.45);
		paymentDTO.setDescription("dadsada");
		paymentDTO.setBorrowerName("Robert");
		paymentDTO.setBorrowerSurname("Trebor");
		return paymentDTO;
	}
}
