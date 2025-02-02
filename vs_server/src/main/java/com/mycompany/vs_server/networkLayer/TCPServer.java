/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.networkLayer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.*;

/**
 *
 * @author Alejandro Carvajal
 */
public class TCPServer {
    private final int port;

    public TCPServer(int port) {
        this.port = port;
    }
    
    public void start(){
        ExecutorService pool = Executors.newCachedThreadPool();
        
        try {
            SSLServerSocketFactory socketFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket)socketFactory.createServerSocket(port);
            System.out.println("Server listing on port: "+port);
            
            while(true){
                //Accept connection 
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
