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
@Builder
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "donationId")
    private Long donationId;

    //@NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "donationId", insertable = false, updatable = false)
    private Donation donation;

    @NotNull
    @Column(name = "ongId")
    private Long ongId;

    //@NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ongId", insertable = false, updatable = false)
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
