/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.networkLayer;

import com.mycompany.vs_server.inventory.InventoryManager;
import com.mycompany.vs_server.logging.LogGenerator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        DataInputStream inputStream = null;
        try {
            String clientIP = clientSocket.getInetAddress().toString();
            
            inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            
            String clientMessage = inputStream.readUTF();
            System.out.println("Client message: "+clientMessage);
            clientMessage = clientMessage.trim();
            String[] parts = clientMessage.split(":");
            
            String response = processCommand(parts, clientIP);
            outputStream.writeUTF(response);
        
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String processCommand(String[] command, String clientIP) {
        try {
            switch (command[0].toUpperCase()) {
                case "ADD":
                    //Codigo para agregar un producto
                    //Codigo para generar el log
                    return "SUCCESS:Producto añadido";
                    
                case "DELETE":
                    //Codigo para eliminar un producto
                    //Codigo para generar el log
                    return "SUCCESS:Producto eliminado";
                    
                // ... otros comandos (UPDATE, SEARCH, REPORT)
                default:
                    return "ERROR:Comando no reconocido";
            }
        } catch (Exception e) {
            return "ERROR:" + e.getMessage();
        }
    }
    
    
    
}
