package com.donfood.service;

import com.donfood.dao.FeedbackRepository;
import com.donfood.dao.IOrderRepository;
import com.donfood.domain.Feedback;
import com.donfood.dto.FeedbackRequestDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityExistsException;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public Feedback create(FeedbackRequestDTO feedbackRequestDTO) {
        if(feedbackRequestDTO.getOrderId() == null)
            throw new IllegalArgumentException("Feedback must be assigned to an Order");
        if(orderRepository.existsById(feedbackRequestDTO.getOrderId()) == false)
            throw new IllegalArgumentException("Order provided does not exist.");

        Feedback feedback = FeedbackMapper.requestToFeedback(feedbackRequestDTO);
        feedbackRepository.save(feedback);
        return feedback;
    }

    @Override
    public List<Feedback> getAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback getById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("The id is null");
        }
        try{
            Feedback feedback = feedbackRepository.findById(id).get();
            return feedback;
        }catch (NoSuchElementException e){
            throw new ResourceNotFoundException("No feedback with this id");
        }
    }

    @Override
    public Feedback update(Long id, FeedbackRequestDTO feedbackRequestDTO) {
        if(!feedbackRepository.existsById(id))
            throw new ResourceNotFoundException("The feedback with id " + id + " was not found");

        Feedback feedback = feedbackRepository.getReferenceById(id);
        if(feedbackRequestDTO.getComment() != null){
            feedback.setComment(feedbackRequestDTO.getComment());
        }
        if(feedbackRequestDTO.getRating() > 0 && feedbackRequestDTO.getRating() < 11){
            feedback.setRating(feedbackRequestDTO.getRating());
        }
        feedback.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return feedbackRepository.save(feedback);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw new IllegalArgumentException("The id is null");
        if(!feedbackRepository.existsById(id))
            throw new ResourceNotFoundException("The feedback with id " + id + " was not found");
        try{
            feedbackRepository.deleteById(id);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error while deleting resource");
        }
    }
}
