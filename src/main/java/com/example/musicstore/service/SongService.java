package com.example.musicstore.service;

import com.example.musicstore.model.Album;
import com.example.musicstore.model.Song;
import com.example.musicstore.repository.AlbumRepository;
import com.example.musicstore.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;
    @Autowired
    private AlbumRepository albumRepository;

    public void getSongsByAlbum(Long albumId) {
        songRepository.findByAlbumId(albumId);
    }

    public void addSongForm(Long albumId) {
        albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));
    }

    public void addSong(Long albumId, Song song, MultipartFile audioFile) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));

        if (!audioFile.isEmpty()) {
            String fileName = audioFile.getOriginalFilename();
            String uploadDir = "uploads/audio/";
            Path uploadPath = Paths.get(uploadDir);

            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(audioFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                song.setAudioFile(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        song.setAlbum(album);
        songRepository.save(song);
    }

    public void editSong(Long songId) {
        songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid song ID"));
    }
}