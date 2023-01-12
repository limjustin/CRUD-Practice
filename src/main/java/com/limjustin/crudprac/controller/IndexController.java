package com.limjustin.crudprac.controller;

import com.limjustin.crudprac.controller.dto.MusicResponseDto;
import com.limjustin.crudprac.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    private final MusicService musicService;

    @Autowired
    public IndexController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("musics", musicService.findAllDesc());
        return "index";
    }

    @GetMapping("/music/save")
    public String musicSave() {
        return "music-save";
    }

    @GetMapping("/music/update/{id}")
    public String musicUpdate(@PathVariable Long id, Model model) {
        MusicResponseDto musicResponseDto = musicService.findById(id);
        model.addAttribute("music", musicResponseDto);
        return "music-update";
    }
}
