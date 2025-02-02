/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro Carvajal
 */
public class LogGenerator {
    private static LogGenerator instance;
    private Path path;

    private LogGenerator() {}

    // Singleton para asegurar una única instancia
    public static synchronized LogGenerator getInstance() {
        if (instance == null) {
            instance = new LogGenerator();
        }
        return instance;
    }
    
    public String getFormattedDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSSS");
        return now.format(formatter);
    }
    
    public void log(String clientIP, String operation, String resource) {
        String date = getFormattedDate();
        path = Paths.get("logs/log_" + date + "_" + operation + ".txt");
        
        try {
            FileWriter fileWriter = new FileWriter(path.toString());
            StringBuilder sb = new StringBuilder();
            sb.append(date);
            sb.append(", ").append(clientIP);
            sb.append(", ").append(operation);
            sb.append(", ").append(resource);
            
            fileWriter.write(sb.toString());
            fileWriter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(LogGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generateCSVReport() {
        Path reportPath = Paths.get("logs/audit_logs.csv");
        Path logDir = Paths.get("logs");
        
        try {
            BufferedWriter writer = Files.newBufferedWriter(reportPath);

            // Escribir encabezado
            writer.write("Timestamp,ClientIP,Operation,Resource");
            writer.newLine();

            // Obtener los logs
            List<Path> logFiles = Files.list(logDir).toList();

            // Procesar cada archivo de log
            for (Path logFile : logFiles) {
                List<String> lines = Files.readAllLines(logFile);
                if (!lines.isEmpty()) {
                    writer.write(lines.get(0)); // Escribir primera línea
                    writer.newLine();
                }
            }

            writer.close();
            
        } catch (NoSuchFileException e) {
            System.err.println("Directorio de logs no encontrado");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        LogGenerator lg = LogGenerator.getInstance();
        lg.generateCSVReport();
    }
}
