package com.donfood.service;

import com.donfood.domain.Feedback;
import com.donfood.dto.FeedbackRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    Feedback create(FeedbackRequestDTO feedbackRequestDTO);
    List<Feedback> getAll();
    Feedback getById(Long id);
    Feedback update(Long id, FeedbackRequestDTO feedbackRequestDTO);
    void delete(Long id);
}
