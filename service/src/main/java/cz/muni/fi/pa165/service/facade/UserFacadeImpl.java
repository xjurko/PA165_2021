package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.dto.UserDto;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Optional;

/**
 * @author lsolodkova
 */

@Service
@RequiredArgsConstructor
@Transactional
public class UserFacadeImpl implements UserFacade {

    final UserService userService;
    final BeanConverter converter;

    @Override
    public Optional<UserDto> findUserById(Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(u -> converter.convert(u, UserDto.class));
    }

    @Override
    public Optional<UserDto> findByName(String name) {
        Optional<User> user = userService.findByName(name);
        return user.map(u -> converter.convert(u, UserDto.class));
    }

    @Override
    public Long registerUser(String name, String email, String rawPassword) throws ValidationException {
        User newUser = userService.registerUser(name, email, rawPassword);
        return newUser.getId();
    }

    @Override
    public boolean authenticate(UserAuthenticateDto uAuthDto) {
        Optional<User> user = userService.findByName(uAuthDto.getName());
        if(user.isEmpty()) return false;
        return userService.authenticate(user.get(), uAuthDto.getPassword());
    }

    @Override
    public boolean isAdmin(UserDto userDto) {
        return userService.isAdmin(converter.convert(userDto, User.class));
    }

    @Override
    public void removeUser(Long userId){
        userService.removeUser(userId);
    }
}
