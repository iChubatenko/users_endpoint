package com.ihorchubatenko.spring.web.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
