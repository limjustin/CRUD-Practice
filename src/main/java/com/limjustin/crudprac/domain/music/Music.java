package com.limjustin.crudprac.domain.music;

import javax.persistence.*;

@Entity
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String artist;

    private String album;

    @Column(length = 5000)  // 일단 5000자 제한으로 넣어보기!
    private String lyrics;

    // Constructor
    public Music(String title, String artist, String album, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.lyrics = lyrics;
    }

    // Getter
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