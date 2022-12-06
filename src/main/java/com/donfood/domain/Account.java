package com.donfood.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private Integer accessRights;
    private Date createAt;
    private Boolean accountVerified;

    @OneToOne(mappedBy = "account")
    private Restaurant restaurant;
}
