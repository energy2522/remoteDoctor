package com.remote.doctor.service;

import com.remote.doctor.auth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	private CustomUserDetailsService detailsService;

	public void autologin(String username, String password) {
		UserDetails userDetails = detailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
				password, userDetails.getAuthorities());

		authenticationProvider.authenticate(authenticationToken);

		if (authenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

	}
}
