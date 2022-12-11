package com.donfood.mapper;

import com.donfood.domain.Feedback;
import com.donfood.dto.FeedbackRequestDTO;

public class FeedbackMapper {

    public static Feedback requestToFeedback (FeedbackRequestDTO feedbackRequestDTO ){
        Feedback feedback = Feedback.builder()
                .orderId(feedbackRequestDTO.getOrderId())
                .order(feedbackRequestDTO.getOrder())
                .comment(feedbackRequestDTO.getComment())
                .rating(feedbackRequestDTO.getRating())
                .createdAt(feedbackRequestDTO.getCreatedAt())
                .build();
        return feedback;
    }
}
