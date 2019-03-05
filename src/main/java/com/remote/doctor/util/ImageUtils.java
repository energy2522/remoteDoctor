package com.remote.doctor.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public final class ImageUtils {
    private static final int HEIGHT = 180;
    private static final int WIDTH = 130;
    private static final String EXTENSION = "png";

    public static byte[] getImageFromFile(MultipartFile file) {
        byte[] imageBytes = null;

        try {
            InputStream inputStream = file.getInputStream();

            imageBytes = compressImageToPng(inputStream);
        } catch (IOException e) {
            System.out.println("Can't get image!!!"); //TODO should rewrite via logger
        }

        return imageBytes;
    }

    private static byte[] compressImageToPng(InputStream imageInputStream) throws IOException {
            BufferedImage bufferedImage = ImageIO.read(imageInputStream);

            bufferedImage = Thumbnails.of(bufferedImage).size(WIDTH, HEIGHT).outputFormat(EXTENSION).asBufferedImage();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage, EXTENSION, outputStream);

            return outputStream.toByteArray();

    }
}
