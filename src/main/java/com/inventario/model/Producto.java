package com.inventario.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo de datos para representar un producto en el inventario
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class Producto {
    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int cantidad;
    private String categoria;
    private LocalDateTime fechaRegistro;
    private boolean activo;

    // Constructores
    public Producto() {
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }

    public Producto(String nombre, String descripcion, BigDecimal precio, int cantidad, String categoria) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Calcula el valor total del producto (precio * cantidad)
     */
    public BigDecimal getValorTotal() {
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }

    /**
     * Verifica si hay stock disponible
     */
    public boolean tieneStock() {
        return cantidad > 0;
    }

    /**
     * Reduce la cantidad del producto
     */
    public boolean reducirCantidad(int cantidadVendida) {
        if (cantidadVendida <= 0 || cantidadVendida > cantidad) {
            return false;
        }
        this.cantidad -= cantidadVendida;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Producto{id=%d, nombre='%s', precio=%s, cantidad=%d}", 
                           id, nombre, precio, cantidad);
    }
}

