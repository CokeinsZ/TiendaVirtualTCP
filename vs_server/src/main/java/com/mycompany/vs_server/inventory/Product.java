/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.inventory;

/**
 *
 * @author Alejandro Carvajal
 * @author Jhonatan Chica
 */

public class Product {
    //The name and id  is private in order to only acces whit set and get 
    /*name as pructs name 
    id as products id */
    private String name;
    private  String id ;
    private String description;
    private String price;
    private String stock;
/**
 * Constructor to create a new product.
 * @param name The name of the product.
 * @param stock The  avalability of the product.
 * @param id The name of the product.
 * @param description The name of the product.
 * @param price The name of the product.
 */

    public Product(String name, String id, String description, String price, String stock) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

   
/**
 * Gets the description of the product.
 * @return The description of the product.
 */

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
/**
 * Gets the price of the product.
 * @return The price of the product.
 */
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
/**
 * Gets the stock of the product.
 * @return The stock of the product.
 */
    public String getStock() {
        return stock;
    }

    public void setStock(String Stock) {
        this.stock = Stock;
    }
/**
 * Gets the id of the product.
 * @return The id of the product.
 */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
/**
 * Gets the name of the product.
 * @return The name of the product.
 */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.getId() 
                + (":")
                + (this.name)
                + (":")
                + (this.description)
                + (":")
                + (this.price)
                + (":")
                + (this.stock)
                + ("\n");
    }
    
    public String toString(String separator) {
        return this.getId() 
                + (separator)
                + (this.name)
                + (separator)
                + (this.description)
                + (separator)
                + (this.price)
                + (separator)
                + (this.stock)
                + ("\n");
    }
    
}
