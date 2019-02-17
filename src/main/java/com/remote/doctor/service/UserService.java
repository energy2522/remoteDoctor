package com.remote.doctor.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.remote.doctor.domain.Client;
import com.remote.doctor.domain.Doctor;
import com.remote.doctor.domain.DoctorType;
import com.remote.doctor.dto.ClientDto;
import com.remote.doctor.dto.DoctorDto;
import com.remote.doctor.repository.ClientRepository;
import com.remote.doctor.repository.DoctorRepository;
import com.remote.doctor.repository.DoctorTypeRepository;

@Service
public class UserService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ConversionService conversionService;


    @Autowired
    private DoctorTypeRepository doctorTypeRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<String> signUpClient(ClientDto clientDTO) {
        Client client = conversionService.convert(clientDTO, Client.class);

        clientRepository.save(client);

        return Collections.emptyList();//TODO should be rewrite with validation
    }

    @Transactional
    public List<String> signUpDoctor(DoctorDto doctorDTO) {
        DoctorType doctorType = getDoctorType(doctorDTO.getType());

        Doctor doctor = conversionService.convert(doctorDTO, Doctor.class);
        doctor.setDoctorType(doctorType);

        doctorRepository.save(doctor);

        return Collections.emptyList();//TODO should be rewrite with validation
    }

    private DoctorType getDoctorType(String type) {
        DoctorType doctorType = doctorTypeRepository.getByName(type);

        if (doctorType != null) {
            return doctorType;
        }

        doctorType = new DoctorType();
        doctorType.setName(type);
        doctorTypeRepository.save(doctorType);

        return doctorType;
    }
}
