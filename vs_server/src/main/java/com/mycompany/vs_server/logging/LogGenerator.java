/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vs_server.logging;

/**
 *
 * @author Alejandro Carvajal
 */
public class LogGenerator {
    private static LogGenerator instance;

    private LogGenerator() {}

    // Singleton para asegurar una única instancia
    public static synchronized LogGenerator getInstance() {
        if (instance == null) {
            instance = new LogGenerator();
        }
        return instance;
    }
}
