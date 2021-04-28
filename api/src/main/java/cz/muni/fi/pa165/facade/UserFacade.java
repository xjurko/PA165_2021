package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.dto.UserDto;


import javax.validation.ValidationException;
import java.util.Optional;

/**
 * @author lsolodkova
 */
public interface UserFacade {
    Optional<UserDto> findUserById(Long id);
    Optional<UserDto> findByName(String name);
    Iterable<UserDto> getAllUsers();
    Long registerUser(String name, String email, String rawPassword) throws ValidationException;
    boolean authenticate(UserAuthenticateDto u);
    boolean isAdmin(UserDto userDto);
    void removeUser(UserDto userDto);

}
