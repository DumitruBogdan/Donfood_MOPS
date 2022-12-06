package com.donfood.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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
