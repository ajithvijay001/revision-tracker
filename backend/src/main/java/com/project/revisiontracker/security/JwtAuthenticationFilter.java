package com.project.revisiontracker.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.revisiontracker.service.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	public static final String HEADER_PREFIX_TOKEN = "Bearer ";
	public static final String API_PATH_PATTERN = "/api/";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getJwtFromRequest(request);
		
		try {
			String userName = jwtUtil.extractUserName(token);
			if (StringUtils.hasText(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
				
				if(jwtUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | MalformedJwtException | SignatureException |
		         UnsupportedJwtException | IllegalArgumentException | BadCredentialsException ex) {
				
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		        response.setContentType("application/json");
		        String json = String.format("{\"error\": \"%s\", \"message\": \"%s\"}", "Unauthorized", ex.getMessage());
		        response.getWriter().write(json);
		        response.getWriter().flush();
		    
		    return;
		}
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
