package com.codecool.shop.dao.mem;


import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Genre;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Artist;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDaoMem implements ProductDao {

    private List<Product> data = new ArrayList<>();
    private static ProductDaoMem instance = null;

    private ProductDaoMem() {}

    public static ProductDaoMem getInstance() {
        if (instance == null) {
            instance = new ProductDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        product.setId(data.size() + 1);
        data.add(product);
    }

    @Override
    public Product find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public void removeAll() {

    }

    @Override
    public List<Product> getAll() {
        return data;
    }

    @Override
    public Product findByName(String name) { return data.stream().filter(t -> t.getDescription() == name).findFirst().orElse(null); }

    @Override
    public List<Product> getBy(Artist artist) {
        return data.stream().filter(t -> t.getSupplier().equals(artist)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getBy(Genre genre) {
        return data.stream().filter(t -> t.getProductCategory().equals(genre)).collect(Collectors.toList());
    }
}
