package com.remote.doctor.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.remote.doctor.constant.Roles;
import com.remote.doctor.domain.Doctor;
import com.remote.doctor.domain.Role;
import com.remote.doctor.dto.DoctorDto;
import com.remote.doctor.util.ImageUtils;

public class DoctorDtoToDoctorConverter implements Converter<DoctorDto, Doctor> {
    private BCryptPasswordEncoder encoder;

    public DoctorDtoToDoctorConverter(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public Doctor convert(DoctorDto source) {
        Doctor doctor = new Doctor();

        doctor.setRole(createDoctorRole());
        doctor.setFirstname(source.getFirstname());
        doctor.setLastname(source.getLastname());
        doctor.setUsername(source.getUsername());
        doctor.setPassword(encoder.encode(source.getPassword()));
        doctor.setCity(source.getCity());
        doctor.setDescription(source.getDescription());
        doctor.setPhoneNumber(source.getPhoneNumber());
        doctor.setPrice(source.getPrice());
        doctor.setAvatar(ImageUtils.getImageFromFile(source.getAvatar()));

        return doctor;
    }

    private Role createDoctorRole() {
        Role role = new Role();
        role.setId(Roles.ADMIN.ordinal() + 1);
        role.setName(Roles.ADMIN.getVal());

        return role;
    }
}
