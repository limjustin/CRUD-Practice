package com.limjustin.crudprac.domain.music;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MusicRepositoryTest {

    @Autowired MusicRepository musicRepository;

    @AfterEach
    void AfterEach() {
        musicRepository.deleteAll();
    }

    @Test
    void 게시글_저장() {
        // given
        Music music1 = new Music("Sneakers", "ITZY", "CHECKMATE", "Put my sneakers on!");
        Music music2 = new Music("Loco", "ITZY", "CRAZY IN LOVE", "I'm gettin loco loco");
        Music result1 = musicRepository.save(music1);
        Music result2 = musicRepository.save(music2);

        // when
        List<Music> musicList = musicRepository.findAll();

        // then
        // 실제로 DB에 제대로 저장되었는지 iterator 통해서 확인
        for (Music m : musicList) {
            System.out.println(m.getId());
            System.out.println(m.getTitle());
            System.out.println(m.getArtist());
            System.out.println(m.getAlbum());
            System.out.println(m.getLyrics());
        }
        assertThat(result1.getTitle()).isEqualTo("Sneakers");
        assertThat(result2.getAlbum()).isEqualTo("CRAZY IN LOVE");
    }
}