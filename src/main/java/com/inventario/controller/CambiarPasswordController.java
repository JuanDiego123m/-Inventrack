package com.inventario.controller;

import com.inventario.model.Usuario;
import com.inventario.service.UsuarioService;
import com.inventario.view.CambiarPasswordFrame;

/**
 * Controlador para el cambio de contraseña
 * Gestiona la lógica de validación y actualización de contraseñas
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class CambiarPasswordController {
    
    private CambiarPasswordFrame view;
    private UsuarioService usuarioService;
    private Usuario usuarioActual;

    public CambiarPasswordController(CambiarPasswordFrame view, Usuario usuarioActual) {
        this.view = view;
        this.usuarioService = new UsuarioService();
        this.usuarioActual = usuarioActual;
    }

    /**
     * Cambia la contraseña del usuario actual
     */
    public void cambiarPassword() {
        try {
            // Obtener datos del formulario
            String passwordActual = view.getPasswordActual();
            String passwordNueva = view.getPasswordNueva();
            String confirmarPassword = view.getConfirmarPassword();

            // Validar campos obligatorios
            if (!validarCamposObligatorios(passwordActual, passwordNueva, confirmarPassword)) {
                return;
            }

            // Validar que las contraseñas nuevas coincidan
            if (!passwordNueva.equals(confirmarPassword)) {
                view.mostrarError("Las contraseñas nuevas no coinciden.");
                return;
            }

            // Validar longitud de la nueva contraseña
            if (passwordNueva.length() < 6) {
                view.mostrarError("La nueva contraseña debe tener al menos 6 caracteres.");
                return;
            }

            // Validar que la nueva contraseña sea diferente a la actual
            if (passwordActual.equals(passwordNueva)) {
                view.mostrarError("La nueva contraseña debe ser diferente a la actual.");
                return;
            }

            // Intentar cambiar la contraseña
            boolean exito = usuarioService.cambiarPassword(usuarioActual, passwordActual, passwordNueva);

            if (exito) {
                view.mostrarMensaje("Contraseña cambiada exitosamente.\n" +
                    "Por favor, recuerde su nueva contraseña.");
                view.limpiarFormulario();
                view.cerrar();
            } else {
                view.mostrarError("La contraseña actual es incorrecta.\n" +
                    "Por favor, verifique e intente nuevamente.");
            }

        } catch (Exception e) {
            view.mostrarError("Error al cambiar contraseña: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Valida que todos los campos obligatorios estén completos
     */
    private boolean validarCamposObligatorios(String passwordActual, String passwordNueva, String confirmarPassword) {
        if (passwordActual.isEmpty()) {
            view.mostrarError("Debe ingresar su contraseña actual.");
            return false;
        }

        if (passwordNueva.isEmpty()) {
            view.mostrarError("Debe ingresar una nueva contraseña.");
            return false;
        }

        if (confirmarPassword.isEmpty()) {
            view.mostrarError("Debe confirmar la nueva contraseña.");
            return false;
        }

        return true;
    }
}

