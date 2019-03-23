package com.remote.doctor.converter;

import java.util.Base64;

import org.springframework.core.convert.converter.Converter;

import com.remote.doctor.domain.Doctor;
import com.remote.doctor.dto.DoctorDto;

public class DoctorToDoctorDtoConverter implements Converter<Doctor, DoctorDto> {

    @Override
    public DoctorDto convert(Doctor source) {
        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setId(source.getId());
        doctorDto.setUsername(source.getUsername());
        doctorDto.setFirstname(source.getFirstname());
        doctorDto.setLastname(source.getLastname());
        doctorDto.setCity(source.getCity());
        doctorDto.setType(source.getDoctorType().getName());
        doctorDto.setPhoneNumber(source.getPhoneNumber());
        doctorDto.setPrice(source.getPrice());
        doctorDto.setDescription(source.getDescription());
        doctorDto.setRate(Math.round(source.getRate()));

        if (source.getAvatar() != null) {
            String encodeImage = Base64.getEncoder().encodeToString(source.getAvatar());
            doctorDto.setEncodeAvatar(encodeImage);

        }

        return doctorDto;
    }
}
