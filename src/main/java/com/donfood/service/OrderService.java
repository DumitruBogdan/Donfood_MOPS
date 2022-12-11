package com.donfood.service;

import com.donfood.dao.IDonationRepository;
import com.donfood.dao.IONGRepository;
import com.donfood.dao.IOrderRepository;
import com.donfood.domain.Donation;
import com.donfood.domain.Order;
import com.donfood.dto.DonationRequestDTO;
import com.donfood.dto.OrderRequestDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static com.donfood.domain.enums.Status.NEW;
@Service
public class OrderService implements IOrderService{

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IDonationRepository donationRepository;

    @Autowired
    private IONGRepository ongRepository;

    @Override
    public Order create(OrderRequestDTO orderRequestDTO) {

        if(orderRequestDTO.getOngId() == null)
            throw new IllegalArgumentException("Donation must be assigned to an ONG");
        if(ongRepository.existsById(orderRequestDTO.getOngId()) == false)
            throw new IllegalArgumentException("ONG provided does not exist.");

        if(orderRequestDTO.getQuantitySelected() == null)
            throw new IllegalArgumentException("Provide a quantity");

        Donation donation;
        if(orderRequestDTO.getDonationId() == null)
            throw new IllegalArgumentException("Order must contain a donation");
        try{
            donation= donationRepository.findById(orderRequestDTO.getDonationId()).get();
            if(orderRequestDTO.getQuantitySelected() > donation.getQuantity())
                throw new IllegalArgumentException("Input quantity is bigger than the available quantity.");
            donation.setQuantity(donation.getQuantity() - orderRequestDTO.getQuantitySelected());
        }
        catch(NoSuchElementException e){
            throw new ResourceNotFoundException("No donation with that id.");
        }

        orderRequestDTO.setStatus(NEW);
        orderRequestDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Order order = OrderMapper.requestToOrder(orderRequestDTO);
        orderRepository.save(order);

        donationRepository.save(donation);
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        try{
            Order order = orderRepository.findById(id).get();
            return order;
        }
        catch(NoSuchElementException e){
            throw new ResourceNotFoundException("No order with that id.");
        }
    }

    @Override
    public Order update(Long id, OrderRequestDTO orderRequestDTO) {
        if (!orderRepository.existsById(id))
            throw new ResourceNotFoundException("The order with id " + id + " was not found");

        Order order = orderRepository.getReferenceById(id);
        if(orderRequestDTO.getQuantitySelected() != null){
            Donation donation = donationRepository.findById(order.getDonationId()).get();
            if(orderRequestDTO.getQuantitySelected() > donation.getQuantity())
                throw new IllegalArgumentException("Input quantity is bigger than the available quantity.");
            order.setQuantitySelected(orderRequestDTO.getQuantitySelected());
        }
        if(orderRequestDTO.getStatus() != null)
            order.setStatus(orderRequestDTO.getStatus());
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("The id is null");
        if (!orderRepository.existsById(id))
            throw new ResourceNotFoundException("The order with id " + id + " was not found");
        try{
            orderRepository.deleteById(id); //has delete cascade
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error while deleting resource");
        }
    }
}
