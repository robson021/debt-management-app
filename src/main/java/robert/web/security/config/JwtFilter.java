package robert.web.security.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import robert.exeptions.AuthException;
import robert.web.request.data.UserDataProvider;
import robert.web.security.JwtUtils;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDataProvider userDataProvider;

    @Autowired
    public JwtFilter(UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authHeaderValue = request.getHeader("Authorization");

        if ( authHeaderValue != null ) // user is logged in
            validateUserToken(authHeaderValue, request);

        chain.doFilter(request, response);
    }

    private void validateUserToken(String authHeaderValue, HttpServletRequest request) {
        try {
            Claims userClaims = JwtUtils.getUserClaims(authHeaderValue);
            request.setAttribute("claims", userClaims);
            setUserAuthentication(userClaims);
            userDataProvider.setData(request);
        } catch (Exception e) {
            throw new AuthException("Invalid token.");
        }
    }

    private void setUserAuthentication(Claims userClaims) {
        SecurityContextHolder.getContext()
                .setAuthentication(new AuthenticationImpl(userClaims.getSubject(), true));

    }

}