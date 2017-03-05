package robert.web.rest.svc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import robert.web.security.JwtUtils;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

    @RequestMapping("/is-admin")
    public Boolean checkIfAdmin(HttpServletRequest request) {
        return JwtUtils.isAdmin(request);
    }

}
