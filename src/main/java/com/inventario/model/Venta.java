package com.inventario.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos para representar una venta
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class Venta {
    private int id;
    private Usuario usuario;
    private List<ItemVenta> items;
    private BigDecimal total;
    private LocalDateTime fechaVenta;

    // Constructores
    public Venta() {
        this.items = new ArrayList<>();
        this.total = BigDecimal.ZERO;
        this.fechaVenta = LocalDateTime.now();
    }

    public Venta(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemVenta> getItems() {
        return items;
    }

    public void setItems(List<ItemVenta> items) {
        this.items = items;
        calcularTotal();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * Agrega un item a la venta
     */
    public void agregarItem(ItemVenta item) {
        this.items.add(item);
        calcularTotal();
    }

    /**
     * Elimina un item de la venta
     */
    public void eliminarItem(ItemVenta item) {
        this.items.remove(item);
        calcularTotal();
    }

    /**
     * Calcula el total de la venta
     */
    private void calcularTotal() {
        this.total = items.stream()
                .map(ItemVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Obtiene la cantidad total de items
     */
    public int getCantidadItems() {
        return items.size();
    }

    /**
     * Verifica si la venta tiene items
     */
    public boolean tieneItems() {
        return !items.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Venta{id=%d, total=%s, items=%d, fecha=%s}", 
                           id, total, items.size(), fechaVenta);
    }
}
