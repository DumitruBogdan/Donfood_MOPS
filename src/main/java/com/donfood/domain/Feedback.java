package com.donfood.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.NoArgsConstructor;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@Table(name = "feedback")
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    @Column(name="orderId")
    private Long orderId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name="orderId",insertable = false, updatable = false)
    private Order order;

    @NotNull
    @Column(name="comment")
    private String comment;

    @NotNull
    @Column(name="rating")
    private Integer rating;

    @Column(name="createdAt")
    private Timestamp createdAt;

}
