package robert.web.security.filters;

import robert.web.rest.svc.api.LoginAndRegisterCtrl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Validation {

	List<String> PUBLIC_URIS = Collections.unmodifiableList(Arrays.asList(
			"/",
			LoginAndRegisterCtrl.LOGIN_URL + "**",
			LoginAndRegisterCtrl.REGISTER_URL + "**"
	));

	List<String> PUBLIC_FILES = Collections.unmodifiableList(Arrays.asList( //
			".html", ".js", ".css", ".map", ".jpg", ".png", ".jpeg")
	);

}
