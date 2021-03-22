package com.codecool.shop.model;

import java.util.Currency;

public class Product extends BaseModel {

    private float defaultPrice;
    private Currency defaultCurrency;
    private Genre genre;
    private Artist artist;


    public Product(String name, float defaultPrice, String currencyString, String description, Genre genre, Artist artist) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(artist);
        this.setProductCategory(genre);
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    public void setPrice(float price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    public Genre getProductCategory() {
        return genre;
    }

    public void setProductCategory(Genre genre) {
        this.genre = genre;
        this.genre.addProduct(this);
    }

    public Artist getSupplier() {
        return artist;
    }

    public void setSupplier(Artist artist) {
        this.artist = artist;
        this.artist.addProduct(this);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "genre: %5$s, " +
                        "artist: %6$s",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.genre.getName(),
                this.artist.getName());
    }
}
