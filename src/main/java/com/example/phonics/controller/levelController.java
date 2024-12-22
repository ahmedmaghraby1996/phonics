package com.example.phonics.controller;

import com.example.phonics.entity.Lesson;
import com.example.phonics.entity.Level;
import com.example.phonics.exception.DuplicateEntryException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
public class levelController {

    @Autowired
    private LevelService levelService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/admin/level", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})

    public ResponseEntity<Level> insertLevel(@Valid @RequestParam("file") MultipartFile file, @RequestParam("title") String title) {
        Level checkLevel= levelService.findLevelByTitle(title);
        if(checkLevel!=null) {
            throw new DuplicateEntryException("title already exists");
        }
        Level level = levelService.insertLevel(file, title);
        return ResponseEntity.ok(level);
    }


    @GetMapping(value = "/level")

    public ActionResponse<List<Level>> fetchLevels() {
        List<Level> levels = levelService.getAllLevels();
        return new ActionResponse<>(levels);
    }

    @GetMapping(value = "/level/{level_id}/lessons")

    public ActionResponse<List<Lesson>> fetchLessons(@Parameter @PathVariable("level_id") long levelId) {
        List<Lesson> lessons = levelService.findLessonsByLevel(levelId);
        return new ActionResponse<>(lessons);
    }


}











