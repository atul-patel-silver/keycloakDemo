package com.datmt.keycloak.springbootauth.http.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CreateUserRequest {
    String id;
    String username;
    String password;
    String email;
    String firstname;
    String lastname;
}
