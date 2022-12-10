package com.donfood.controller;

import com.donfood.domain.Donation;
import com.donfood.dto.DonationRequestDTO;
import com.donfood.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donation")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @PostMapping("/new")
    public Donation create(@RequestBody DonationRequestDTO donationRequestDTO){
        return donationService.create(donationRequestDTO);
    }

    @GetMapping
    public List<Donation> getAll(){
        return donationService.getAll();
    }

    @GetMapping("/{id}")
    public Donation getById(@PathVariable Long id){
        return donationService.getById(id);
    }

    @GetMapping("/filter/{product}")
    public List<Donation> getByProduct(@PathVariable String product){
        return donationService.findByProduct(product);
    }

    @PutMapping("/{id}")
    public Donation update(@PathVariable Long id, @RequestBody DonationRequestDTO donationRequestDTO){
        return donationService.update(id, donationRequestDTO);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        donationService.delete(id);
    }
}
