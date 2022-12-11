package com.donfood.domain;

import javax.validation.constraints.NotNull;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurantId")
    private Restaurant restaurant;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ongId")
    private ONG ong;

    @NotNull
    @Column(name="reason")
    private String reportReason;
}
