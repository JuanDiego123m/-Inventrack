package com.inventario.service;

import com.inventario.model.Usuario;
import com.inventario.model.Rol;
import com.inventario.dao.UsuarioDAO;
import java.util.List;

/**
 * Servicio para la gestión de usuarios
 * Implementa la lógica de negocio para autenticación y gestión de usuarios
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Los usuarios iniciales se crean automáticamente en DatabaseManager

    /**
     * Autentica un usuario en el sistema
     * 
     * @param username Nombre de usuario
     * @param password Contraseña
     * @param esAdmin Si se espera que sea administrador
     * @return Usuario autenticado o null si las credenciales son incorrectas
     */
    public Usuario autenticarUsuario(String username, String password, boolean esAdmin) {
        return usuarioDAO.autenticar(username, password, esAdmin);
    }

    /**
     * Busca un usuario por su nombre de usuario
     * 
     * @param username Nombre de usuario
     * @return Usuario encontrado o null
     */
    public Usuario buscarUsuario(String username) {
        return usuarioDAO.obtenerPorUsername(username);
    }

    /**
     * Obtiene todos los usuarios del sistema
     * 
     * @return Lista de usuarios
     */
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioDAO.obtenerTodos();
    }

    /**
     * Crea un nuevo usuario en el sistema
     * Solo el SUPER_ADMIN puede crear usuarios
     * 
     * @param usuario Usuario a crear
     * @param usuarioCreador Usuario que está creando el nuevo usuario
     * @return true si se creó exitosamente
     */
    public boolean crearUsuario(Usuario usuario, Usuario usuarioCreador) {
        // Verificar permisos: solo SUPER_ADMIN puede crear usuarios
        if (usuarioCreador == null || !usuarioCreador.esSuperAdministrador()) {
            return false;
        }
        
        // Verificar que no exista un usuario con el mismo username
        if (usuarioDAO.existeUsername(usuario.getUsername())) {
            return false;
        }
        
        return usuarioDAO.crear(usuario);
    }

    /**
     * Crea un nuevo usuario en el sistema (método sin verificación de permisos para compatibilidad)
     * 
     * @param usuario Usuario a crear
     * @return true si se creó exitosamente
     */
    public boolean crearUsuario(Usuario usuario) {
        // Verificar que no exista un usuario con el mismo username
        if (usuarioDAO.existeUsername(usuario.getUsername())) {
            return false;
        }
        
        return usuarioDAO.crear(usuario);
    }

    /**
     * Actualiza un usuario existente
     * 
     * @param usuario Usuario actualizado
     * @return true si se actualizó exitosamente
     */
    public boolean actualizarUsuario(Usuario usuario) {
        return usuarioDAO.actualizar(usuario);
    }

    /**
     * Desactiva un usuario (eliminación lógica)
     * 
     * @param id ID del usuario
     * @return true si se desactivó exitosamente
     */
    public boolean desactivarUsuario(int id) {
        return usuarioDAO.eliminar(id);
    }

    /**
     * Valida las credenciales de un usuario
     * 
     * @param username Nombre de usuario
     * @param password Contraseña
     * @return true si las credenciales son válidas
     */
    public boolean validarCredenciales(String username, String password) {
        Usuario usuario = buscarUsuario(username);
        return usuario != null && 
               usuario.getPassword().equals(password) && 
               usuario.isActivo();
    }

    /**
     * Cambia la contraseña de un usuario
     * 
     * @param username Nombre de usuario
     * @param passwordActual Contraseña actual
     * @param passwordNueva Nueva contraseña
     * @return true si se cambió exitosamente
     */
    public boolean cambiarPassword(String username, String passwordActual, String passwordNueva) {
        Usuario usuario = buscarUsuario(username);
        if (usuario != null && usuario.getPassword().equals(passwordActual)) {
            usuario.setPassword(passwordNueva);
            return true;
        }
        return false;
    }
}

