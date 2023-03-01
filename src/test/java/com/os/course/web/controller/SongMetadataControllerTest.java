package com.os.course.web.controller;

import com.os.course.Main;
import com.os.course.model.dto.DeletedFilesDto;
import com.os.course.model.dto.SongMetadataDto;
import com.os.course.model.entity.SongMetadata;
import com.os.course.persistent.repository.SongMetadataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class SongMetadataControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SongMetadataRepository repository;

    @BeforeEach
    public void setUp() {
        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setId(1L);
        songMetadata.setName("qqq");
        songMetadata.setResourceId(1L);
        songMetadata.setDeleted(false);
        repository.save(songMetadata);
    }

    @Test
    public void givenSongMetadataDto_whenSaveSongMetadataDto_thenReturnSongMetadataDtoWithId() {
        Long id  = 1L;
        String name = "qqq";
        Long resourceId = 1L;

        SongMetadataDto songMetadataDto = new SongMetadataDto();
        songMetadataDto.setId(id);
        songMetadataDto.setName(name);
        songMetadataDto.setResourceId(resourceId);

        ResponseEntity<SongMetadataDto> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/v1/songs",
                songMetadataDto, SongMetadataDto.class);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void givenSongMetadataId_whenGetFileById_thenReturnSongMetadataDto() {
        ResponseEntity<SongMetadataDto> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/v1/songs/1",
                SongMetadataDto.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void givenSongMetadataDtoToDelete_whenDelete_thenReturnDeletedSongMetadataDto() {
        ResponseEntity<DeletedFilesDto> responseEntity = restTemplate.exchange("http://localhost:" + port + "/v1/songs?id=1",
                HttpMethod.DELETE, HttpEntity.EMPTY, DeletedFilesDto.class);
        Assertions.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }
}