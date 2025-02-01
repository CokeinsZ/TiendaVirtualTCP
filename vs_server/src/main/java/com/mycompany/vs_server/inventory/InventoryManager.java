/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.inventory;

import java.util.ArrayList;

/**
 *
 * @author Alejandro Carvajal
 */
public class InventoryManager {
    private static InventoryManager instance;
    private ArrayList<Product> productos;

    private InventoryManager() {
        //The constructor is private in order to prevent the creation of another instances
        this.productos = new ArrayList<>();
    }
    
    

    public static synchronized InventoryManager getInstance() {
        //This method is used to obtain de unique instance of the function        
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }
    
    /*this method adds a product for the array  */
    public void addProduct (String name,String id ){
        Product p = new Product(name,id);     
        productos.add(p);
    }
    
    public void deleteProduct(){
        
    }
}
