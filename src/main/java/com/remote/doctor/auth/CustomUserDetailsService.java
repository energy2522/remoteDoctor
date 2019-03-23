package com.remote.doctor.auth;

import com.remote.doctor.domain.User;
import com.remote.doctor.repository.AdminRepository;
import com.remote.doctor.repository.ClientRepository;
import com.remote.doctor.repository.DoctorRepository;
import com.remote.doctor.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User with username: %s isn't found", username));
        }

        return new CustomUserDetails(user);
    }
}
