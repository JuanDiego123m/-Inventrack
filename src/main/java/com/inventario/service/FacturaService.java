package com.inventario.service;

import com.inventario.model.Factura;
import com.inventario.model.ItemVenta;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servicio para generación y gestión de facturas
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class FacturaService {
    
    private static final String NOMBRE_EMPRESA = "SISTEMA DE INVENTARIO S.A.S";
    private static final String NIT_EMPRESA = "900.123.456-7";
    private static final String DIRECCION_EMPRESA = "Calle 123 #45-67, Medellín";
    private static final String TELEFONO_EMPRESA = "(604) 123-4567";
    private static final String EMAIL_EMPRESA = "ventas@inventario.com";
    
    private NumberFormat formatoMoneda;
    private DateTimeFormatter formatoFecha;

    public FacturaService() {
        this.formatoMoneda = NumberFormat.getCurrencyInstance();
        this.formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * Genera el texto completo de la factura
     */
    public String generarFacturaTexto(Factura factura, boolean incluirIVA) {
        StringBuilder sb = new StringBuilder();
        
        // Encabezado
        sb.append("═══════════════════════════════════════════════════════════════════\n");
        sb.append("                         FACTURA DE VENTA\n");
        sb.append("═══════════════════════════════════════════════════════════════════\n\n");
        
        // Información de la empresa
        sb.append(String.format("%-30s %s\n", "Empresa:", NOMBRE_EMPRESA));
        sb.append(String.format("%-30s %s\n", "NIT:", NIT_EMPRESA));
        sb.append(String.format("%-30s %s\n", "Dirección:", DIRECCION_EMPRESA));
        sb.append(String.format("%-30s %s\n", "Teléfono:", TELEFONO_EMPRESA));
        sb.append(String.format("%-30s %s\n", "Email:", EMAIL_EMPRESA));
        sb.append("\n");
        sb.append("───────────────────────────────────────────────────────────────────\n\n");
        
        // Información de la factura
        sb.append(String.format("%-30s %s\n", "Número de Factura:", factura.getNumeroFactura()));
        sb.append(String.format("%-30s %s\n", "Fecha de Emisión:", 
            factura.getFechaEmision().format(formatoFecha)));
        sb.append(String.format("%-30s %s\n", "Vendedor:", 
            factura.getVenta().getUsuario().getNombre()));
        sb.append("\n");
        
        // Información del cliente
        sb.append("DATOS DEL CLIENTE:\n");
        sb.append(String.format("%-30s %s\n", "Nombre:", factura.getClienteNombre()));
        sb.append(String.format("%-30s %s\n", "Documento:", factura.getClienteDocumento()));
        sb.append("\n");
        sb.append("───────────────────────────────────────────────────────────────────\n\n");
        
        // Tabla de items
        sb.append("DETALLE DE LA COMPRA:\n\n");
        sb.append(String.format("%-40s %8s %12s %15s\n", 
            "PRODUCTO", "CANT.", "PRECIO UNIT.", "SUBTOTAL"));
        sb.append("───────────────────────────────────────────────────────────────────\n");
        
        BigDecimal subtotalGeneral = BigDecimal.ZERO;
        
        for (ItemVenta item : factura.getVenta().getItems()) {
            String nombreProducto = item.getProducto().getNombre();
            if (nombreProducto.length() > 40) {
                nombreProducto = nombreProducto.substring(0, 37) + "...";
            }
            
            sb.append(String.format("%-40s %8d %12s %15s\n",
                nombreProducto,
                item.getCantidad(),
                formatoMoneda.format(item.getPrecioUnitario()),
                formatoMoneda.format(item.getSubtotal())
            ));
            
            subtotalGeneral = subtotalGeneral.add(item.getSubtotal());
        }
        
        sb.append("───────────────────────────────────────────────────────────────────\n\n");
        
        // Totales
        sb.append("TOTALES:\n\n");
        sb.append(String.format("%-50s %15s\n", "Subtotal:", 
            formatoMoneda.format(subtotalGeneral)));
        
        if (incluirIVA) {
            BigDecimal iva = subtotalGeneral.multiply(BigDecimal.valueOf(0.19));
            BigDecimal total = subtotalGeneral.add(iva);
            
            sb.append(String.format("%-50s %15s\n", "IVA (19%):", 
                formatoMoneda.format(iva)));
            sb.append("───────────────────────────────────────────────────────────────────\n");
            sb.append(String.format("%-50s %15s\n", "TOTAL A PAGAR:", 
                formatoMoneda.format(total)));
            
            // Actualizar factura
            factura.setSubtotal(subtotalGeneral);
            factura.setIva(iva);
            factura.setTotal(total);
        } else {
            sb.append("───────────────────────────────────────────────────────────────────\n");
            sb.append(String.format("%-50s %15s\n", "TOTAL A PAGAR:", 
                formatoMoneda.format(subtotalGeneral)));
            
            // Actualizar factura
            factura.setSubtotal(subtotalGeneral);
            factura.setIva(BigDecimal.ZERO);
            factura.setTotal(subtotalGeneral);
        }
        
        sb.append("\n");
        sb.append("═══════════════════════════════════════════════════════════════════\n\n");
        
        // Pie de página
        sb.append("                     ¡GRACIAS POR SU COMPRA!\n\n");
        sb.append("        Esta factura es un documento válido para efectos legales\n");
        sb.append("               y tributarios según la legislación vigente.\n\n");
        
        if (factura.getObservaciones() != null && !factura.getObservaciones().isEmpty()) {
            sb.append("OBSERVACIONES:\n");
            sb.append(factura.getObservaciones()).append("\n\n");
        }
        
        sb.append("───────────────────────────────────────────────────────────────────\n");
        sb.append("                Sistema de Inventario v1.0 - 2025\n");
        sb.append("═══════════════════════════════════════════════════════════════════\n");
        
        return sb.toString();
    }

    /**
     * Guarda la factura en un archivo
     */
    public String guardarFacturaArchivo(Factura factura, boolean incluirIVA) throws IOException {
        String nombreArchivo = String.format("factura_%s_%s.txt", 
            factura.getNumeroFactura().replace("-", "_"),
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));
        
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            String contenido = generarFacturaTexto(factura, incluirIVA);
            writer.write(contenido);
        }
        
        return nombreArchivo;
    }

    /**
     * Genera un resumen corto de la factura
     */
    public String generarResumen(Factura factura) {
        return String.format(
            "Factura: %s | Cliente: %s | Total: %s",
            factura.getNumeroFactura(),
            factura.getClienteNombre(),
            formatoMoneda.format(factura.getTotal())
        );
    }

    /**
     * Valida los datos de una factura
     */
    public boolean validarFactura(Factura factura) {
        if (factura == null) {
            return false;
        }
        
        if (factura.getVenta() == null) {
            return false;
        }
        
        if (factura.getClienteNombre() == null || factura.getClienteNombre().isEmpty()) {
            return false;
        }
        
        if (factura.getTotal() == null || factura.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        
        return true;
    }

    /**
     * Calcula el total con descuento
     */
    public BigDecimal calcularTotalConDescuento(Factura factura, BigDecimal porcentajeDescuento) {
        if (factura == null || porcentajeDescuento == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal descuento = factura.getTotal()
            .multiply(porcentajeDescuento)
            .divide(BigDecimal.valueOf(100));
        
        return factura.getTotal().subtract(descuento);
    }
}
