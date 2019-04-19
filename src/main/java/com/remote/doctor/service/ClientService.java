package com.remote.doctor.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.remote.doctor.domain.Client;
import com.remote.doctor.dto.ClientDto;
import com.remote.doctor.repository.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ConversionService conversionService;

    @Transactional
    public List<String> clientSignUp(ClientDto clientDto) {
        Client client = conversionService.convert(clientDto, Client.class);

        clientRepository.save(client);

        return Collections.emptyList();//TODO should be rewrite with validation
    }

    @Transactional
    public List<String> updateOldClient(ClientDto clientDto) {
        Client newClient = conversionService.convert(clientDto, Client.class);
        Client oldClient = clientRepository.findById(clientDto.getId()).get();

        updateOldClient(newClient, oldClient);

        clientRepository.save(oldClient);

        return Collections.emptyList();//TODO should be rewrite with validation
    }

    public ClientDto getClientById(int id) {
        return conversionService.convert(clientRepository.findById(id).orElse(null), ClientDto.class);
    }

    private void updateOldClient(Client newClient, Client oldClient) {
        if (Objects.nonNull(newClient.getAvatar())) {
            oldClient.setAvatar(newClient.getAvatar());
        }

        if (StringUtils.isNotBlank(newClient.getPassword())) {
            oldClient.setPassword(newClient.getPassword());
        }

        oldClient.setPhoneNumber(newClient.getPhoneNumber());
        oldClient.setUsername(newClient.getUsername());
        oldClient.setLastname(newClient.getLastname());
        oldClient.setFirstname(newClient.getFirstname());
    }

}
