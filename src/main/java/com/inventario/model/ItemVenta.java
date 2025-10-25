package com.inventario.model;

import java.math.BigDecimal;

/**
 * Modelo de datos para representar un item de venta
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class ItemVenta {
    private int id;
    private Venta venta;
    private Producto producto;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Constructores
    public ItemVenta() {
    }

    public ItemVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        calcularSubtotal();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (producto != null) {
            this.precioUnitario = producto.getPrecio();
            calcularSubtotal();
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Calcula el subtotal del item
     */
    private void calcularSubtotal() {
        if (precioUnitario != null && cantidad > 0) {
            this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }

    /**
     * Verifica si hay stock suficiente
     */
    public boolean hayStockSuficiente() {
        return producto != null && producto.getCantidad() >= cantidad;
    }

    @Override
    public String toString() {
        return String.format("ItemVenta{producto='%s', cantidad=%d, subtotal=%s}", 
                           producto != null ? producto.getNombre() : "null", 
                           cantidad, 
                           subtotal);
    }
}
