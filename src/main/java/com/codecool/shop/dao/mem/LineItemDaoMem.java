package com.codecool.shop.dao.mem;

import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;

import java.util.ArrayList;
import java.util.List;

public class LineItemDaoMem implements LineItemDao {

    private List<LineItem> data = new ArrayList<>();
    private static LineItemDaoMem instance = null;

    private LineItemDaoMem() {}

    public static LineItemDaoMem getInstance() {
        if (instance == null) {
            instance = new LineItemDaoMem();
        }
        return instance;
    }

    @Override
    public void add(LineItem lineItem) {
        lineItem.setId(data.size() + 1);
        data.add(lineItem);
    }

    @Override
    public LineItem find(int id) { return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null); }

    @Override
    public void removeAll() { data = new ArrayList<>(); }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void removeByObject(LineItem lineItem) { data.remove(data.indexOf(lineItem)); }

    @Override
    public LineItem findByName(String name) { return data.stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null); }

    @Override
    public List<LineItem> getAll() { return data; }
}
