/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.networkLayer;

import com.mycompany.vs_server.inventory.InventoryManager;
import com.mycompany.vs_server.inventory.Product;
import com.mycompany.vs_server.logging.LogGenerator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Alejandro Carvajal
 */
public class ClientHandler extends Thread {
    private final SSLSocket clientSocket;
    private final InventoryManager inventoryManager;
    private final LogGenerator logger;            

    public ClientHandler(SSLSocket clientSocket) {
        this.clientSocket = clientSocket;
        
        this.inventoryManager = InventoryManager.getInstance();
        this.logger = LogGenerator.getInstance();
    }

    @Override
    public void run() {
        try {
            String clientIP = clientSocket.getInetAddress().toString();
            System.out.println("Connected from: " + clientIP);

            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            // Enviar los primero 10 productos al conectarse
            String initialProducts = buildProductsResponse(inventoryManager.getProductsPaginated(0, 10));
            outputStream.writeUTF(initialProducts);
            outputStream.flush();
            
            while (true) {
                String clientMessage;
                try {
                    System.out.println("Esperando mensaje...");
                    clientMessage = inputStream.readUTF();
                                        
                } catch (IOException e) {
                    System.out.println("Cliente desconectado: " + clientIP);
                    break; // Salir del bucle cuando el cliente se desconecta
                }

                System.out.println("Client message: " + clientMessage);
                String[] parts = clientMessage.trim().split(":");

                String response = processCommand(parts, clientIP);
                outputStream.writeUTF(response);
                outputStream.flush();
            }
            
        } catch (SocketException ex) {
            System.out.println("Conexión reseteada por el cliente.");
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, "Error en la comunicación", ex);
            
        } finally {
            try {
                clientSocket.close();
                System.out.println("Conexión cerrada con el cliente: " + clientSocket.getInetAddress().toString());
                
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, "Error al cerrar el socket", ex);
            }
        }
    }

    
    
    
    private String processCommand(String[] command, String clientIP) {
        String response;
        try {
            switch (command[0].toUpperCase()) {
                case "HI":
                    return "SUCCES:Hola " + clientIP;
                    
                case "ADD":
                    response = inventoryManager.addProduct(command[1], command[2], command[3], command[4], command[5]);
                    if (response.contains("SUCCES")) 
                        logger.log(clientIP, "ADD", command[2]);
                    
                    return response;
                    
                case "DELETE":
                    response = inventoryManager.deleteProduct(command[1]);
                    if (response.contains("SUCCES")) 
                        logger.log(clientIP, "DELETE", command[1]);
                    
                    return response;
                    
                case "UPDATE_NAME":
                    response = inventoryManager.changeNameProuct(command[1], command[2]);
                    if (response.contains("SUCCES")) 
                        logger.log(clientIP, "UPDATE_NAME", command[1]);
                    
                    return response;
                    
                case "UPDATE_DESCRIPTION":
                    response = inventoryManager.changeDescriptionProuct(command[1], command[2]);
                    if (response.contains("SUCCES")) 
                        logger.log(clientIP, "UPDATE_DESCRIPTION", command[1]);
                    
                    return response;
                    
                case "UPDATE_STOCK":
                    response = inventoryManager.changeStockProduct(command[1], command[2]);
                    if (response.contains("SUCCES")) 
                        logger.log(clientIP, "UPDATE_STOCK", command[1]);
                    
                    return response;
                    
                case "UPDATE_PRICE":
                    response = inventoryManager.changePriceProduct(command[1], command[2]);
                    if (response.contains("SUCCES")) 
                        logger.log(clientIP, "UPDATE_PRICE", command[1]);
                    
                    return response;
                    
                case "SEARCH_NAME":
                    ArrayList<Product> foundProducts = inventoryManager.foundProductsbyName(command[1]);
                    
                    if (foundProducts != null) {
                        response = buildProductsResponse(foundProducts);
                        logger.log(clientIP, "UPDATE_NAME", command[1]);
                    
                    } else {
                        response = "ERROR:No products found";
                        
                    }
                    
                    return response;
                    
                case "REPORT":
                    return inventoryManager.generateReport();

                case "LIST_PAGE":
                    try {
                        int page = Integer.parseInt(command[1]);
                        ArrayList<Product> paginatedProducts = inventoryManager.getProductsPaginated(page, 10);
                        
                        if (paginatedProducts.isEmpty()) {
                            return "ERROR:No more products available";
                        }
                        
                        return buildProductsResponse(paginatedProducts);
                    
                    } catch (NumberFormatException e) {
                        return "ERROR:Invalid page number";
                    }

                default:
                    return "ERROR:Comando no reconocido";
            }
        } catch (Exception e) {
            return "ERROR:" + e.getMessage();
        }
    }

    private String buildProductsResponse(ArrayList<Product> products) {
        StringBuilder sb = new StringBuilder();
        
        for (Product foundProduct : products) {
            sb.append(foundProduct.toString());
        }
    
        return sb.toString();
    }
    
    
    
}
