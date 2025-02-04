/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.inventory;

import java.io.File;
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
        
        addTestProducts();
    }
    
    private void addTestProducts() {
        this.productos.add(new Product("Producto 1", "1", "Muy lindo producto 1", "10000", "10"));
        this.productos.add(new Product("Producto 2", "2", "Muy lindo producto 2", "10000", "10"));
        this.productos.add(new Product("Producto 3", "3", "Muy lindo producto 3", "10000", "10"));
        this.productos.add(new Product("Producto 4", "4", "Muy lindo producto 4", "10000", "10"));
        this.productos.add(new Product("Producto 5", "5", "Muy lindo producto 5", "10000", "10"));
        
    }
    
    public static synchronized InventoryManager getInstance() {
        //This method is used to obtain de unique instance of the function        
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }
    
    
    /*this method adds a product for the array  */
    public synchronized String addProduct (String id, String name, String description, String price, String stock){
        if (foundProuct(id) != null) {
            return "ERROR:Product with id: " + id + " already exist";
        }
        
        Product p = new Product( name,  id,  description,  price,  stock);     
        productos.add(p);
        return("SUCCES:Product added");
    }
    
    public synchronized String addProduct (String name, String description, String price, String stock){        
        //Busqueda del id disponible
        String id = ""+(productos.size()+1);
        int i = 2;
        while (foundProuct(id) != null) {            
            id = ""+(productos.size()+i);
            i++;
        }
        
        Product p = new Product( name,  id,  description,  price,  stock);     
        productos.add(p);
        return("SUCCES:Product added");
    }
    
    /*this metodes deletes a product by id */
    public synchronized String deleteProduct(String id){
        Iterator<Product> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId().equals(id)) {
                iterator.remove();
                return "SUCCES:Product deleted";
            }
        }
        
        return "ERROR:Product with ID " + id + " not found.";
    }
    
    public synchronized String changeNameProuct (String id, String name ){
        Iterator<Product> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId().equals(id)) {
                product.setName(name);
                return "SUCCES:Product name changed";
            }
        }
        
        return "ERROR:Product with ID: " + id + " not found";
    }
    
    public synchronized String changeDescriptionProuct (String id, String description ){
        Iterator<Product> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId().equals(id)) {
                product.setDescription(description);
                return "SUCCES:Description updated";
            }
        }
        
        return "ERROR:Product with ID: " + id + " not found";
    }
    
    private synchronized Product foundProuct(String id){
        Iterator<Product> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId().equals(id)) {
                System.out.println("Producto con la ID " + id + " ha sido encontrado.");
                return product;
            }
        }
        
        return null;
    }
     
    public synchronized String changePriceProduct(String id, String price){
        Product product = foundProuct(id);
        if (product != null) {
            product.setPrice(price);
            return "SUCCES:Product price updated";
        }
        
        return "ERROR:Product with ID: " + id + " not found";               
    }
     
     
    public synchronized String changeStockProduct(String id, String stock){
        Product product = foundProuct(id);
        if (product != null) {
            product.setPrice(stock);
            return "SUCCES:Product price updated";
        }
        
        return "ERROR:Product with ID: " + id + " not found";
    }
     
    public synchronized ArrayList<Product> foundProductsbyName (String name){
        Iterator<Product> iterator = productos.iterator();
        ArrayList<Product> productsFound = new ArrayList<>();
        
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getName().contains(name)) {
               productsFound.add(product);
            }
        }
        
        if (productsFound.isEmpty()) {
            return null;
        }
        
        return productsFound;
    }
      
    public File generateCSVReport(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("ID,Name,Description,Price,Stock\n");  // columnas del csv

            for (Product product : productos) {
                writer.append(product.toString(","));
            }

            System.out.println("CSV report generated successfully!");

        } catch (IOException e) {
            System.err.println("Error while generating CSV report: " + e.getMessage());
        }
        
        return new File(fileName);
    }
    
    public String generateReport() {
        String writer= "";
        writer = writer + ("ID,Name,Description,Price,Stock\n");  // columnas del csv

        for (Product product : productos) {
            writer = writer + product.toString();                      
        }

        System.out.println(" reporte generado");
        return writer;
    }
    
    public ArrayList<Product> getProductsPaginated(int page, int pageSize) {
        int totalProducts = productos.size();

        int fromIndex = (page -1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalProducts);

        if (fromIndex >= totalProducts) {
            return new ArrayList<>(); // No hay más productos
        }

        return new ArrayList<>(productos.subList(fromIndex, toIndex));
    }

    public String changeProduct(String id, String name, String description, String price, String stock) {
        Product product = foundProuct(id);
        if (product != null) {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            
            return "SUCCES:Product updated";
        }
        
        return "ERROR:Product with ID: " + id + " not found";

    }

}

            
