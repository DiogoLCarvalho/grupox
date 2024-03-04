package com.fatec.sig1.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fatec.sig1.model.MantemAdminRepository;
import com.fatec.sig1.model.MantemOngRepository;
import com.fatec.sig1.model.MantemUserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	MantemUserRepository repositoryUser;
	
	@Autowired
	MantemAdminRepository repositoryAdmin;
	
	@Autowired
	MantemOngRepository repositoryOng;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		var JWTtoken = recuperarToken(request);

		
		if(JWTtoken != null) {
			var subject = tokenService.pegaSubject(JWTtoken);		
			
			if(repositoryUser.findByLogin(subject) != null) {
				
				var user = repositoryUser.findByLogin(subject);
				
				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}else if(repositoryAdmin.findByLogin(subject) != null) {
				
				var admin = repositoryAdmin.findByLogin(subject);
				
				var authentication = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
			}else if(repositoryOng.findByLogin(subject) != null) {
				
				var ong = repositoryOng.findByLogin(subject);
				
				var authentication = new UsernamePasswordAuthenticationToken(ong, null, ong.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
			
			
		}
		
		
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		
		return null;
	}
	

}
