package com.limjustin.crudprac.controller;

import com.limjustin.crudprac.controller.dto.MusicResponseDto;
import com.limjustin.crudprac.controller.dto.MusicSaveRequestDto;
import com.limjustin.crudprac.controller.dto.MusicUpdateRequestDto;
import com.limjustin.crudprac.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class MusicAPIController {
    private final MusicService musicService;

    @Autowired
    public MusicAPIController(MusicService musicService) {
        System.out.println("MusicAPIController.MusicAPIController");
        this.musicService = musicService;
    }

    @PostMapping("/api/v1/music")  // 등록 (POST-CREATE)
    public Long save(@RequestBody MusicSaveRequestDto requestDto) {
        System.out.println("MusicAPIController.save");
        return musicService.save(requestDto);
    }  // Why return Long type?

    @GetMapping("/api/v1/music/{id}")  // 조회 (GET-READ)
    public MusicResponseDto findById(@PathVariable Long id) {
        System.out.println("MusicAPIController.findById");
        MusicResponseDto responseDto = musicService.findById(id);
        System.out.println("Come back to Controller");
        return responseDto;  // 아마 여기서 return 하는데 Getter 사용해서 보여줘야하니까
    }  // Why return DTO type? : 그 DTO 객체를 보여줘야하니까? 조회니까?

    @PutMapping("/api/v1/music/{id}")  // 수정 (PUT-UPDATE)
    public Long update(@PathVariable Long id, @RequestBody MusicUpdateRequestDto requestDto) {  // 수정 내용은 requestDto 변수에 넣었음
        return musicService.update(id, requestDto);
    }  // Why return Long type?

    @DeleteMapping("/api/v1/music/{id}")  // 삭제 (DELETE-DELETE)
    public Long delete(@PathVariable Long id) {
        return musicService.delete(id);
    }
}
