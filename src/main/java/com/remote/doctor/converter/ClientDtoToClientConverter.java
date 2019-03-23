package com.remote.doctor.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.remote.doctor.constant.Roles;
import com.remote.doctor.domain.Client;
import com.remote.doctor.domain.Role;
import com.remote.doctor.dto.ClientDto;
import com.remote.doctor.util.ImageUtils;

public class ClientDtoToClientConverter implements Converter<ClientDto, Client> {
    private BCryptPasswordEncoder encoder;

    public ClientDtoToClientConverter(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public Client convert(ClientDto source) {
        Client client = new Client();

        client.setId(source.getId());
        client.setUsername(source.getUsername());
        client.setFirstname(source.getUsername());
        client.setLastname(source.getLastname());
        client.setPhoneNumber(source.getPhoneNumber());
        client.setRole(createClientRole());
        client.setAvatar(ImageUtils.getImageFromFile(source.getAvatar()));


        if (StringUtils.isNotBlank(source.getPassword())) {
            client.setPassword(encoder.encode(source.getPassword()));
        }

        return client;
    }

    private Role createClientRole() {
        Role role = new Role();
        role.setId(Roles.CLIENT.ordinal() + 1);
        role.setName(Roles.CLIENT.getVal());

        return role;
    }
}
