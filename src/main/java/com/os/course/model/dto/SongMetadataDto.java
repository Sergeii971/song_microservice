package com.os.course.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SongMetadataDto {
    private Long id;
    @NotNull
    private String name;
    private String artist;
    private String album;
    @NotNull
    private String length;
    @NotNull
    private Long resourceId;
}
