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
     * Genera el nombre de archivo para la factura
     */
    public String generarNombreArchivo(Factura factura) {
        return String.format("factura_%s_%s.txt", 
            factura.getNumeroFactura().replace("-", "_"),
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));
    }
    
    /**
     * Guarda la factura en un archivo (en el directorio actual)
     */
    public String guardarFacturaArchivo(Factura factura, boolean incluirIVA) throws IOException {
        String nombreArchivo = generarNombreArchivo(factura);
        
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            String contenido = generarFacturaTexto(factura, incluirIVA);
            writer.write(contenido);
        }
        
        return nombreArchivo;
    }
    
    /**
     * Guarda la factura en una ruta específica
     */
    public String guardarFacturaArchivo(Factura factura, boolean incluirIVA, String rutaCompleta) throws IOException {
        try (FileWriter writer = new FileWriter(rutaCompleta)) {
            String contenido = generarFacturaTexto(factura, incluirIVA);
            writer.write(contenido);
        }
        
        return rutaCompleta;
    }
    
    /**
     * Genera la factura en formato HTML para mejor impresión
     */
    public String generarFacturaHTML(Factura factura, boolean incluirIVA) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n<head>\n");
        sb.append("<meta charset='UTF-8'>\n");
        sb.append("<title>Factura ").append(factura.getNumeroFactura()).append("</title>\n");
        sb.append("<style>\n");
        sb.append("body { font-family: 'Courier New', monospace; margin: 20px; background: white; }\n");
        sb.append(".factura { max-width: 800px; margin: 0 auto; padding: 20px; }\n");
        sb.append(".header { text-align: center; border-bottom: 3px double #000; padding-bottom: 10px; margin-bottom: 15px; }\n");
        sb.append(".header h1 { margin: 0; font-size: 24px; }\n");
        sb.append(".info { margin: 10px 0; }\n");
        sb.append(".info-label { display: inline-block; width: 200px; font-weight: bold; }\n");
        sb.append("table { width: 100%; border-collapse: collapse; margin: 15px 0; }\n");
        sb.append("th { background: #333; color: white; padding: 8px; text-align: left; }\n");
        sb.append("td { padding: 6px; border-bottom: 1px solid #ddd; }\n");
        sb.append(".totales { text-align: right; margin-top: 20px; font-size: 14px; }\n");
        sb.append(".total-line { margin: 5px 0; }\n");
        sb.append(".total-final { font-size: 18px; font-weight: bold; border-top: 2px solid #000; padding-top: 10px; }\n");
        sb.append(".footer { text-align: center; margin-top: 30px; border-top: 1px solid #000; padding-top: 10px; font-size: 12px; }\n");
        sb.append("@media print { body { margin: 0; } .no-print { display: none; } }\n");
        sb.append("</style>\n</head>\n<body>\n");
        
        sb.append("<div class='factura'>\n");
        
        // Encabezado
        sb.append("<div class='header'>\n");
        sb.append("<h1>FACTURA DE VENTA</h1>\n");
        sb.append("<p><strong>").append(NOMBRE_EMPRESA).append("</strong></p>\n");
        sb.append("<p>NIT: ").append(NIT_EMPRESA).append("</p>\n");
        sb.append("<p>").append(DIRECCION_EMPRESA).append("</p>\n");
        sb.append("<p>Tel: ").append(TELEFONO_EMPRESA).append(" | Email: ").append(EMAIL_EMPRESA).append("</p>\n");
        sb.append("</div>\n");
        
        // Información de la factura
        sb.append("<div class='info'>\n");
        sb.append("<p><span class='info-label'>Número de Factura:</span>").append(factura.getNumeroFactura()).append("</p>\n");
        sb.append("<p><span class='info-label'>Fecha de Emisión:</span>")
          .append(factura.getFechaEmision().format(formatoFecha)).append("</p>\n");
        sb.append("<p><span class='info-label'>Vendedor:</span>")
          .append(factura.getVenta().getUsuario().getNombre()).append("</p>\n");
        sb.append("<p><span class='info-label'>Cliente:</span>").append(factura.getClienteNombre()).append("</p>\n");
        sb.append("<p><span class='info-label'>Documento:</span>").append(factura.getClienteDocumento()).append("</p>\n");
        sb.append("</div>\n");
        
        // Tabla de productos
        sb.append("<h3>Detalle de la Compra</h3>\n");
        sb.append("<table>\n");
        sb.append("<tr><th>Producto</th><th style='text-align:center'>Cantidad</th><th style='text-align:right'>Precio Unit.</th><th style='text-align:right'>Subtotal</th></tr>\n");
        
        BigDecimal subtotalGeneral = BigDecimal.ZERO;
        
        for (ItemVenta item : factura.getVenta().getItems()) {
            sb.append("<tr>\n");
            sb.append("<td>").append(item.getProducto().getNombre()).append("</td>\n");
            sb.append("<td style='text-align:center'>").append(item.getCantidad()).append("</td>\n");
            sb.append("<td style='text-align:right'>").append(formatoMoneda.format(item.getPrecioUnitario())).append("</td>\n");
            sb.append("<td style='text-align:right'>").append(formatoMoneda.format(item.getSubtotal())).append("</td>\n");
            sb.append("</tr>\n");
            
            subtotalGeneral = subtotalGeneral.add(item.getSubtotal());
        }
        
        sb.append("</table>\n");
        
        // Totales
        sb.append("<div class='totales'>\n");
        sb.append("<div class='total-line'><strong>Subtotal:</strong> ").append(formatoMoneda.format(subtotalGeneral)).append("</div>\n");
        
        if (incluirIVA) {
            BigDecimal iva = subtotalGeneral.multiply(BigDecimal.valueOf(0.19));
            BigDecimal total = subtotalGeneral.add(iva);
            
            sb.append("<div class='total-line'><strong>IVA (19%):</strong> ").append(formatoMoneda.format(iva)).append("</div>\n");
            sb.append("<div class='total-final'><strong>TOTAL A PAGAR:</strong> ").append(formatoMoneda.format(total)).append("</div>\n");
            
            factura.setSubtotal(subtotalGeneral);
            factura.setIva(iva);
            factura.setTotal(total);
        } else {
            sb.append("<div class='total-final'><strong>TOTAL A PAGAR:</strong> ").append(formatoMoneda.format(subtotalGeneral)).append("</div>\n");
            
            factura.setSubtotal(subtotalGeneral);
            factura.setIva(BigDecimal.ZERO);
            factura.setTotal(subtotalGeneral);
        }
        
        sb.append("</div>\n");
        
        // Observaciones
        if (factura.getObservaciones() != null && !factura.getObservaciones().isEmpty()) {
            sb.append("<p><strong>Observaciones:</strong><br>").append(factura.getObservaciones()).append("</p>\n");
        }
        
        // Pie de página
        sb.append("<div class='footer'>\n");
        sb.append("<p><strong>¡GRACIAS POR SU COMPRA!</strong></p>\n");
        sb.append("<p>Esta factura es un documento válido para efectos legales y tributarios</p>\n");
        sb.append("<p style='margin-top:10px; font-size:10px;'>Sistema de Inventario v2.0 - 2025</p>\n");
        sb.append("</div>\n");
        
        sb.append("</div>\n</body>\n</html>");
        
        return sb.toString();
    }
    
    /**
     * Guarda la factura en formato HTML
     */
    public String guardarFacturaHTML(Factura factura, boolean incluirIVA, String rutaCompleta) throws IOException {
        try (FileWriter writer = new FileWriter(rutaCompleta)) {
            String contenido = generarFacturaHTML(factura, incluirIVA);
            writer.write(contenido);
        }
        
        return rutaCompleta;
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
