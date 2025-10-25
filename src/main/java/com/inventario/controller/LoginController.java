package com.inventario.controller;

import com.inventario.model.Usuario;
import com.inventario.service.UsuarioService;
import com.inventario.view.ILoginView;
import com.inventario.view.MainFrame;
import javax.swing.SwingUtilities;

/**
 * Controlador para la funcionalidad de login
 * Implementa el patrón MVC para separar la lógica de la vista
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class LoginController {
    
    private ILoginView loginFrame;
    private UsuarioService usuarioService;
    private Usuario usuarioActual;

    public LoginController(ILoginView loginFrame) {
        this.loginFrame = loginFrame;
        this.usuarioService = new UsuarioService();
    }

    /**
     * Valida las credenciales del usuario y realiza el login
     */
    public void validarLogin() {
        String username = loginFrame.getUsuario();
        String password = loginFrame.getPassword();
        boolean esAdmin = loginFrame.isAdminSelected();

        // Validaciones básicas
        if (username.isEmpty() || password.isEmpty()) {
            loginFrame.mostrarError("Por favor, complete todos los campos.");
            return;
        }

        try {
            // Buscar usuario en el sistema
            usuarioActual = usuarioService.autenticarUsuario(username, password, esAdmin);
            
            if (usuarioActual != null) {
                // Login exitoso
                loginFrame.mostrarMensaje("¡Bienvenido " + usuarioActual.getNombre() + "!");
                
                // Abrir ventana principal
                abrirVentanaPrincipal();
                
                // Cerrar ventana de login
                loginFrame.dispose();
                
            } else {
                // Credenciales incorrectas
                loginFrame.mostrarError("Usuario o contraseña incorrectos.\n\n" +
                    "Usuarios de prueba:\n" +
                    "• superadmin / superadmin123 (Super Admin)\n" +
                    "• admin / admin123 (Admin)\n" +
                    "• vendedor / vendedor123 (Vendedor)");
                loginFrame.limpiarCampos();
            }
            
        } catch (Exception e) {
            loginFrame.mostrarError("Error al validar credenciales: " + e.getMessage());
            e.printStackTrace(); // Para debugging
        }
    }

    /**
     * Abre la ventana principal del sistema
     */
    private void abrirVentanaPrincipal() {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(usuarioActual);
            mainFrame.setVisible(true);
        });
    }

    /**
     * Obtiene el usuario actual autenticado
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}

