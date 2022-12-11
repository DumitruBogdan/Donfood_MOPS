package com.donfood.service;

import com.donfood.domain.Order;
import com.donfood.domain.Report;
import com.donfood.dto.OrderRequestDTO;
import com.donfood.dto.ReportRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportService {
    Report create(ReportRequestDTO reportRequestDTO);
    List<Report> getAll();
    Report getById(Long id);
    Report update(Long id, ReportRequestDTO reportRequestDTO);
    void delete(Long id);
}
