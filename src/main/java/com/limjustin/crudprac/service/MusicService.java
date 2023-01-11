package com.limjustin.crudprac.service;

import com.limjustin.crudprac.controller.dto.MusicResponseDto;
import com.limjustin.crudprac.controller.dto.MusicSaveRequestDto;
import com.limjustin.crudprac.controller.dto.MusicUpdateRequestDto;
import com.limjustin.crudprac.domain.music.Music;
import com.limjustin.crudprac.domain.music.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional  // 이거 모두 붙어야하나?
    public MusicResponseDto findById(Long id) {
        Optional<Music> result = musicRepository.findById(id);
        Music entity = new Music();

        if (result.isPresent()) {  // Optional 처리 이렇게 해줘야하나..?
            System.out.println("isPresent");
            entity = result.get();
            return new MusicResponseDto(entity);
        } else {
            System.out.println("적절한 예외 처리 필요!");
        }

        return new MusicResponseDto(entity);
    }

    @Transactional
    public Long update(Long id, MusicUpdateRequestDto requestDto) {
        return null;
    }
}
