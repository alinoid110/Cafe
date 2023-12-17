package org.example.CoffeeBewerter.service.generalFuncs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.example.CoffeeBewerter.SpecialText.*;

@Slf4j
@Component
public class FileService {

    public void downloadPhotoByFilePath(String filePath, String directory, Long chat_id, Long cafe_id, String botToken) throws IOException {
        java.io.File downloadedFile = new java.io.File(directory);

        java.io.File localFile = new java.io.File(downloadedFile, CAFE_IMG_FILE_NAME + chat_id.toString() +
                '_' + cafe_id.toString() + CAFE_IMG_FILE_EXTENSION);

        String link = TG_DOWNLOAD_FILE_LINK + botToken + "/" + filePath;

        try (InputStream in = new URL(link).openStream()) {
            Files.copy(in, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

    }

    public File givePhotoByFilePath(Long chat_id, Long cafe_id) throws IOException {
        java.io.File downloadedFile = new java.io.File(PHOTO_STORAGE_DIR);

        return new File(downloadedFile, CAFE_IMG_FILE_NAME + chat_id.toString() + '_'
                + cafe_id.toString() + CAFE_IMG_FILE_EXTENSION);
    }

    public File giveCaffePhotoByFilePath(Long cafe_id) throws IOException {
        java.io.File downloadedFile = new java.io.File(CAFFE_STORAGE_DIR);

        return new File(downloadedFile, cafe_id.toString() + CAFE_IMG_FILE_EXTENSION);
    }

}
