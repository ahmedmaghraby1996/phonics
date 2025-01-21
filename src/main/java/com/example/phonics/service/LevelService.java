package com.example.phonics.service;

import com.example.phonics.config.ServerConfig;
import com.example.phonics.core.Helper;
import com.example.phonics.entity.Lesson;
import com.example.phonics.entity.Level;
import com.example.phonics.entity.enums.LevelType;
import com.example.phonics.model.request.LevelRequest;
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
    private LevelRepository levelRepository;

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private Helper helper;

    public Level insertLevel(LevelRequest request) {

        // Handle file uploads and create the Level entity
        Level level = new Level();
        level.setTitle(request.getTitle());
        level.setType(request.getType());
        level.setDescription(request.getDescription());

        // Handle logo file
        if (request.getLogo() != null && !request.getLogo().isEmpty()) {
            String logoPath = fileStorageService.saveFile(request.getLogo(), "logo");
            level.setLogo(logoPath);  // Set the saved logo path
        }

        // Handle letter_sound file
        if (request.getLetterSound() != null && !request.getLetterSound().isEmpty()) {
            String letterSoundPath = fileStorageService.saveFile(request.getLetterSound(), "letter_sound");
            level.setLetterSound(letterSoundPath);  // Set the saved letter sound path
        }

        // Handle lesson_image file
        if (request.getLessonImage() != null && !request.getLessonImage().isEmpty()) {
            String lessonImagePath = fileStorageService.saveFile(request.getLessonImage(), "lesson_image");
            level.setLessonImage(lessonImagePath);  // Set the saved lesson image path
        }

        // Save the level entity
        return levelRepository.save(level);
    }

    public  Level findLevelByTitle(String title){
        return levelRepository.findByTitle(title);
    }
    public List<Level> getAllLevels(LevelType type) {
        List<Level> levels = levelRepository.findByType(type);

        // Map the logo paths to full URLs
        return levels.stream().map(level -> {
            String fullLogoUrl = helper.toUrl(level.getLogo());
            level.setLogo(fullLogoUrl);
            String letterSoundUrl = helper.toUrl(level.getLetterSound());
            level.setLetterSound(letterSoundUrl);
            String lessonImageUrl = helper.toUrl(level.getLessonImage());
            level.setLessonImage(lessonImageUrl);
            String letterSound = helper.toUrl(level.getLetterSound());
            level.setLetterSound(letterSound);
            return level;
        }).collect(Collectors.toList());
    }

    public  List<Lesson> findLessonsByLevel(long id) {return  lessonRepository.findByLevelId(id);}
}
