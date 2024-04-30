package com.example.RegisterLogin.modals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "location")
    private String location;

    @Column(name = "phone_number")
    private BigInteger phoneNumber;

    @Column(name = "user_email")
    private String userEmail;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
