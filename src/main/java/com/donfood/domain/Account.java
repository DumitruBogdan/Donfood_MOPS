package com.donfood.domain;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ONG ong;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Restaurant restaurant;

    @Email(message = "Email should be valid")
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
