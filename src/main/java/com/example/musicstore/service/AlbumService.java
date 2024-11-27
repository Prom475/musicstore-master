package com.example.musicstore.service;

import com.example.musicstore.model.Album;
import com.example.musicstore.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;  // Assume you have a repository for album data

    public List<Album> searchAlbums(String query) {
        return albumRepository.findByTitleContainingIgnoreCase(query);  // Search by title, ignoring case
    }
}
