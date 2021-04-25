package cz.muni.fi.pa165.service.facade;

import cz.muni.fi.pa165.dto.MovieDto;
import cz.muni.fi.pa165.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.dto.UserDto;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.facade.UserFacade;
import cz.muni.fi.pa165.service.MovieService;
import cz.muni.fi.pa165.service.UserService;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

/**
 * @author lsolodkova
 */

@Service
@RequiredArgsConstructor
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
    public Iterable<UserDto> getAll() {
        return converter.convert(userService.getAll(), UserDto.class);
    }

    @Override
    public void registerUser(UserDto userDto, String unencryptedPassword) {
        User user = converter.convert(userDto, User.class);
        userService.registerUser(user, unencryptedPassword);
        userDto.setId(user.getId());
    }

    @Override
    public boolean authenticate(UserAuthenticateDto u) {
        Optional<User> user = userService.findById(u.getId());
        if(user.isEmpty()) return false;
        return userService.authenticate(user.get(), u.getPassword());
    }

    @Override
    public boolean isAdmin(UserDto userDto) {
        return userService.isAdmin(converter.convert(userDto, User.class));
    }
}
