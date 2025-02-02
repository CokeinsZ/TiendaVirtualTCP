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
import java.net.SocketException;
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
        try {
            switch (command[0].toUpperCase()) {
                case "HI":
                    return "SUCCES:Hola " + clientIP;
                    
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
