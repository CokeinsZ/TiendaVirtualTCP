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

    public Product(String name, String id) {
        this.name = name;
        this.id = id;
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
    
    
    
}
