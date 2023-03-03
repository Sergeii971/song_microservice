package com.os.course.service.impl;

import com.os.course.Main;
import com.os.course.model.dto.DeletedFilesDto;
import com.os.course.model.dto.SongMetadataDto;
import com.os.course.model.entity.SongMetadata;
import com.os.course.persistent.repository.SongMetadataRepository;
import com.os.course.service.SongMetadataService;
import com.os.course.service.configuration.ServiceConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {Main.class, ServiceConfiguration.class})
public class SongMetadataServiceImplTest {

    @Autowired
    private SongMetadataService songMetadataService;
    @MockBean
    private SongMetadataRepository repository;

    @Test
    public void givenSongMetadataDto_whenSaveSongMetadataDto_thenReturnSongMetadataDtoWithId() {
        Long id  = 1L;
        String name = "qqq";
        Long resourceId = 1L;
        SongMetadataDto songMetadataDto = new SongMetadataDto();

        songMetadataDto.setName(name);
        songMetadataDto.setResourceId(resourceId);

        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setName(name);
        songMetadata.setResourceId(resourceId);

        SongMetadata savingObject = new SongMetadata();
        savingObject.setId(id);
        savingObject.setName(name);
        savingObject.setResourceId(resourceId);

        Mockito.when(repository.save(songMetadata)).thenReturn(savingObject);

        SongMetadataDto actual = songMetadataService.save(songMetadataDto);
        songMetadataDto.setId(id);
        Assertions.assertEquals(songMetadataDto, actual);
    }

    @Test
    public void givenSongMetadataDtoToDelete_whenDelete_thenReturnDeletedSongMetadataDto() {
        Long id  = 1L;

        String name = "qqq";
        Long resourceId = 1L;
        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setId(id);
        songMetadata.setName(name);
        songMetadata.setDeleted(false);
        songMetadata.setResourceId(resourceId);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(songMetadata));

        SongMetadata savingObject = new SongMetadata();
        savingObject.setId(id);
        savingObject.setName(name);
        savingObject.setDeleted(true);
        savingObject.setResourceId(resourceId);

        Mockito.when(repository.save(songMetadata)).thenReturn(savingObject);

        DeletedFilesDto actual = songMetadataService.delete("1");
        DeletedFilesDto expected = new DeletedFilesDto();
        expected.getIds().add(id);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void givenSongMetadataId_whenGetFileById_thenReturnSongMetadataDto() {
        Long id  = 1L;

        String name = "qqq";
        Long resourceId = 1L;
        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setId(id);
        songMetadata.setName(name);
        songMetadata.setResourceId(resourceId);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(songMetadata));

        SongMetadataDto actual = songMetadataService.getFileBy(id);

        SongMetadataDto expected = new SongMetadataDto();
        expected.setId(id);
        expected.setName(name);
        expected.setResourceId(resourceId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void whenGetAllFiles_thenReturnSongMetadataDtoList() {
        Long id  = 1L;
        String name = "qqq";
        Long resourceId = 1L;
        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setId(id);
        songMetadata.setName(name);
        songMetadata.setResourceId(resourceId);

        List<SongMetadata> songMetadataList = new ArrayList<>();
        songMetadataList.add(songMetadata);

        Mockito.when(repository.findAll()).thenReturn((songMetadataList));

        List<SongMetadataDto> actual = songMetadataService.getAllFiles();

        SongMetadataDto songMetadataDto = new SongMetadataDto();
        songMetadataDto.setId(id);
        songMetadataDto.setName(name);
        songMetadataDto.setResourceId(resourceId);

        List<SongMetadataDto> expected = new ArrayList<>();
        expected.add(songMetadataDto);

        Assertions.assertEquals(expected, actual);
    }
}