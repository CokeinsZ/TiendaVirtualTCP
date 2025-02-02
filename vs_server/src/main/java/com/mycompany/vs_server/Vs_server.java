/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.vs_server;

import com.mycompany.vs_server.inventory.InventoryManager;
import com.mycompany.vs_server.networkLayer.TCPServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro Carvajal
 */
public class Vs_server {

    public static void main(String[] args) {
        /*pruebas de datos reporte string y csv*/
        InventoryManager invMan =   InventoryManager.getInstance();
        invMan.addProduct("1", "aguacate", "descrpcionm 1", "10.000", "100");
        invMan.addProduct("2", "pasaqs", "descrpcionm 2", "20.000", "200");
        invMan.addProduct("3", "arroz C", "descrpcionm 3", "30.000", "300");
        System.out.println(""+ invMan.generateReport());
        invMan.generateCSVReport("reporteProductosCSV.CSV");
        
        
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("properties.properties")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Vs_server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Vs_server.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sslRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
        String sslPassword = p.getProperty("SSL_PASSWORD");
        System.setProperty("javax.net.ssl.keyStore",sslRoute);
        System.setProperty("javax.net.ssl.keyStorePassword",sslPassword);
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.trustStore", sslRoute);
        System.setProperty("javax.net.ssl.trustStorePassword", sslPassword);
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
        
        TCPServer server = new TCPServer(9090);
        server.start();
    }
}
