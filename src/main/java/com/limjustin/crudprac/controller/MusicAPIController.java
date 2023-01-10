package com.limjustin.crudprac.controller;

import com.limjustin.crudprac.controller.dto.MusicSaveRequestDto;
import com.limjustin.crudprac.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicAPIController {
    private final MusicService musicService;

    @Autowired
    public MusicAPIController(MusicService musicService) {
        System.out.println("MusicAPIController.MusicAPIController");
        this.musicService = musicService;
    }

    @PostMapping("/api/v1/music")
    public Long save(@RequestBody MusicSaveRequestDto requestDto) {
        System.out.println("MusicAPIController.save");
        return musicService.save(requestDto);
    }

}
