package robert.web.security.auth.filters;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import robert.exeptions.InvalidJwtException;
import robert.web.security.auth.JwtAuthenticationToken;
import robert.web.security.auth.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class JwtFilter extends OncePerRequestFilter {

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
					.setAuthentication(generateAuthentication(userClaims));
		} catch (Exception e) {
			throw new InvalidJwtException("Invalid token.");
		}
	}

	private Authentication generateAuthentication(Claims userClaims) {
		return new JwtAuthenticationToken( //
				JwtUtils.getRoles(userClaims), //
				JwtUtils.getUserEmail(userClaims), //
				JwtUtils.getUserId(userClaims));
	}

}