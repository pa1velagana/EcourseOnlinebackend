package com.example.RegisterLogin.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private String fullName;

    private String location;

    private String userEmail;

    private BigInteger phoneNumber;
}
