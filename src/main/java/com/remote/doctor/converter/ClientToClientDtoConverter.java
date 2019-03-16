package com.remote.doctor.converter;

import java.util.Base64;

import org.springframework.core.convert.converter.Converter;

import com.remote.doctor.domain.Client;
import com.remote.doctor.dto.ClientDto;

public class ClientToClientDtoConverter implements Converter<Client, ClientDto> {

    @Override
    public ClientDto convert(Client source) {
        ClientDto clientDto = new ClientDto();

        if (source == null) {
            return clientDto;
        }

        clientDto.setId(source.getId());
        clientDto.setFirstname(source.getFirstname());
        clientDto.setLastname(source.getLastname());
        clientDto.setPhoneNumber(source.getPhoneNumber());
        clientDto.setUsername(source.getUsername());

        if (source.getAvatar() != null) {
            String encodeImage = Base64.getEncoder().encodeToString(source.getAvatar());
            clientDto.setEncodeAvatar(encodeImage);
        }

        return clientDto;
    }
}
