package com.limjustin.crudprac.domain.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    @Query("SELECT m FROM Music m ORDER BY m.id DESC")
    List<Music> findAllDesc();
}