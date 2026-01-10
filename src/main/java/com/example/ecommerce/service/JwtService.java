package com.example.ecommerce.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

	private String secretkey=generatekey();
	
	//This method is use to generate jwt token
	public String generateToken(String email) {
		
		Map<String,Object> claim = new HashMap<>();
		
		return Jwts.builder()
				.claims()
				.add(claim)
				.subject(email)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 *60 *30))
				.and()
				.signWith(getKey())
				.compact();
	}
	
	//This method is use to get Key for jwt token
	private SecretKey getKey() {
		 byte[] key=Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(key);
	}
	
	//This method is use to generate key for jwt token
	private String generatekey() {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey skey = keygen.generateKey();
			return Base64.getEncoder().encodeToString(skey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//This method is use to extract email from the token
	public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
	
	public String getToken(HttpServletRequest request) {
		
		String auth = request.getHeader("Authorization");
		
		if(auth == null || !auth.startsWith("Bearer "))
			return null;
		
		String token = auth.substring(7);
		return token;
	}
	
	//Sub method for extractEmail() Method
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    
  //Sub method for extractEmail() Method
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    //This method is use for to validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractEmail(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    //Sub method for validateToken() Method
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
  //Sub method for validateToken() Method
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}