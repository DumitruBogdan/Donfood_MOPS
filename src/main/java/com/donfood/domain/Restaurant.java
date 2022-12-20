package com.donfood.domain;
import javax.validation.constraints.NotNull;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @Column(name="accountId")
    private Long accountId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name="accountId", insertable = false, updatable = false)
    private Account accountRest;

    @NotNull
    @Column(name="fiscalIdCode")
    private String fiscalIdCode;

    @NotNull
    @Column(name="nrPeopleHelping")
    private Integer nrPeopleHelping;

    //many to many with favorite restaurants
    @ManyToMany(fetch=FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE
            },
            mappedBy = "favRestaurants"
    )
    private Set<ONG> favOngs = new HashSet<>();

    //one to many with donation
    @OneToMany(mappedBy = "restaurant")
    private Set<Donation> donations = new HashSet<>();

    //one to many with report
    @OneToMany(mappedBy = "restaurant")
    private Set<Report> reports = new HashSet<>();
}
