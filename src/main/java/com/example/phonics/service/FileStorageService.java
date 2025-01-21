package com.example.phonics.service;

import com.example.phonics.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileStorageService {

    @Autowired
    private ServerConfig serverConfig;



    public String saveFile(MultipartFile file, String fileType) {
        // Generate a unique file name to avoid overwriting
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(serverConfig.getStorageLocation() + fileType + "/" + fileName); // Save file in a directory based on its type

        try {
            // Ensure the directory exists
            Files.createDirectories(path.getParent());

            // Write the file to disk
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving the " + fileType + " file", e);
        }

        return  "uploads/"+fileType+"/"+fileName;  // Return the relative file path (for database storage)
    }
}
