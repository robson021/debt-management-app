package robert.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import robert.db.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {

	private static final String KEY = "secretkey";

	public static Claims getUserClaims(String authHeaderValue) throws Exception {
		return Jwts.parser()
				.setSigningKey(KEY)
				.parseClaimsJws(authHeaderValue.substring(7)) // get the part after "Bearer "
				.getBody();
	}

	public static Claims getUserClaims(HttpServletRequest request) {
		return (Claims) request.getAttribute("claims");
	}

	public static Long getUserId(HttpServletRequest request) {
		return Long.parseLong(getUserClaims(request).getSubject());
	}

	public static boolean isAdmin(HttpServletRequest request) {
		return getUserClaims(request).getSubject().equals("admin");
	}

	public static String generateToken(User user) {
		return Jwts.builder()
				.setSubject(String.valueOf(user.getId()))
				.claim("roles", user.getRole())
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, KEY)
				.compact();
	}

}
