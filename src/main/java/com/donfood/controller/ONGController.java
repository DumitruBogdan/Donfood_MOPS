package com.donfood.controller;

import com.donfood.dto.ONGRequestDTO;
import com.donfood.dto.ONGResponseDTO;
import com.donfood.service.ONGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ong")
public class ONGController {

    @Autowired
    private ONGService ongService;

    @GetMapping
    public List<ONGResponseDTO> getAll(){
        return ongService.getAll();
    }

    @GetMapping("/{id}")
    public ONGResponseDTO getById(@PathVariable Long id){
        return ongService.getById(id);
    }

    @PutMapping("/{id}")
    public ONGResponseDTO updateONG(@PathVariable Long id, @RequestBody ONGRequestDTO ongRequestDTO){
        return ongService.update(ongRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteONG(@PathVariable Long id){
        ongService.delete(id);
    }
}