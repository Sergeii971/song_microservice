package com.os.course.persistent.repository;

import com.os.course.model.dto.SongMetadataDto;
import com.os.course.model.entity.SongMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongMetadataRepository extends CrudRepository<SongMetadata, Long> {
    List<SongMetadata> findAll();
}
