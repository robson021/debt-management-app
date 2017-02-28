package robert.web.security.filters;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import robert.web.rest.svc.api.LoginAndRegisterCtrl;

public interface Validation {

	List<String> PUBLIC_URIS = Collections.unmodifiableList(Arrays.asList(
			"/",
			LoginAndRegisterCtrl.LOGIN_URL + "**", LoginAndRegisterCtrl.REGISTER_URL + "**", "/register/" + "**"));

	List<String> PUBLIC_FILES = Collections.unmodifiableList(Arrays.asList( //
            ".html", ".js", ".css", ".ico", ".map", ".jpg", ".png", ".jpeg"));

}
