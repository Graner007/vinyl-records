package com.codecool.shop.dao;

import com.codecool.shop.model.User;

import java.util.List;

public interface UserDao {

    void add(User user);
    void remove(int id);
    void removeUser(User user);
    User find(int id);

    List<User> getAll();
}
