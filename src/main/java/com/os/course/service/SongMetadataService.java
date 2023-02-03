package com.os.course.service;

import com.os.course.model.dto.DeletedFilesDto;
import com.os.course.model.dto.SongMetadataDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongMetadataService {

    SongMetadataDto save(SongMetadataDto songMetadataDto);

    DeletedFilesDto delete(String ids);

    SongMetadataDto getFileBy(Long id);

    List<SongMetadataDto> getAllFiles();
}
