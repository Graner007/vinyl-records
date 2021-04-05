package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;

import java.util.List;

public interface OrderDao {

    void add(Order LineItem);
    void remove(int id);
    void removeLineItem(LineItem lineItem);
    Order find(int id);

    List<Order> getAll();

}
