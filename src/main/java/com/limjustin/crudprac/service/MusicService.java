package com.limjustin.crudprac.service;

import com.limjustin.crudprac.controller.dto.MusicListResponseDto;
import com.limjustin.crudprac.controller.dto.MusicResponseDto;
import com.limjustin.crudprac.controller.dto.MusicSaveRequestDto;
import com.limjustin.crudprac.controller.dto.MusicUpdateRequestDto;
import com.limjustin.crudprac.domain.music.Music;
import com.limjustin.crudprac.domain.music.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // @Transactional  // 이거 모두 붙어야하나? (findById는 굳이? 데이터 찾는거니까 저장이 아니고! 그래서 쓸 필요가 없는듯..?)
    public MusicResponseDto findById(Long id) {
        Optional<Music> result = musicRepository.findById(id);
        Music entity = new Music();

        if (result.isPresent()) {  // Optional 처리 이렇게 해줘야하나..?
            System.out.println("MusicService.findById.result.isPresent()");
            entity = result.get();
        } else {
            System.out.println("적절한 예외 처리 필요!");
        }

        System.out.println("MusicService.findById.return MusicResponseDto");
        return new MusicResponseDto(entity);
        // MusicResponseDto(Music entity) 생성자 통해서 값만 매칭되서 보여주면 되는거 아닌가? Getter가 여기서 왜 더 필요하지?
    }

    @Transactional
    public Long update(Long id, MusicUpdateRequestDto requestDto) {
        // 여기서 id랑 requestDto를 어떻게 활용하냐
        Optional<Music> result = musicRepository.findById(id);

        if (result.isPresent()) {
            System.out.println("isPresent in update");
            Music music = result.get();  // Optional을 벗겨서 가져옴!
            music.update(requestDto.getTitle(), requestDto.getArtist(), requestDto.getAlbum(), requestDto.getLyrics());
        } else {
            System.out.println("적절한 예외 처리 필요!");
        }

        return id;
    }

    public Long delete(Long id) {
        Optional<Music> result = musicRepository.findById(id);

        if (result.isPresent()) {
            Music music = result.get();
            musicRepository.delete(music);
        } else {
            System.out.println("적절한 예외 처리 필요!");
        }
        return id;
    }

    @Transactional
    public List<MusicListResponseDto> findAllDesc() {
        return musicRepository.findAllDesc().stream()
                .map(MusicListResponseDto::new)
                .collect(Collectors.toList());
    }
}
