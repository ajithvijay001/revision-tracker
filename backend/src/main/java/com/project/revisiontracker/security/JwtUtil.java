package com.project.revisiontracker.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret; 
	
	public String generateToken(UserDetails userDetails) {
		CustomUserDetails user = (CustomUserDetails) userDetails;
		return Jwts.builder()
				.subject(user.getUsername())
				.claim("userId", user.getUserId())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 *30))
				.signWith(getKey())
				.compact();
	}
	
	public SecretKey getKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	public Boolean validateToken(String token, UserDetails user) {
		return extractUserName(token).equals(user.getUsername());
	}
	
	public String extractUserName(String token) {
		try {
			return Jwts.parser().verifyWith(getKey())
			.build()
			.parseSignedClaims(token)
			.getPayload().getSubject();
		} catch(JwtException e) {
			System.out.println("Invalid Jwt "+ e.getMessage());
			return null;
		}
	}	
	
//	public Integer extractUserId(String token) {
//        return extractUserIdClaims(token).get("userId", Integer.class);
//    }
//	
//	private Claims extractUserIdClaims(String token) {
//		try {
//	        return (Claims) Jwts.parser().verifyWith(getKey())
//	        		.build() 
//	        		.parseSignedClaims(token)
//	        		.getPayload().get("userId");
//			} catch(JwtException e) {
//				System.out.println("Invalid Jwt "+ e.getMessage());
//				return null;
//			}
//    }
}
