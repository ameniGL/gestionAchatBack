package com.example.recherche.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Produit {
    @Id
    private int id;
    private String category;
    private String title;
    private String brand;
    private String price;
    private double rating;
    private String shipping;
    private String image;
    private double comment;
    private double globalRating;
    private String url;
    private String provider;
    private Boolean liked;
    private Boolean disliked;
    public Produit() {}

    public Produit(int id, String category, String title, String brand, String price, double rating, String shipping, String image, double comment, double globalRating, String url, String provider) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.brand = brand;
        this.price = price;
        this.rating = rating;
        this.shipping = shipping;
        this.image = image;
        this.comment = comment;
        this.globalRating = globalRating;
        this.url = url;
        this.provider = provider;
    }
}
