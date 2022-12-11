package com.donfood.controller;

import com.donfood.domain.Order;
import com.donfood.domain.Report;
import com.donfood.dto.OrderRequestDTO;
import com.donfood.dto.ReportRequestDTO;
import com.donfood.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/new")
    public Report create(@RequestBody ReportRequestDTO reportRequestDTO){
        return reportService.create(reportRequestDTO);
    }

    @GetMapping
    public List<Report> getAll(){
        return reportService.getAll();
    }

    @GetMapping("/{id}")
    public Report getById(@PathVariable Long id){
        return reportService.getById(id);
    }

    @PutMapping("/{id}")
    public Report update(@PathVariable Long id, @RequestBody ReportRequestDTO reportRequestDTO){
        return reportService.update(id, reportRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ reportService.delete(id);}
}
