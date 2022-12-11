package com.donfood.dto;

import com.donfood.domain.Order;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class FeedbackRequestDTO {
    private Long orderId;
    private Order order;
    private String comment;
    private int rating;
    private Timestamp createdAt;
}
