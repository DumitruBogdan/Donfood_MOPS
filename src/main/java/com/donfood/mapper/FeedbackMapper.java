package com.donfood.mapper;

import com.donfood.domain.Feedback;
import com.donfood.dto.FeedbackDTO;

public class FeedbackMapper {
    public static FeedbackDTO doToDto(Feedback feedback) {
        return FeedbackDTO.builder()
                .orderId(feedback.getOrderId())
                .comment(feedback.getComment())
                .rating(feedback.getRating())
                .createdAt(feedback.getCreatedAt())
                .build();
    }

    public static Feedback dtoToDo(FeedbackDTO feedbackDto) {
        return Feedback.builder()
                .orderId(feedbackDto.getOrderId())
                .comment(feedbackDto.getComment())
                .rating(feedbackDto.getRating())
                .createdAt(feedbackDto.getCreatedAt())
                .build();
    }
}
