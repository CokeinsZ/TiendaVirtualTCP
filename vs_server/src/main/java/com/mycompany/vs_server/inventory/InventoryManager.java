/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.inventory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
    public  synchronized void addProduct (String name,String id, String price, String stock, String description){
        Product p = new Product( name,  id,  description,  price,  stock);     
        productos.add(p);
    }
    /*this metodes deletes a product by id */
    public synchronized void deleteProduct(String id){
          Iterator<Product> iterator = productos.iterator();
    while (iterator.hasNext()) {
        Product product = iterator.next();
        if (product.getId().equals(id)) {
            iterator.remove();
            System.out.println("Producto con la ID " + id + " ha sido borrado.");
            return;
        }
    }
    System.out.println("Producto con la ID " + id + " no encontrado.");
    }
    
    public synchronized void changeNameProuct (String id, String name ){
         Iterator<Product> iterator = productos.iterator();
        while (iterator.hasNext()) {
        Product product = iterator.next();
        if (product.getId().equals(id)) {
            product.setName(name);
            System.out.println("Producto con la ID " + id + " ha sido borrado.");
            return;
        }
            System.out.println("Producto con la ID " + id + " no encontrado.");

        }
    }
    
     public synchronized void changeDescriptionProuct (String id, String name ){
         Iterator<Product> iterator = productos.iterator();
        while (iterator.hasNext()) {
        Product product = iterator.next();
        if (product.getId().equals(id)) {
            product.setName(name);
            System.out.println("Producto con la ID " + id + " ha sido borrado.");
            return;
        }
            System.out.println("Producto con la ID " + id + " no encontrado.");

        }
        
         
     }
     private synchronized Product foundProuct(String id){
              Iterator<Product> iterator = productos.iterator();
        while (iterator.hasNext()) {
        Product product = iterator.next();
        if (product.getId().equals(id)) {
              System.out.println("Producto con la ID " + id + " ha sido encontrado.");
            return product;
        }
         System.out.println("Producto con la ID " + id + " no encontrado.");
       
        } return null;
        }
     
     public synchronized void changepriceProduct(String id, String price){
         Product product = foundProuct(id);
         product.setPrice(price);
               
    }
     
     
      public synchronized void changeStockProduct(String id, String stock){
         Product product = foundProuct(id);
         product.setPrice(stock);
               
    }
     
      public synchronized String foundIdProductsbyName (String name){
              Iterator<Product> iterator = productos.iterator();
        while (iterator.hasNext()) {
        Product product = iterator.next();
        if (product.getId().equals(name)) {
              System.out.println("Producto con el Nombre " + name + " ha sido encontrado.");
            return product.getId();
        }
         System.out.println("Producto con el nombre " + name  + " no encontrado.");
       
        } return null;
        }
      
       public void generateCSVReport(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("ID,Name,Description,Price,Stock\n");  // columnas del csv

            for (Product product : productos) {
                writer.append(product.getId())
                      .append(",")
                      .append(product.getName())
                      .append(",")
                      .append(product.getDescription())
                      .append(",")
                      .append(product.getPrice())
                      .append(",")
                      .append(product.getStock())
                      .append("\n");
            }

            System.out.println("CSV report generated successfully!");

        } catch (IOException e) {
            System.err.println("Error while generating CSV report: " + e.getMessage());
        }
       }
        public String generateReport() {
        String writer="" ;
            writer= writer+("ID,Name,Description,Price,Stock\n");  // columnas del csv

            for (Product product : productos) {
                writer=writer +
                      (product.getId())
                      +(",")
                      +(product.getName())
                      +(",")
                      +(product.getDescription())
                      +(",")
                      +(product.getPrice())
                      +(",")
                      +(product.getStock())
                      +("\n");
            }

            System.out.println(" reporte generado");
            return writer;
        }
    }

            
