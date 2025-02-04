/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_client.networkLayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Alejandro Carvajal
 */
public class TCPClient {
    private final String serverAddress;
    private final int port;
    private SSLSocket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public TCPClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }
    
    public void connect() throws IOException {
        SSLSocketFactory socketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        clientSocket = (SSLSocket)socketFactory.createSocket(serverAddress, port);
        
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());
    }
    
    public void closeConnection(){
        try{
            if(inputStream != null)
                inputStream.close();
            
            if(outputStream != null)
                outputStream.close();
            
            if(clientSocket != null)
                clientSocket.close();
            
        }catch(IOException ex){
            System.out.println("Error closing connection: "+ex.getMessage());
        }
        
    }
    
    public String sendRequest(String command, String id, String name, String description, String price, String stock) {
        String response = "ERROR";
        StringBuilder sb = new StringBuilder();
        sb.append(command);
        if (id != null)
            sb.append(":").append(id);
        if (name != null)
            sb.append(":").append(name);
        if (description != null)
            sb.append(":").append(description);
        if (price != null)
            sb.append(":").append(price);
        if (stock != null)
            sb.append(":").append(stock);
            
        try {
            outputStream.writeUTF(sb.toString());
            response = inputStream.readUTF();
            
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }

    public String receiveFile(String fileName) {
        try {
            // Leer el tamaño del archivo
            long fileSize = inputStream.readLong();
            System.out.println("Recibiendo archivo de tamaño: " + fileSize + " bytes");

            // Crear para guardar el archivo
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);

            // Leer los bytes del archivo y escribirlos en el archivo local
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytesRead = 0;

            while (totalBytesRead < fileSize && (bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }

            fileOutputStream.close();
            return "Archivo recibido y guardado como " + fileName;

        } catch (IOException e) {
            return "Error al recibir el archivo: " + e.getMessage();
        }
    }

}
