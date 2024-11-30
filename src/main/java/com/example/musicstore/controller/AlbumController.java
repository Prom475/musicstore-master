package com.example.musicstore.controller;

import com.example.musicstore.model.Album;
import com.example.musicstore.model.Artist;
import com.example.musicstore.model.User;
import com.example.musicstore.repository.AlbumRepository;
import com.example.musicstore.repository.ArtistRepository;
import com.example.musicstore.repository.UserRepository;
import com.example.musicstore.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private UserRepository userRepository;


    // Get all albums
    @GetMapping("/")
    public String getAllAlbums(Model model) {
        model.addAttribute("albums", albumRepository.findAll());
        return "index";
    }

    // Show the form to add a new album with artistId from URL
    @GetMapping("/albums/add")
    public String showAddAlbumForm(@RequestParam("artistId") Long artistId, Model model) {
        model.addAttribute("artistId", artistId);
        model.addAttribute("album", new Album());
        return "add-album";
    }
//    @GetMapping("/albums/add")
//    public String showAddAlbumForm( Model model) {
//        model.addAttribute("album", new Album());
//        model.addAttribute("artists", artistRepository.findAll());
//        return "add-albumAndArtist";
//    }

    // Save the album
    @PostMapping("/albums")
    public String addAlbum(@RequestParam("artistId") Long artistId, @ModelAttribute Album album) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new IllegalArgumentException("Invalid artist ID"));
        album.setArtist(artist);  // Set the artist to the album
        albumRepository.save(album);  // Save the album with the artist association
        return "redirect:/albums?artistId=" + artistId;  // Redirect to the albums list for this artist
    }


    @GetMapping("/albums")
    public String getAllAlbums(@RequestParam("artistId") Long artistId, Model model) {
        List<Album> albums = albumRepository.findByArtist_Id(artistId); // Fetch albums by artist ID
        model.addAttribute("albums", albums); // Add albums to the model
        model.addAttribute("artistId", artistId); // Add artistId to the model for the "Add Album" link
        return "albums"; // Return the view (albums.html)
    }
    @GetMapping("/albums/edit")
    public String editAlbum(@RequestParam Long albumId, Model model) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));

        model.addAttribute("album", album);
        model.addAttribute("artists", artistRepository.findAll());  // Assuming an artist list is needed
        return "edit-album";  // Create an edit-album.html template
    }

    // Handle the form submission for editing an album
    @PostMapping("/albums/edit")
    public String updateAlbum(@ModelAttribute Album album) {
        albumRepository.save(album);  // Save the updated album
        return "redirect:/albums?artistId=" + album.getArtist().getId();  // Redirect to the album list for the artist
    }

    // Delete an album
    @GetMapping("/albums/delete")
    public String deleteAlbum(@RequestParam Long albumId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid album ID"));

        Long artistId = album.getArtist().getId();  // Get the artist ID before deleting
        albumRepository.delete(album);  // Delete the album

        return "redirect:/albums?artistId=" + artistId;  // Redirect to the album list for the artist
    }


}
