package com.donfood.domain;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @OneToOne(mappedBy = "accountONG", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ONG ong;

    @OneToOne(mappedBy = "accountRest", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Restaurant restaurant;

    @NotNull
    @Column(name="email")
    private String email;

    @NotNull
    @Column(name="password")
    private String password;

    @NotNull
    @Column(name="fullName")
    private String fullName;

    @Column(name="accessRights")
    private Integer accessRights;

    @Column(name="createdAt")
    private Timestamp createAt;

    @Column(name="accountVerified")
    private Boolean accountVerified;
}
