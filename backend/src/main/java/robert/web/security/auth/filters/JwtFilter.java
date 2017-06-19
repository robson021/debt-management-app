package robert.web.security.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import robert.exeptions.AuthException;
import robert.web.security.auth.JwtAuthenticationToken;
import robert.web.security.auth.JwtUtils;

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
			SecurityContextHolder.getContext()
					.setAuthentication(getAuthentication(userClaims));
		} catch (Exception e) {
			throw new AuthException("Invalid token.");
		}
	}

	private Authentication getAuthentication(Claims userClaims) {
		return new JwtAuthenticationToken( //
				JwtUtils.getRoles(userClaims), //
				JwtUtils.getUserEmail(userClaims), //
				JwtUtils.getUserId(userClaims));
	}

}