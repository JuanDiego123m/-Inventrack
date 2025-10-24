package com.inventario.model;

/**
 * Modelo de datos para representar un usuario del sistema
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class Usuario {
    private int id;
    private String username;
    private String password;
    private String nombre;
    private String email;
    private Rol rol;
    private boolean activo;

    // Constructores
    public Usuario() {
        this.activo = true;
    }

    public Usuario(String username, String password, String nombre, String email, Rol rol) {
        this();
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Verifica si el usuario es super administrador
     */
    public boolean esSuperAdministrador() {
        return rol == Rol.SUPER_ADMIN;
    }

    /**
     * Verifica si el usuario es administrador
     */
    public boolean esAdministrador() {
        return rol == Rol.ADMIN;
    }

    /**
     * Verifica si el usuario es vendedor
     */
    public boolean esVendedor() {
        return rol == Rol.VENDEDOR;
    }

    /**
     * Verifica si el usuario puede realizar una acción específica
     */
    public boolean puedeRealizarAccion(String accion) {
        return rol != null && rol.puedeRealizarAccion(accion);
    }

    /**
     * Verifica si el usuario puede crear usuarios de un tipo específico
     */
    public boolean puedeCrearUsuario(Rol rolDestino) {
        return rol != null && rol.puedeCrearUsuario(rolDestino);
    }

    @Override
    public String toString() {
        return String.format("Usuario{id=%d, username='%s', nombre='%s', rol='%s'}", 
                           id, username, nombre, rol);
    }
}

