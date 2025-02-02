package com.example.phonics.controller;

import com.example.phonics.entity.Activity;
import com.example.phonics.entity.Lesson;
import com.example.phonics.entity.Level;
import com.example.phonics.entity.User;
import com.example.phonics.entity.enums.LevelType;
import com.example.phonics.exception.DuplicateEntryException;
import com.example.phonics.model.request.LevelRequest;
import com.example.phonics.model.response.ActionResponse;
import com.example.phonics.service.LevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;


@RestController
public class levelController {

    @Autowired
    private LevelService levelService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/level", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})

    public ResponseEntity<Level> insertLevel(@Valid  @ModelAttribute LevelRequest request) {
        Level checkLevel= levelService.findLevelByTitle(request.getTitle());
        if(checkLevel!=null) {
            throw new DuplicateEntryException("title already exists");
        }
        Level level = levelService.insertLevel(request);
        return ResponseEntity.ok(level);
    }


        @GetMapping(value = "/level")

        public ActionResponse<List<Level>> fetchLevels(@RequestParam(required = false)LevelType type) {
            List<Level> levels = levelService.getAllLevels(type);
            return new ActionResponse<>(levels);
        }

    @GetMapping(value = "/level/{level_id}/lessons")

    public ActionResponse<List<Lesson>> fetchLessons(@Parameter @PathVariable("level_id") long levelId) {
        List<Lesson> lessons = levelService.findLessonsByLevel(levelId);
        return new ActionResponse<>(lessons);
    }

    @GetMapping(value = "/level/{level_id}/activities")

    public ActionResponse<List<Activity>> fetchActivity(@Parameter @PathVariable("level_id") long levelId) {
        List<Activity> activities = levelService.findActivitiesByLevel(levelId);
        return new ActionResponse<>(activities);
    }

    @PostMapping(value = "/complete/activity/{activity_id}")

    public ActionResponse<Activity> completeActivity(@Parameter @PathVariable("activity_id") long activityId) {
       Activity activity = levelService.compeleteActivity(activityId);
       return new ActionResponse<Activity>(activity);
    }

    @PostMapping(value = "/complete/lesson/{lesson_id}")

    public ActionResponse<Lesson> completeLesson(@Parameter @PathVariable("lesson_id") long lessonId) {
        Lesson lesson = levelService.compeleteLesson(lessonId);
        return new ActionResponse<Lesson>(lesson);
    }



}











