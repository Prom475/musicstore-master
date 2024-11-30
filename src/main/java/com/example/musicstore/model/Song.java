package com.example.musicstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@AllArgsConstructor@NoArgsConstructor

public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String duration;
    @Transient
    private  String artist;
    private Double price;
    private String previewUrl;
    private String audioFile;




    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

}
