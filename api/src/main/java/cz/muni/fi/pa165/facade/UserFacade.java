package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.dto.UserDto;


import javax.validation.ValidationException;
import java.util.Optional;

/**
 * User facade interface
 * @author lsolodkova
 */
public interface UserFacade {
    /**
     * Search for user by id
     *
     * @param id primary key assigned to a row in database
     * @return DTO of a user found
     */
    Optional<UserDto> findUserById(Long id);

    /**
     * Search for user by username
     *
     * @param name username
     * @return DTO of a user found
     */
    Optional<UserDto> findByName(String name);

    /**
     * registers a new user in the system
     *
     * @param name username
     * @param email user's email
     * @param rawPassword plaintext password chosen by user
     * @return id of a newly registered user
     */
    Long registerUser(String name, String email, String rawPassword) throws ValidationException;

    /**
     * Checks if a user with given credentials exists in the system
     *
     * @param uAuthDto DTO of user credentials: name, password
     * @return true, if user with given name exists and password matches the hash; otherwise false
     */
    boolean authenticate(UserAuthenticateDto uAuthDto);

    /**
     * Checks if a user with given credentials has administrative privileges
     *
     * @param userDto DTO of user: id, name, email
     * @return true, if user exists AND has admin privileges; otherwise false
     */
    boolean isAdmin(UserDto userDto);

    /**
     * Removes a particular user
     *
     * @param userDto DTO of user to be removed: id, name, email
     * @return nothing on success; throws an exception if such user does not exist in the system
     */
    void removeUser(UserDto userDto);

}
