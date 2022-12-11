package com.donfood.service;

import com.donfood.domain.Donation;
import com.donfood.dto.DonationRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDonationService {
    Donation create(DonationRequestDTO donationRequestDTO);
    List<Donation> getAll();
    List<Donation> findByProduct(String product);
    Donation getById(Long id);
    Donation update(Long id, DonationRequestDTO donationRequestDTO);
    void delete(Long id);
}
