package com.remote.doctor.converter;

import org.springframework.core.convert.converter.Converter;

import com.remote.doctor.domain.Client;
import com.remote.doctor.dto.ClientDto;
import com.remote.doctor.util.ImageUtils;

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
        clientDto.setAmount(source.getAmount());
        clientDto.setEncodeAvatar(ImageUtils.encodeImage(source.getAvatar()));

        return clientDto;
    }
}
