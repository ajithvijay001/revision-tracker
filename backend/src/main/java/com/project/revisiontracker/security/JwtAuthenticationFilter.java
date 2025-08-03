package com.project.revisiontracker.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	public static final String HEADER_PREFIX_TOKEN = "Bearer";
	public static final String API_PATH_PATTERN = "/api/";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getJwtFromRequest(request);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getServletPath();
		return !path.startsWith(API_PATH_PATTERN);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		
		return Optional.ofNullable(request.getHeader(AUTHENTICATION_HEADER_NAME))
				.filter(header -> header.toLowerCase().startsWith(HEADER_PREFIX_TOKEN.toLowerCase()))
				.map(header -> header.substring(HEADER_PREFIX_TOKEN.length()).trim())
				.orElseThrow(() -> new BadCredentialsException("Token Corrupted"));
	}

}
