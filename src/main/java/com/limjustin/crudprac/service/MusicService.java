package com.limjustin.crudprac.service;

import com.limjustin.crudprac.controller.dto.MusicSaveRequestDto;
import com.limjustin.crudprac.domain.music.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusicService {
    private final MusicRepository musicRepository;

    @Autowired
    public MusicService(MusicRepository musicRepository) {
        System.out.println("MusicService.MusicService");
        this.musicRepository = musicRepository;
    }

    @Transactional
    public Long save(MusicSaveRequestDto requestDto) {
        System.out.println("MusicService.save");
        return musicRepository.save(requestDto.toEntity()).getId();
    }
}
