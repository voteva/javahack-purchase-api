package com.purchase.admin.model.in;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthenticationRequest {

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String password;
}
