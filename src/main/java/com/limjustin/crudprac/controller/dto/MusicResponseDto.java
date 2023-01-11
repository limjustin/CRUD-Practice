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
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.artist = entity.getArtist();
        this.album = entity.getAlbum();
        this.lyrics = entity.getLyrics();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getLyrics() {
        return lyrics;
    }
}
