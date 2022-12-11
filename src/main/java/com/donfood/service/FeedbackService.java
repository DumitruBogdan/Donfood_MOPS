package com.donfood.service;

import com.donfood.domain.Feedback;
import com.donfood.dto.FeedbackDTO;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {

    FeedbackDTO getFeedbackById(Long feedbackId);
    FeedbackDTO createFeedback(FeedbackDTO feedback);
    FeedbackDTO updateFeedback(FeedbackDTO feedbackDTO);
    void deleteFeedback(Long feedbackId);
    //Feedback getFeedbackForSpecificOrder(Long orderId);
    //void deleteFeedbackFromSpecificOrder(Long orderId);
}
