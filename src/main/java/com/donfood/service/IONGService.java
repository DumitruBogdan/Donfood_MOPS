package com.donfood.service;

import com.donfood.domain.Account;
import com.donfood.dto.AccountRequestDTO;
import com.donfood.dto.ONGRequestDTO;
import com.donfood.dto.ONGResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IONGService {
    ONGResponseDTO register(ONGRequestDTO ongRequestDTO);
    ONGResponseDTO update(Long id, ONGRequestDTO ongRequestDTO);
    void delete(Long id);
    List<ONGResponseDTO> getAll();
    ONGResponseDTO getById(Long id);
    List<ONGResponseDTO> getByFullName(String fullName);
}
