package com.donfood.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "restaurantId")
    private Long restaurantId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "restaurantId", insertable = false, updatable = false)
    private Restaurant restaurant;

    @NotNull
    @Column(name = "ongId")
    private Long ongId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ongId", insertable = false, updatable = false)
    private ONG ong;

    @NotNull
    @Column(name="reason")
    private String reportReason;
}
