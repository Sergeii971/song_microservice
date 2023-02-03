package com.os.course.web.controller;

import com.os.course.model.dto.DeletedFilesDto;
import com.os.course.model.dto.SongMetadataDto;
import com.os.course.service.SongMetadataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;

@RestController
@RequestMapping("/v1/songs")
public class SongMetadataController {

    private final SongMetadataService songMetadataService;

    public SongMetadataController(SongMetadataService songMetadataService) {
        this.songMetadataService = songMetadataService;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public SongMetadataDto save(@RequestBody SongMetadataDto songMetadataDto) {
        return songMetadataService.save(songMetadataDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public SongMetadataDto getBy(@PathVariable(value = "id") Long id) {
        return songMetadataService.getFileBy(id);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeletedFilesDto remove(@RequestParam(value = "id") @Max(200) String ids) {
        return songMetadataService.delete(ids);
    }
}
