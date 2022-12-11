package com.donfood.service;

import com.donfood.dao.FeedbackRepository;
import com.donfood.domain.Feedback;
import com.donfood.dto.FeedbackDTO;
import com.donfood.exception.ResourceNotFoundException;
import com.donfood.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityExistsException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService{
    @Autowired
    FeedbackRepository feedbackRepository;
    @Override
    public FeedbackDTO getFeedbackById(Long feedbackId) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if(!feedback.isPresent()){
            throw new ResourceNotFoundException("The feedback with id: " + feedbackId + " was not found");
        }
        return FeedbackMapper.doToDto(feedback.get());
    }

    @Override
    public FeedbackDTO createFeedback(FeedbackDTO feedback) {
        if(feedbackRepository.existsById(feedback.getOrderId())){
            throw new EntityExistsException("This feedback id already exists");
        }
        return FeedbackMapper.doToDto(feedbackRepository.save(FeedbackMapper.dtoToDo(feedback)));
    }

    @Override
    public FeedbackDTO updateFeedback(FeedbackDTO feedbackDTO) {
        Optional<Feedback> databaseFeedback = feedbackRepository.findById(feedbackDTO.getOrderId());
        if (!databaseFeedback.isPresent()) {
            throw new ResourceNotFoundException("Feedback with id: " + feedbackDTO.getOrderId() + " was not found");
        }
        return FeedbackMapper.doToDto(feedbackRepository.save(validateFeedback(databaseFeedback.get(), feedbackDTO)));
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        if(!feedbackRepository.existsById(feedbackId)){
            throw new ResourceNotFoundException("Feedback with id: " + feedbackId + " was not found");
        }
        feedbackRepository.deleteById(feedbackId);
    }

    private Feedback validateFeedback(Feedback feedback, FeedbackDTO feedbackDTO) {
        if (feedbackDTO.getOrderId() != null) {
            feedback.setOrderId(feedbackDTO.getOrderId());
        }
        if (StringUtils.hasText(feedbackDTO.getComment())) {
            feedback.setComment(feedbackDTO.getComment());
        }
        if (feedbackDTO.getRating() > 0 && feedbackDTO.getRating() < 11) {
            feedback.setRating(feedbackDTO.getRating());
        }
        if(feedbackDTO.getCreatedAt() != null){
            feedback.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        return feedback;

    }
}
