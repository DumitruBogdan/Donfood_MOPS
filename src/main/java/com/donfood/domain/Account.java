package com.donfood.domain;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private String passwordEncoded;

    @NotNull
    @Column(name="fullName")
    private String fullName;

    @NotNull
    @Column(name="accessRights")
    private Integer accessRights; // 0=admin, 1=ong, 2=restaurant

    @NotNull
    @Column(name="createdAt")
    private Timestamp createAt;

    @NotNull
    @Column(name="accountVerified")
    private Boolean accountVerified;
}
