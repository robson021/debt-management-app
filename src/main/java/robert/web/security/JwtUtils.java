package robert.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import robert.db.entities.User;

import java.util.Date;

public class JwtUtils {

	private static final String KEY = "secretkey";

	public static Claims getUserClaims(String authHeader) throws Exception {
		return Jwts.parser()
				.setSigningKey(KEY)
				.parseClaimsJws(authHeader.substring(7)) // get the part after "Bearer "
				.getBody();
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
