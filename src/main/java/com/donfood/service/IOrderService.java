package com.donfood.service;

import com.donfood.domain.Order;
import com.donfood.dto.OrderRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IOrderService {
    Order create(OrderRequestDTO orderRequestDTO);
    List<Order> getAll();
    Order getById(Long id);
    Order update(Long id, OrderRequestDTO orderRequestDTO);
    void delete(Long id);
}
