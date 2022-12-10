package com.donfood.domain;

import com.donfood.domain.enums.Measure;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "donation")
@AllArgsConstructor
@NoArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "restaurantId")
    private Long restaurantId;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "restaurantId", insertable = false, updatable = false)
    private Restaurant restaurant;

    @NotNull
    @Column(name="expirationDate")
    private Timestamp expirationDate;

    @NotNull
    @Column(name="quantity")
    private Double quantity;

    @NotNull
    @Column(name="quantityMeasure")
    private Measure quantityMeasure;

    @NotNull
    @Column(name="product")
    private String product;

    @NotNull
    @Column(name="pickUpLocation")
    private String pickUpLocation;

    @NotNull
    @Column(name="pickUpTime")
    private Time pickUpTime;

    @Column(name="photo")
    private String photo;

    @Column(name="createdAt")
    private Timestamp createdAt;

    @Column(name="modifiedAt")
    private Timestamp modifiedAt;

    // one to many with order
    @OneToMany(mappedBy = "donation")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();
}
