package com.donfood.domain;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

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
