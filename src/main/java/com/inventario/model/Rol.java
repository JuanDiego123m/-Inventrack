package com.inventario.model;

/**
 * Enum para definir los roles del sistema
 * Implementa jerarquía de permisos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public enum Rol {
    SUPER_ADMIN("SUPER_ADMIN", "Super Administrador", 100),
    ADMIN("ADMIN", "Administrador", 50),
    VENDEDOR("VENDEDOR", "Vendedor", 10),
    CONSULTA("CONSULTA", "Solo Consulta", 1);

    private final String codigo;
    private final String descripcion;
    private final int nivelPermiso;

    Rol(String codigo, String descripcion, int nivelPermiso) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nivelPermiso = nivelPermiso;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getNivelPermiso() {
        return nivelPermiso;
    }

    /**
     * Verifica si este rol tiene permisos para realizar una acción
     * 
     * @param accion Acción a verificar
     * @return true si tiene permisos
     */
    public boolean puedeRealizarAccion(String accion) {
        switch (accion) {
            case "CREAR_USUARIOS":
                return this == SUPER_ADMIN;
            case "CREAR_ADMIN":
                return this == SUPER_ADMIN;
            case "GESTIONAR_PRODUCTOS":
                return this == SUPER_ADMIN || this == ADMIN;
            case "PROCESAR_VENTAS":
                return this == SUPER_ADMIN || this == ADMIN || this == VENDEDOR;
            case "GENERAR_REPORTES":
                return this == SUPER_ADMIN || this == ADMIN;
            case "CONSULTAR_DATOS":
                return true; // Todos pueden consultar
            default:
                return false;
        }
    }

    /**
     * Verifica si este rol puede crear usuarios de un tipo específico
     * 
     * @param rolDestino Rol del usuario a crear
     * @return true si puede crear ese tipo de usuario
     */
    public boolean puedeCrearUsuario(Rol rolDestino) {
        // Solo SUPER_ADMIN puede crear cualquier tipo de usuario
        if (this == SUPER_ADMIN) {
            return true;
        }
        
        // Los demás roles no pueden crear usuarios
        return false;
    }

    /**
     * Obtiene el rol por su código
     * 
     * @param codigo Código del rol
     * @return Rol correspondiente o null si no existe
     */
    public static Rol obtenerPorCodigo(String codigo) {
        for (Rol rol : values()) {
            if (rol.getCodigo().equals(codigo)) {
                return rol;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
