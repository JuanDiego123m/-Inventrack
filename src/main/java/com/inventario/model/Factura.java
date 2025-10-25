package com.inventario.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Modelo de datos para representar una factura
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class Factura {
    private int id;
    private Venta venta;
    private String numeroFactura;
    private String clienteNombre;
    private String clienteDocumento;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private String observaciones;
    private boolean generada;

    // Constructores
    public Factura() {
        this.fechaEmision = LocalDateTime.now();
        this.generada = false;
    }

    public Factura(Venta venta) {
        this();
        this.venta = venta;
        this.subtotal = venta.getTotal();
        calcularIvaYTotal();
        generarNumeroFactura();
    }

    /**
     * Genera un número de factura único
     */
    private void generarNumeroFactura() {
        // Formato: FACT-YYYYMMDD-NNNN
        String fecha = LocalDateTime.now().toString().substring(0, 10).replace("-", "");
        int numero = id > 0 ? id : (int) (Math.random() * 9999) + 1;
        this.numeroFactura = String.format("FACT-%s-%04d", fecha, numero);
    }

    /**
     * Calcula el IVA (19%) y el total
     */
    private void calcularIvaYTotal() {
        if (subtotal != null) {
            // IVA del 19%
            this.iva = subtotal.multiply(BigDecimal.valueOf(0.19));
            this.total = subtotal.add(iva);
        } else {
            this.iva = BigDecimal.ZERO;
            this.total = BigDecimal.ZERO;
        }
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        generarNumeroFactura();
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
        if (venta != null) {
            this.subtotal = venta.getTotal();
            calcularIvaYTotal();
        }
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteDocumento() {
        return clienteDocumento;
    }

    public void setClienteDocumento(String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        calcularIvaYTotal();
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isGenerada() {
        return generada;
    }

    public void setGenerada(boolean generada) {
        this.generada = generada;
    }

    /**
     * Recalcula IVA y total
     */
    public void recalcular() {
        calcularIvaYTotal();
    }

    @Override
    public String toString() {
        return String.format("Factura{numero='%s', total=%s, fecha=%s}", 
                           numeroFactura, total, fechaEmision);
    }
}
