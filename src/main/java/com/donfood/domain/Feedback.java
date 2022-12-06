package com.donfood.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "feedback")
public class Feedback {
    @Id
    @Column(name="orderId")
    private Long orderId;

    @NotNull
    @OneToOne
    @MapsId
    @JoinColumn(name="orderId")
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
