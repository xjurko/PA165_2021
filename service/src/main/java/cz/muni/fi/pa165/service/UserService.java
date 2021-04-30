package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.User;

import java.util.Optional;

public interface UserService {
    //stub
    Optional<User> findUserById(Long userId);
}
