package com.os.course.service.impl;

import com.os.course.model.dto.DeletedFilesDto;
import com.os.course.model.dto.SongMetadataDto;
import com.os.course.model.entity.SongMetadata;
import com.os.course.model.exception.FileNotFoundException;
import com.os.course.model.exception.MultipartFileException;
import com.os.course.persistent.repository.SongMetadataRepository;
import com.os.course.service.SongMetadataService;
import com.os.course.util.Constant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongMetadataServiceImpl implements SongMetadataService {
    private final SongMetadataRepository songMetadataRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SongMetadataServiceImpl(SongMetadataRepository songMetadataRepository, ModelMapper modelMapper) {
        this.songMetadataRepository = songMetadataRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SongMetadataDto save(SongMetadataDto songMetadataDto) {
        SongMetadata songMetadata = modelMapper.map(songMetadataDto, SongMetadata.class);
        return modelMapper.map(songMetadataRepository.save(songMetadata), SongMetadataDto.class);
    }

    @Override
    public DeletedFilesDto delete(String ids) {
        DeletedFilesDto deletedFilesDto = new DeletedFilesDto();

        deletedFilesDto.setIds(Arrays.stream(ids.split(Constant.COMMA_SEPARATOR))
                .filter(id -> id.matches("[0-9]"))
                .map(id -> songMetadataRepository.findById(Long.parseLong(id)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(songMetadata -> !songMetadata.isDeleted())
                .peek(file -> file.setDeleted(true))
                .map(songMetadataRepository::save)
                .map(SongMetadata::getId)
                .collect(Collectors.toList()));

        return deletedFilesDto;
    }

    @Override
    public SongMetadataDto getFileBy(Long id) {
        return songMetadataRepository.findById(id)
                .map(file -> modelMapper.map(file, SongMetadataDto.class))
                .orElseThrow(() -> new FileNotFoundException(Constant.RESOURCE_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public List<SongMetadataDto> getAllFiles() {
        return songMetadataRepository.findAll()
                .stream()
                .map(file -> modelMapper.map(file, SongMetadataDto.class))
                .collect(Collectors.toList());
    }
}
