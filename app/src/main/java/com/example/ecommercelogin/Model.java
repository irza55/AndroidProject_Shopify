package com.example.ecommercelogin;

/**
 * Created by ankit on 22/1/18.
 */

public class Model {
    public String description, image, title,price;

    public Model() {

    }

    public Model(String description, String image, String title,String price) {
        this.description = description;
        this.image = image;
        this.title = title;
        this.price=price;
    }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
