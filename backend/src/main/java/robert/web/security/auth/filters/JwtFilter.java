package robert.web.security.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import robert.exeptions.AuthException;
import robert.web.request.data.UserDataProvider;
import robert.web.request.data.UserDataProviderImpl;
import robert.web.security.auth.JwtUtils;

@Component
public class JwtFilter extends OncePerRequestFilter {

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
			UserDataProvider userDataProvider = new UserDataProviderImpl(userClaims);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDataProvider, null, userDataProvider.getAuthorities());
			SecurityContextHolder.getContext()
					.setAuthentication(auth);
		} catch (Exception e) {
			throw new AuthException("Invalid token.");
		}
	}

}