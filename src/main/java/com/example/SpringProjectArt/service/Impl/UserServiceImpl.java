package com.example.SpringProjectArt.service.Impl;

import ch.qos.logback.classic.Logger;
import com.example.SpringProjectArt.model.Role;
import com.example.SpringProjectArt.model.Status;
import com.example.SpringProjectArt.model.User;
import com.example.SpringProjectArt.repository.RoleRepository;
import com.example.SpringProjectArt.repository.UserRepository;
import com.example.SpringProjectArt.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role>  userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);

        User registeredUser = userRepository.save(user);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
