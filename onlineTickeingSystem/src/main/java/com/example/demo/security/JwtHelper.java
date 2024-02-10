package com.example.demo.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



@Component
public class JwtHelper {


    private static final Logger log = LoggerFactory.getLogger(JwtHelper.class);

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // It's advisable to store the secret key securely or use a secure configuration method
    private String secret = "jwtTokenKey";

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    private Key signInKey = null;
    
    private Key getSignKey() {
        // Generate a secure key for HMAC-SHA256 algorithm
        Key signInKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
     // Convert the secret key to a byte array
        byte[] keyBytes = signInKey.getEncoded();

        // Convert the byte array to a Base64-encoded string
        String keyString = Base64.getEncoder().encodeToString(keyBytes);
    	log.info( keyString);
        return signInKey;
        
        //byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        //return Keys.hmacShaKeyFor(keyBytes);    	
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signInKey )
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error extracting claims from JWT token: {}", e.getMessage());
            log.error("Problematic token: {}", token);
            throw e;
        }
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	//-------------------------------------------------02.02.2023
	
//	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//
//
//    private String secret = "jwtTokenKey";
//    
//    public String generateTokan ( String userName ) {
//    	Map<String , Object> claims = new HashMap<>();
//    	return createTokan(claims,userName);
//    }
//
//	private String createTokan(Map<String, Object> claims, String userName) {
//		
//		return Jwts.builder()
//				.setClaims(claims)
//				.setSubject(userName)
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 10000*60*30) )
//				.signWith( getSignKey() , SignatureAlgorithm.HS256 )
//				.compact();
//	}
//
//	private Key getSignKey() {
//		byte[] keybytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
//		return Keys.hmacShaKeyFor(keybytes);
//	}
//	
////	private Key getSignKey() {
////		byte[] keybytes = Decoders.BASE64.decode(secret);
////		return Keys.hmacShaKeyFor(keybytes);
////	}
//    
//	public String extractUserName ( String token ) {
//		return extractClaims(token, Claims::getSubject);
//	}
//
//	private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
//		final Claims claims = extractAllClaims(token);
//		return claimResolver.apply(claims);
//	}
//
//	private Claims extractAllClaims(String token) {
//		return Jwts.parserBuilder()
//				.setSigningKey(getSignKey())
//				.build()
//				.parseClaimsJws(token)
//				.getBody();
//	}
//	
//	public Boolean isTokenExpired ( String token ) {
//		return extractExpiration(token).before(new Date());
//	}
//
//	private Date extractExpiration(String token) {
//		return extractClaims(token, Claims::getExpiration) ;
//	}
//    
//	public Boolean validateToken ( String token , UserDetails userDetails ) {
//		final String userName = extractUserName(token);
//		return ( userName.equals(userDetails.getUsername()) && !isTokenExpired(token) );
//	}
//	
	
	
	
	
	//------------------------------------------------------------------------old
	
//	public String getUsernameFromToken(String token) {
//		return extractClaims(token, Claims::getSubject);
//	}
    
    
    
//
//    //retrieve username from jwt token
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    //retrieve expiration date from jwt token
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    //for retrieveing any information from token we will need the secret key
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    }
//
//    //check if the token has expired
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    //generate token for user
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }
//
//    //while creating the token -
//    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//    //2. Sign the JWT using the HS512 algorithm and secret key.
//    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//    //   compaction of the JWT to a URL-safe string
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, secret).compact();
//    }
//
//    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
}
