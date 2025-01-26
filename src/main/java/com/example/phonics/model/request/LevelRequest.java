package com.example.phonics.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.phonics.entity.enums.LevelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LevelRequest {

    private MultipartFile logo;

    private MultipartFile letterSound ;
    private MultipartFile lessonImage ;
    private String description;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotNull(message = "Type is mandatory")
    private LevelType type;









}
