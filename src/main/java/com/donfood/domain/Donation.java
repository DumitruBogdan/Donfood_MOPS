package com.donfood.domain;

import com.donfood.domain.enums.Measure;
import javax.validation.constraints.NotNull;
import lombok.Data;
import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "donation")
public class Donation {

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurantId")
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
    private Set<Order> orders = new HashSet<>();
}
