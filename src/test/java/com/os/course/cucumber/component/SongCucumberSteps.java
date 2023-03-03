package com.os.course.cucumber.component;

import com.os.course.model.dto.SongMetadataDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class SongCucumberSteps {

    @Autowired
    private TestRestTemplate template;

    private String URL;

    @Given("The application is available at {string}")
    public void applicationBaseUrl(String baseUrl) {
        log.info("Application URL -> " + baseUrl);
        URL = baseUrl;
    }

    @When("User provides song metadata to upload {string}")
    public void upload_metadata_response(String metadata) {
        ResponseEntity<SongMetadataDto> response = template.postForEntity(URL, getDto(), SongMetadataDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        log.info("Metadata -> " + metadata);
        log.info("Id received -> " + response.getBody());
    }

    @Then("User should receive id {int} of the saved record")
    public void assert_post_metadata_response_result(Integer id) {
        log.info("Metadata received -> " + id);
        assertNotNull(id);
    }

    @When("User passes id {int} of a song record")
    public void get_metadata_response(Integer id) {
        ResponseEntity<SongMetadataDto> response = template.getForEntity(URL + id, SongMetadataDto.class);
        log.info("Metadata received -> " + response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("User should receive song metadata {string}")
    public void assert_get_metadata_response_result(String metadata) {
        log.info("Metadata received -> " + metadata);
        assertNotNull(metadata);
    }

    private SongMetadataDto getDto() {
        SongMetadataDto dto = new SongMetadataDto();
        dto.setName("test");
        dto.setLength("99999");
        dto.setAlbum("test-album");
        dto.setArtist("test-artist");
        dto.setResourceId(999L);

        return dto;
    }

}
