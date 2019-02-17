package com.remote.doctor.util;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public final class ImageUtils {

    public static byte[] getImageFromFile(MultipartFile file) {
        byte[] imageBytes = null;

        try {
            imageBytes = file.getBytes();
        } catch (IOException e) {
            System.out.println("Can't get image!!!"); //TODO should rewrite via logger
        }

        return imageBytes;
    }
}
