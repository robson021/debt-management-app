package robert.web.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import robert.db.entities.User;

import java.util.Date;

public class JwtUtils {

    public static final String KEY = RandomStringUtils.randomAlphanumeric(16);

    public static Claims getUserClaims(String authHeaderValue) throws Exception {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(authHeaderValue.substring(7)) // get the part after "Bearer "
                .getBody();
    }

    public static Long getUserId(Claims claims) {
        return Long.parseLong(claims.getId());
    }

    public static String getUserEmail(Claims claims) {
        return claims.getSubject();
    }

    public static boolean isAdmin(Claims claims) {
        return Boolean.valueOf(claims.get("role")
                .toString());
    }

    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setId(String.valueOf(user.getId()))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }
}
