package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoMem implements OrderDao {

    private List<Order> data = new ArrayList<>();
    private static OrderDaoMem instance = null;

    private OrderDaoMem() {}

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Order order) {
        order.setId(data.size() + 1);
        data.add(order);
    }

    @Override
    public void remove(int id) { data.remove(id); }

    @Override
    public void removeLineItem(LineItem lineItem) {
        for (Order order : data) {
            if (order.getProducts().contains(lineItem)) {
                order.getProducts().remove(order.getProducts().indexOf(lineItem));
            }
        }
    }

    @Override
    public Order find(int id) { return data.stream().filter(order -> order.getId() == id).findFirst().orElse(null); }

    @Override
    public List<Order> getAll() { return data; }
}
