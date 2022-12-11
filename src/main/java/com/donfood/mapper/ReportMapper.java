package com.donfood.mapper;

import com.donfood.domain.Order;
import com.donfood.domain.Report;
import com.donfood.dto.OrderRequestDTO;
import com.donfood.dto.ReportRequestDTO;

public class ReportMapper {
    public static Report requestToReport (ReportRequestDTO reportRequestDTO ){
        Report report = Report.builder()
                .id(reportRequestDTO.getId())
                .restaurantId(reportRequestDTO.getRestaurantId())
                .restaurant(reportRequestDTO.getRestaurant())
                .ongId(reportRequestDTO.getOngId())
                .ong(reportRequestDTO.getOng())
                .reportReason(reportRequestDTO.getReason())
                .build();
        return report;
    }
}
