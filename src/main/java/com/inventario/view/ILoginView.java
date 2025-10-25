package com.inventario.view;

/**
 * Interfaz para las vistas de login
 * Permite al controlador trabajar con diferentes implementaciones
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public interface ILoginView {
    String getUsuario();
    String getPassword();
    boolean isAdminSelected();
    boolean isRecordarUsuario();
    void limpiarCampos();
    void mostrarError(String mensaje);
    void mostrarMensaje(String mensaje);
    void dispose();
}
