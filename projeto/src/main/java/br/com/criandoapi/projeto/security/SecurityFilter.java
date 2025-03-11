package br.com.criandoapi.projeto.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

    	//AUTENTICAÇÃO DO TOKEN
      if (request.getHeader("Authorization") != null) {
    	  
    	  Authentication auth = TokenUtil.validate(request);
    	  SecurityContextHolder.getContext().setAuthentication(auth);
		
	}

        filterChain.doFilter(request, response);
    }
}