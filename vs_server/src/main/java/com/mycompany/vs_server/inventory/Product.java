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

    public Product(String name, String id, String description, String price, String stock) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

   

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String Stock) {
        this.stock = Stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    
}
