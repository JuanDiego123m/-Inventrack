package com.inventario.controller;

import com.inventario.model.Usuario;
import com.inventario.model.Rol;
import com.inventario.service.UsuarioService;
import com.inventario.view.RegistroUsuarioFrame;
import com.inventario.util.Validador;

/**
 * Controlador para el registro de usuarios
 * Implementa la lógica de validación y registro de nuevos usuarios
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class RegistroUsuarioController {
    
    private RegistroUsuarioFrame view;
    private UsuarioService usuarioService;
    private Usuario usuarioActual;

    public RegistroUsuarioController(RegistroUsuarioFrame view) {
        this.view = view;
        this.usuarioService = new UsuarioService();
    }

    public RegistroUsuarioController(RegistroUsuarioFrame view, Usuario usuarioActual) {
        this.view = view;
        this.usuarioService = new UsuarioService();
        this.usuarioActual = usuarioActual;
    }

    /**
     * Registra un nuevo usuario en el sistema
     */
    public void registrarUsuario() {
        try {
            // Verificar permisos: solo SUPER_ADMIN puede crear usuarios
            if (usuarioActual == null || !usuarioActual.esSuperAdministrador()) {
                view.mostrarError("No tiene permisos para crear usuarios.\n" +
                    "Solo el Super Administrador puede registrar nuevos usuarios.");
                return;
            }

            // Obtener datos del formulario
            String username = view.getUsername();
            String password = view.getPassword();
            String confirmPassword = view.getConfirmPassword();
            String nombre = view.getNombre();
            String email = view.getEmail();
            boolean esAdmin = view.isAdminSelected();

            // Validar campos obligatorios
            if (!validarCamposObligatorios(username, password, confirmPassword, nombre, email)) {
                return;
            }

            // Validar formato de datos
            if (!validarFormatoDatos(username, password, nombre, email)) {
                return;
            }

            // Validar que las contraseñas coincidan
            if (!password.equals(confirmPassword)) {
                view.mostrarError("Las contraseñas no coinciden.");
                return;
            }

            // Verificar que el usuario no exista
            if (usuarioService.buscarUsuario(username) != null) {
                view.mostrarError("El nombre de usuario ya existe. Por favor, elija otro.");
                return;
            }

            // Determinar el rol del nuevo usuario
            Rol rolNuevoUsuario = esAdmin ? Rol.ADMIN : Rol.VENDEDOR;
            
            // Verificar que el usuario actual puede crear este tipo de usuario
            if (!usuarioActual.puedeCrearUsuario(rolNuevoUsuario)) {
                view.mostrarError("No tiene permisos para crear usuarios con rol: " + rolNuevoUsuario.getDescripcion());
                return;
            }

            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setRol(rolNuevoUsuario);
            nuevoUsuario.setActivo(true);

            // Guardar usuario con verificación de permisos
            if (usuarioService.crearUsuario(nuevoUsuario, usuarioActual)) {
                view.mostrarMensaje("Usuario registrado exitosamente.\n" +
                    "Usuario: " + username + "\n" +
                    "Rol: " + rolNuevoUsuario.getDescripcion());
                view.limpiarFormulario();
            } else {
                view.mostrarError("Error al registrar el usuario. Intente nuevamente.");
            }

        } catch (Exception e) {
            view.mostrarError("Error al registrar usuario: " + e.getMessage());
        }
    }

    /**
     * Valida que todos los campos obligatorios estén completos
     */
    private boolean validarCamposObligatorios(String username, String password, 
                                            String confirmPassword, String nombre, String email) {
        if (username.isEmpty()) {
            view.mostrarError("El nombre de usuario es obligatorio.");
            return false;
        }

        if (password.isEmpty()) {
            view.mostrarError("La contraseña es obligatoria.");
            return false;
        }

        if (confirmPassword.isEmpty()) {
            view.mostrarError("Debe confirmar la contraseña.");
            return false;
        }

        if (nombre.isEmpty()) {
            view.mostrarError("El nombre completo es obligatorio.");
            return false;
        }

        if (email.isEmpty()) {
            view.mostrarError("El email es obligatorio.");
            return false;
        }

        return true;
    }

    /**
     * Valida el formato de los datos ingresados
     */
    private boolean validarFormatoDatos(String username, String password, String nombre, String email) {
        // Validar username (mínimo 3 caracteres, solo letras y números)
        if (username.length() < 3) {
            view.mostrarError("El nombre de usuario debe tener al menos 3 caracteres.");
            return false;
        }

        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            view.mostrarError("El nombre de usuario solo puede contener letras, números y guiones bajos.");
            return false;
        }

        // Validar password (mínimo 6 caracteres)
        if (password.length() < 6) {
            view.mostrarError("La contraseña debe tener al menos 6 caracteres.");
            return false;
        }

        // Validar nombre (mínimo 2 caracteres, solo letras y espacios)
        if (nombre.length() < 2) {
            view.mostrarError("El nombre debe tener al menos 2 caracteres.");
            return false;
        }

        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            view.mostrarError("El nombre solo puede contener letras y espacios.");
            return false;
        }

        // Validar email
        if (!Validador.validarEmail(email)) {
            view.mostrarError("El formato del email no es válido.");
            return false;
        }

        return true;
    }
}
