package com.donfood.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "ong")
@AllArgsConstructor
@NoArgsConstructor
public class ONG {
    @Id
    @Column(name="accountId")
    private Long accountId;

    @NotNull
    @OneToOne
    @MapsId
    @JoinColumn(name="accountId")
    private Account accountONG;

    @NotNull
    @Column(name="address")
    private String address;

    @Column(name="socialScore")
    private Double socialScore;

    //many to many with favorite restaurants
    @ManyToMany(fetch=FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE
            })
    @JoinTable(name = "favoriterest",
            joinColumns = {@JoinColumn (name = "ongId")},
            inverseJoinColumns = {@JoinColumn (name = "restaurantId")}
    )
    private Set<Restaurant> favRestaurants = new HashSet<>();

    public void addRestaurant(Restaurant restaurant){
        this.favRestaurants.add(restaurant);
        restaurant.getFavOngs().add(this);
    }

    // one to many with report
    @OneToMany(mappedBy = "ong")
    private Set<Report> reports = new HashSet<>();

    // one to many with order
    @OneToMany(mappedBy = "ong")
    private Set<Report> orders = new HashSet<>();

}
