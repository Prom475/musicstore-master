package com.example.musicstore.repository;

import com.example.musicstore.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtist_Id(Long artistId);


    List<Album> findAll();

    List<Album> findByTitleContainingIgnoreCase(String query);

}
