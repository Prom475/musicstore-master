package com.example.musicstore.repository;

import com.example.musicstore.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Component
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByAlbumId(Long albumId);
}

