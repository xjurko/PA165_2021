package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.dto.UserDto;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lsolodkova
 */
public interface UserFacade {
    Optional<UserDto> findUserById(Long id);
    Optional<UserDto> findByName(String name);
    Iterable<UserDto> getAll();
    void registerUser(UserDto userDto, String unencryptedPassword);
    boolean authenticate(UserAuthenticateDto u);
    boolean isAdmin(UserDto userDto);
}
