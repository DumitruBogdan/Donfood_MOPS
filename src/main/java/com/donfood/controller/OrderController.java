package com.donfood.controller;

import com.donfood.domain.Order;
import com.donfood.dto.OrderRequestDTO;
import com.donfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/new")
    public Order create(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.create(orderRequestDTO);
    }

    @GetMapping
    public List<Order> getAll(){
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id){
        return orderService.getById(id);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.update(id, orderRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ orderService.delete(id);}
}
