package com.donfood.mapper;

import com.donfood.domain.Account;
import com.donfood.domain.Order;
import com.donfood.dto.AccountRequestDTO;
import com.donfood.dto.OrderRequestDTO;

public class OrderMapper {
    public static Order requestToOrder (OrderRequestDTO orderRequestDTO ){
        Order order = Order.builder()
                .donationId(orderRequestDTO.getDonationId())
                .donation(orderRequestDTO.getDonation())
                .ongId(orderRequestDTO.getOngId())
                .ong(orderRequestDTO.getOng())
                .quantitySelected(orderRequestDTO.getQuantitySelected())
                .status(orderRequestDTO.getStatus())
                .createdAt(orderRequestDTO.getCreatedAt())
                .build();
        return order;
    }
}
