package com.limjustin.crudprac.domain.music;

import javax.persistence.*;

@Entity
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String album;

    @Column(length = 5000)  // 일단 5000자 제한으로 넣어보기!
    private String lyrics;

    // Constructor
    public Music() {}  // 기본 생성자 추가, Because of 'No default constructor for entity'

    public Music(String title, String artist, String album, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.lyrics = lyrics;
    }

    // Getter
    public Long getId() {
        System.out.println("Music.getId");
        return id;
    }

    public String getTitle() {
        System.out.println("Music.getTitle");
        return title;
    }

    public String getArtist() {
        System.out.println("Music.getArtist");
        return artist;
    }

    public String getAlbum() {
        System.out.println("Music.getAlbum");
        return album;
    }

    public String getLyrics() {
        System.out.println("Music.getLyrics");
        return lyrics;
    }

    // update?
    public void update(String title, String artist, String album, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.lyrics = lyrics;
    }
}