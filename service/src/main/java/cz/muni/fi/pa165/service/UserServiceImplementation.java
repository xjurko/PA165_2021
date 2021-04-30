package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    //stub
    @Override
    public Optional<User> findUserById(Long userId) {
        return Optional.empty();
    }
}
