package com.inventario.main;

import com.inventario.view.LoginFrame;
import javax.swing.*;

/**
 * Clase principal de la aplicación de inventario
 * Punto de entrada del sistema
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class InventarioApp {
    
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al configurar Look and Feel: " + e.getMessage());
        }
        
        // Configurar propiedades del sistema
        System.setProperty("swing.aatext", "true");
        System.setProperty("awt.useSystemAAFontSettings", "on");
        
        // Ejecutar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Crear y mostrar la ventana de login
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                
                // Centrar la ventana
                loginFrame.setLocationRelativeTo(null);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error al iniciar la aplicación: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}

