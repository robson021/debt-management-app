package robert.web.security.auth;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import robert.exeptions.AuthException;
import robert.web.request.data.UserDataProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDataProvider userDataProvider;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authHeaderValue = request.getHeader("Authorization");

        if ( authHeaderValue != null ) // user is logged in
            validateUserToken(authHeaderValue);

        chain.doFilter(request, response);
    }

    private void validateUserToken(String authHeaderValue) {
        try {
            Claims userClaims = JwtUtils.getUserClaims(authHeaderValue);
            userDataProvider.setData(userClaims);
            setUserAuthentication(userClaims);
        } catch (Exception e) {
            throw new AuthException("Invalid token.");
        }
    }

    private void setUserAuthentication(Claims userClaims) {
        SecurityContextHolder.getContext()
                .setAuthentication(new AuthenticationImpl(userClaims.getSubject()));

    }

}