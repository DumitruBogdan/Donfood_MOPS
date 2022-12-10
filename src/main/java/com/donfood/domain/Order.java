package com.donfood.domain;

import com.donfood.domain.enums.Status;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "donationId")
    private Donation donation;

    @NotNull
    @ManyToOne
    @JsonIgnore
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
