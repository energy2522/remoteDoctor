package com.remote.doctor.auth;

import com.remote.doctor.domain.User;
import com.remote.doctor.repository.AdminRepository;
import com.remote.doctor.repository.ClientRepository;
import com.remote.doctor.repository.DoctorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;

    public CustomUserDetailsService(ClientRepository clientRepository, AdminRepository adminRepository, DoctorRepository doctorRepository) {
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loadUser(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User with username: %s isn't found", username));
        }

        return new CustomUserDetails(user);
    }

    private User loadUser(String username) {
        User user = clientRepository.getClientByUsername(username);

        if (Objects.isNull(user)) {
            user = doctorRepository.getDoctorByUsername(username);
        }

        if (Objects.isNull(user)) {
            user = adminRepository.getAdminByUsername(username);
        }

        return user;
    }
}
