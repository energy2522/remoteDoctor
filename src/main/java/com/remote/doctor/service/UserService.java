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

    public List<String> signUpClient(ClientDto clientDTO) {
        Client client = conversionService.convert(clientDTO, Client.class);

        clientRepository.save(client);

        return Collections.emptyList();//TODO should be rewrite with validation
    }


}
