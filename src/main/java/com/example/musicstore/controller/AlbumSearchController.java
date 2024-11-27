package com.example.musicstore.controller;

import com.example.musicstore.model.Album;
import com.example.musicstore.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/search")
public class AlbumSearchController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    @ResponseBody
    public List<Album> searchAlbums(@RequestParam("query") String query) {
        return albumService.searchAlbums(query);  // Return albums matching the query
    }

}
