package com.donfood.domain;

import com.donfood.domain.enums.Status;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "order")
public class Order {
    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "donationId")
    private Donation donation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ongId")
    private ONG ong;

    @NotNull
    @Column(name="quantitySelected")
    private Double quantitySelected;

    @NotNull
    @Column(name="status")
    private Status status;

    @Column(name="createdAt")
    private Timestamp createdAt;
}
