package robert.web.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import robert.db.entities.User;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

public abstract class JwtUtils {

	public static final String KEY = RandomStringUtils.randomAlphanumeric(16);

	private static final JwtParser JWT_PARSER = Jwts.parser()
			.setSigningKey(KEY);

	public static Claims getUserClaims(String authHeaderValue) throws Exception {
		return JWT_PARSER.parseClaimsJws(authHeaderValue.substring(7)) // get the part after "Bearer "
				.getBody();
	}

	public static String generateToken(User user) {
		return Jwts.builder()
				.setSubject(user.getEmail())
				.setId(String.valueOf(user.getId()))
				.claim("role", user.getRole())
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, KEY)
				.compact();
	}

	public static long getUserId(Claims claims) {
		return Long.parseLong(claims.getId());
	}

	public static String getUserEmail(Claims claims) {
		return claims.getSubject();
	}

	public static Set<SimpleGrantedAuthority> getRoles(Claims userClaims) {
		if (JwtUtils.isAdmin(userClaims)) {
			return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return Collections.emptySet();
	}

	private static boolean isAdmin(Claims claims) {
		return Boolean.valueOf(claims.get("role")
				.toString());
	}
}
