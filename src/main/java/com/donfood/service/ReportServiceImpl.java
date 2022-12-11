package com.donfood.service;

import com.donfood.dao.IONGRepository;
import com.donfood.dao.ReportRepository;
import com.donfood.dao.RestaurantRepository;
import com.donfood.domain.Report;
import com.donfood.dto.ReportRequestDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.ReportMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private IONGRepository ongRepository;
    @Override
    public Report create(ReportRequestDTO reportRequestDTO) {
        if(reportRequestDTO.getOngId() == null)
            throw new IllegalArgumentException("Report must be assigned to an ONG");
        if(ongRepository.existsById(reportRequestDTO.getOngId()) == false)
            throw new IllegalArgumentException("ONG provided does not exist.");

        if(reportRequestDTO.getRestaurantId() == null)
            throw new IllegalArgumentException("Report must be assigned to a restaurant");
        if(restaurantRepository.existsById(reportRequestDTO.getRestaurantId()) == false)
            throw new IllegalArgumentException("Restaurant provided does not exist.");

        Report report = ReportMapper.requestToReport(reportRequestDTO);
        reportRepository.save(report);

        return report;
    }

    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public Report getById(Long id) {
        if(id == null)
            throw new IllegalArgumentException("The id is null");
        try{
            Report report = reportRepository.findById(id).get();
            return report;
        }catch(NoSuchElementException e){
            throw new ResourceNotFoundException("No report with that id.");
        }
    }

    @Override
    public Report update(Long id, ReportRequestDTO reportRequestDTO) {
        if(!reportRepository.existsById(id))
            throw new ResourceNotFoundException("The report with id " + id + " was not found");

        Report report = reportRepository.getReferenceById(id);
        if(Strings.isNotEmpty(reportRequestDTO.getReason())){
            report.setReportReason(reportRequestDTO.getReason());
        }
        return reportRepository.save(report);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw new IllegalArgumentException("The id is null");
        if(!reportRepository.existsById(id))
            throw new ResourceNotFoundException("The report with id " + id + " was not found");
        try{
            reportRepository.deleteById(id);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error while deleting resource");
        }
    }
}
