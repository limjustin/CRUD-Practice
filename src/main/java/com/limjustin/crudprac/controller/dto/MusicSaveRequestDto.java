package com.limjustin.crudprac.controller.dto;

import com.limjustin.crudprac.domain.music.Music;

public class MusicSaveRequestDto {
    private String title;
    private String artist;
    private String album;
    private String lyrics;

    // Constructor
    public MusicSaveRequestDto() {}

    public MusicSaveRequestDto(String title, String artist, String album, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.lyrics = lyrics;
    }

    // Getter
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

    // Return to entity (repository.save()에 넘겨질 때는 Music 객체 타입으로 넘어가야)
    public Music toEntity() {
        return new Music(title, artist, album, lyrics);
    }
}
