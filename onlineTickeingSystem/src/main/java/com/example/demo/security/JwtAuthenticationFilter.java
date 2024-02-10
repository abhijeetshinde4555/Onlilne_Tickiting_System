package com.example.demo.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	 private Logger log = LoggerFactory.getLogger(OncePerRequestFilter.class);
	
	@Autowired
	private JwtHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//String authHeader = request.getHeader("Authorization");
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = null;
		String userName = null;
		
		if ( authHeader != null && authHeader.startsWith("Bearer ") ) {
			token = authHeader.substring(7);
			userName = jwtTokenHelper.extractUserName(token);
		}
		
		if ( userName != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
			UserDetails userDetails = userDetailService.loadUserByUsername(userName);
			
			if ( jwtTokenHelper.validateToken(token, userDetails) ) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
		
		

//	        String requestHeader = request.getHeader("Authorization");
//	        //Bearer 2352345235sdfrsfgsdfsdf
//	        log.info(" Header :  {}", requestHeader);
//	        String username = null;
//	        String token = null;
//	        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
//	            //looking good
//	            token = requestHeader.substring(7);
//	            try {
//
//	                username = this.jwtTokenHelper.getUsernameFromToken(token);
//
//	            } catch (IllegalArgumentException e) {
//	                log.info("Illegal Argument while fetching the username !!");
//	                e.printStackTrace();
//	            } catch (ExpiredJwtException e) {
//	                log.info("Given jwt token is expired !!");
//	                e.printStackTrace();
//	            } catch (MalformedJwtException e) {
//	                log.info("Some changed has done in token !! Invalid Token");
//	                e.printStackTrace();
//	            } catch (Exception e) {
//	                e.printStackTrace();
//
//	            }
//
//
//	        } else {
//	            log.info("Invalid Header Value !! ");
//	        }
//
//
//	        //
//	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//
//	            //fetch user detail from username
//	            UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
//	            Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDetails);
//	            if (validateToken) {
//
//	                //set the authentication
//	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//	                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//	            } else {
//	                log.info("Validation fails !!");
//	            }
//
//
//	        }
//
//	        filterChain.doFilter(request, response);
//
//
	    }

}
