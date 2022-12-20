package com.donfood.mapper;

import com.donfood.domain.ONG;
import com.donfood.dto.ONGRequestDTO;
import com.donfood.dto.ONGResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ONGMapper {
    private static AccountMapper accountMapper;
    public static ONG requestToONG(ONGRequestDTO ongRequestDTO){
        return ONG.builder()
                .accountId(ongRequestDTO.getAccountId())
                .address(ongRequestDTO.getAddress())
                .socialScore(ongRequestDTO.getSocialScore())
                .build();
    }

    public static ONGResponseDTO ONGToResponse(ONG ong){
        return ONGResponseDTO.builder()
                .accountId(ong.getAccountId())
                .accountONG(ong.getAccountONG())
                .address(ong.getAddress())
                .socialScore(ong.getSocialScore())
                .favRestaurants(ong.getFavRestaurants())
                .reports(ong.getReports())
                .orders(ong.getOrders())
                .build();
    }

    public List<ONG> requestToONGList(List<ONGRequestDTO> ongRequestDTOS) {
        return ongRequestDTOS.stream()
                .map(ONGMapper::requestToONG)
                .collect(Collectors.toList());
    }

    public static List<ONGResponseDTO> ONGToResponseList(List<ONG> ong) {
        return ong.stream()
                .map(ONGMapper::ONGToResponse)
                .collect(Collectors.toList());
    }
}
