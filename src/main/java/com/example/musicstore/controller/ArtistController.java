package com.example.musicstore.controller;

import com.example.musicstore.model.Artist;
import com.example.musicstore.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/artists")
    public String getAllArtists(Model model) {
        List<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        return "artists";  // This will return the artists.html template
    }
    // Show the form to add a new artist
    @GetMapping("/artists/new")
    public String showAddArtistForm(Model model) {
        model.addAttribute("artist", new Artist());
        return "add-artist";  // View to add a new artist
    }

    // Handle the form submission to add a new artist
    @PostMapping("/artists")
    public String addArtist(@ModelAttribute Artist artist) {
        artistRepository.save(artist);  // Save the new artist to the database
        return "redirect:/artists";  // Redirect to the artists list page after saving
    }
}

