package com.donfood.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class FeedbackDTO {
    private Long orderId;
    private String comment;
    private int rating;
    private Timestamp createdAt;
}
