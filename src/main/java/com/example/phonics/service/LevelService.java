package com.example.phonics.service;

import com.example.phonics.config.ServerConfig;
import com.example.phonics.core.Helper;
import com.example.phonics.entity.Lesson;
import com.example.phonics.entity.Level;
import com.example.phonics.repository.LessonRepository;
import com.example.phonics.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelService {

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private Helper helper;

    public Level insertLevel(MultipartFile file, String title) {

        // Handle file and form data

        Path path = null;
        try {
            // Path where the file will be saved
            path = Paths.get(serverConfig.getStorageLocation() + file.getOriginalFilename());
            Files.write(path, file.getBytes());


        } catch (IOException e) {
            e.printStackTrace();

        }
        Level level = new Level();
        level.setTitle(title);
        level.setLogo("uploads/"+file.getOriginalFilename());
        return levelRepository.save(level);
    }

    public  Level findLevelByTitle(String title){
        return levelRepository.findByTitle(title);
    }
    public List<Level> getAllLevels() {
        List<Level> levels = levelRepository.findAll();

        // Map the logo paths to full URLs
        return levels.stream().map(level -> {
            String fullLogoUrl = helper.toUrl(level.getLogo());
            level.setLogo(fullLogoUrl);
            return level;
        }).collect(Collectors.toList());
    }

    public  List<Lesson> findLessonsByLevel(long id) {return  lessonRepository.findByLevelId(id);}
}
