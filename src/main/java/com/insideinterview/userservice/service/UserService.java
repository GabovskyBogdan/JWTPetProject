package com.insideinterview.userservice.service;

import com.insideinterview.userservice.domain.AppUser;
import com.insideinterview.userservice.domain.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    AppUser getUser(String username);

    List<AppUser> getUsers();
}
