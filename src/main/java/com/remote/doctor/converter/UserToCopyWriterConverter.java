package com.remote.doctor.converter;

import java.util.Base64;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.remote.doctor.domain.Client;
import com.remote.doctor.domain.Doctor;
import com.remote.doctor.domain.User;
import com.remote.doctor.dto.CopyWriter;
import com.remote.doctor.util.ImageUtils;

public class UserToCopyWriterConverter implements Converter<User, CopyWriter> {
    private BCryptPasswordEncoder encoder;

    public UserToCopyWriterConverter(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public CopyWriter convert(User source) {
        CopyWriter copyWriter = new CopyWriter();
        byte[] image = null;
        String fullName = "";

        copyWriter.setId(source.getId());
        copyWriter.setUsername(source.getUsername());

        if (source instanceof Client) {
            Client client = (Client) source;

            fullName = String.format("%s %s", client.getLastname(), client.getFirstname());
            image = client.getAvatar();
        }

        if (source instanceof Doctor) {
            Doctor doctor = (Doctor) source;

            fullName = String.format("%s %s", doctor.getLastname(), doctor.getFirstname());
            image = doctor.getAvatar();
        }

        copyWriter.setEncodeAvatar(ImageUtils.encodeImage(image));
        copyWriter.setFullName(fullName);

        return copyWriter;
    }
}
