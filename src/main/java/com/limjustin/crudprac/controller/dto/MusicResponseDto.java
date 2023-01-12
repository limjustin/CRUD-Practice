package com.limjustin.crudprac.controller.dto;

import com.limjustin.crudprac.domain.music.Music;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Optional;

public class MusicResponseDto {
    private Long id;  // id 값도 보여줘야지!
    private String title;
    private String artist;
    private String album;
    private String lyrics;

    // Default Constructor
    public MusicResponseDto() {}

    // Constructor
    public MusicResponseDto(Music entity) {
        System.out.println("MusicResponseDto.MusicResponseDto Constructor Call");
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.artist = entity.getArtist();
        this.album = entity.getAlbum();
        this.lyrics = entity.getLyrics();
        System.out.println("MusicResponseDto.MusicResponseDto Constructor End");
    }

    public Long getId() {
        System.out.println("MusicResponseDto.getId");
        return id;
    }

    public String getTitle() {
        System.out.println("MusicResponseDto.getTitle");
        return title;
    }

    public String getArtist() {
        System.out.println("MusicResponseDto.getArtist");
        return artist;
    }

    public String getAlbum() {
        System.out.println("MusicResponseDto.getAlbum");
        return album;
    }

    public String getLyrics() {
        System.out.println("MusicResponseDto.getLyrics");
        return lyrics;
    }
}
