package com.example.musicstore.controller;

import com.example.musicstore.model.Album;
import com.example.musicstore.model.Song;
import com.example.musicstore.repository.AlbumRepository;
import com.example.musicstore.repository.SongRepository;
import com.example.musicstore.service.FilesStorageService;
import com.example.musicstore.service.FilesStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Controller
public class SongController {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private FilesStorageService filesStorageService;

    @GetMapping("/songs")
    public String getSongsByAlbum(@RequestParam Long albumId, Model model) {
        List<Song> songs = songRepository.findByAlbumId(albumId);
        model.addAttribute("songs", songs);
        model.addAttribute("albumId", albumId);
        return "songs";
    }

    @GetMapping("/songs/add")
    public String addSongForm(@RequestParam Long albumId, Model model) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));

        model.addAttribute("album", album);
        model.addAttribute("albums", albumRepository.findAll());
        model.addAttribute("song", new Song());
        return "add-song";
    }

    @PostMapping("/songs")
    public String addSong(
            @RequestParam Long albumId,
            @ModelAttribute Song song,
            Model model) {

        // Validate album
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));


        // Associate the song with the album and save
        song.setAlbum(album);
        songRepository.save(song);

        return "redirect:/songs?albumId=" + albumId;
    }


    @GetMapping("/songs/edit")
    public String editSong(@RequestParam Long songId, Model model) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid song ID"));
        Album album = albumRepository.findById(song.getAlbum().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));
        model.addAttribute("album", album);
        model.addAttribute("albums", albumRepository.findAll());


        model.addAttribute("song", song);
        return "edit-song";
    }

    @PostMapping("/songs/edit")
    public String updateSong(@ModelAttribute Song song) {
        songRepository.save(song);
        return "redirect:/songs?albumId=" + song.getAlbum().getId();
    }

    @GetMapping("/songs/delete")
    public String deleteSong(@RequestParam Long songId, Model model) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid song ID"));

        Long albumId = song.getAlbum().getId();
        songRepository.delete(song);

        return "redirect:/songs?albumId=" + albumId;
    }
}