package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;

import java.util.List;

public interface LineItemDao {

    void add(LineItem lineItem);
    LineItem find(int id);
    LineItem findByName(String name);
    void remove(int id);
    void removeByObject(LineItem lineItem);

    List<LineItem> getAll();
}
