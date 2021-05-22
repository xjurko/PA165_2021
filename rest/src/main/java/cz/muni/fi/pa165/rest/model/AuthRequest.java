package cz.muni.fi.pa165.rest.model;


import lombok.Data;

@Data
public class AuthRequest {
    final String username;
    final String password;
}
