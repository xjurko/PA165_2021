package cz.muni.fi.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO with the authentication info of a user logging in
 * @author lsolodkova
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticateDto {
    private String name;
    private String password;
}
