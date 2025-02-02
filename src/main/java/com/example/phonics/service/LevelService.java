package com.example.phonics.service;

import com.example.phonics.core.Helper;
import com.example.phonics.entity.Activity;
import com.example.phonics.entity.Lesson;
import com.example.phonics.entity.Level;
import com.example.phonics.entity.User;
import com.example.phonics.entity.enums.LevelType;
import com.example.phonics.exception.BadRequestException;
import com.example.phonics.exception.NotFoundException;
import com.example.phonics.model.request.LevelRequest;
import com.example.phonics.repository.ActivityRepository;
import com.example.phonics.repository.LessonRepository;
import com.example.phonics.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    private ActivityRepository activityRepository;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        // Map the logo paths to full URLs
        return levels.stream().map(level -> {
            String fullLogoUrl = helper.toUrl(level.getLogo());
            level.setLogo(fullLogoUrl);
            // Check if the user has completed the activity
            if (level.getUsers().contains(user)) {
                level.setTracingCompleted(true); // Assuming the Activity entity has a 'completed' field
            } else {
                level.setTracingCompleted(false);
            }
            String letterSoundUrl = helper.toUrl(level.getLetterSound());
            level.setLetterSound(letterSoundUrl);
            String lessonImageUrl = helper.toUrl(level.getLessonImage());
            level.setLessonImage(lessonImageUrl);
            String letterSound = helper.toUrl(level.getLetterSound());
            level.setLetterSound(letterSound);
            // Check if the user has completed all activities and lessons
            boolean allActivitiesCompleted = level.getActivities().stream()
                    .allMatch(activity -> activity.getUsers().contains(user));

            boolean allLessonsCompleted = level.getLessons().stream()
                    .allMatch(lesson -> lesson.getUsers().contains(user));
            if(level.getType()==LevelType.LEVEL){
            level.setCompleted(allActivitiesCompleted && allLessonsCompleted);}
            else
            {
                level.setCompleted(allActivitiesCompleted && allLessonsCompleted && level.isTracingCompleted());
            }
            return level;
        }).collect(Collectors.toList());
    }

    public  List<Lesson> findLessonsByLevel(long id) { List<Lesson> lesson=  lessonRepository.findByLevelId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
    lesson.forEach(l->{l.setSound(helper.toUrl(l.getSound())); l.setImage(helper.toUrl(l.getImage()));

        if (l.getUsers().contains(user)) {
            l.setCompleted(true); // Assuming the Activity entity has a 'completed' field
        } else {
            l.setCompleted(false);
        }});
        return  lesson;
    }

    public List<Activity> findActivitiesByLevel(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<Activity> activities = activityRepository.findByLevelId(id);
        activities.forEach(a -> {

            a.setSound(helper.toUrl(a.getSound()));
            a.setImage(helper.toUrl(a.getImage()));
            // Check if the user has completed the activity
            if (a.getUsers().contains(user)) {
                a.setCompleted(true); // Assuming the Activity entity has a 'completed' field
            } else {
                a.setCompleted(false);
            }
        });

        return activities;
    }

    public  Activity compeleteActivity(long id){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     User user = (User) authentication.getPrincipal();
     System.out.println(user.getId());
        Optional<Activity> optionalActivity =  activityRepository.findById(id);
        if (!optionalActivity.isPresent()) {
            throw new NotFoundException("Activity not found");
        }
     // Add the user to the activity's list of users
     Activity activity = optionalActivity.get();
     System.out.println(activity.getUsers());
     if (!activity.getUsers().contains(user)) {
         activity.getUsers().add(user);
     } else {
         throw new BadRequestException("User is already part of this activity");
     }

     // Save the updated activity
     activityRepository.save(activity);

     return activity;
 }

    public  Lesson compeleteLesson(long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Lesson> optionalLesson=  lessonRepository.findById(id);
        if (!optionalLesson.isPresent()) {
            throw new NotFoundException("Lesson not found");
        }
        // Add the user to the activity's list of users
        Lesson lesson = optionalLesson.get();

        if (!lesson.getUsers().contains(user)) {
            lesson.getUsers().add(user);
        } else {
            throw new BadRequestException("User is already part of this activity");
        }

        // Save the updated activity
        lessonRepository.save(lesson);

        return lesson;
    }

    public  Level compeleteTracing(long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Level> optionalLevel=  levelRepository.findById(id);
        if (!optionalLevel.isPresent()) {
            throw new NotFoundException("Level not found");
        }
        // Add the user to the activity's list of users
        Level level = optionalLevel.get();

        if (!level.getUsers().contains(user)) {
            level.getUsers().add(user);
        } else {
            throw new BadRequestException("User is already part of this activity");
        }

        // Save the updated activity
        levelRepository.save(level);

        return level;
    }
}
