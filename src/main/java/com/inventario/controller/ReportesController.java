package com.inventario.controller;

import com.inventario.model.Usuario;
import com.inventario.model.Venta;
import com.inventario.model.Producto;
import com.inventario.service.VentaService;
import com.inventario.service.ProductoService;
import com.inventario.service.ReportesService;
import com.inventario.view.ReportesFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de reportes
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class ReportesController {
    
    private ReportesFrame view;
    private ReportesService reportesService;
    private VentaService ventaService;
    private ProductoService productoService;
    private Usuario usuarioActual;
    private NumberFormat formatoMoneda;
    private DateTimeFormatter formatoFecha;

    public ReportesController(ReportesFrame view, Usuario usuarioActual) {
        this.view = view;
        this.usuarioActual = usuarioActual;
        this.reportesService = new ReportesService();
        this.ventaService = new VentaService();
        this.productoService = new ProductoService();
        this.formatoMoneda = NumberFormat.getCurrencyInstance();
        this.formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    }

    /**
     * Carga las estadísticas generales
     */
    public void cargarEstadisticasGenerales() {
        try {
            Map<String, Object> stats = reportesService.obtenerEstadisticasGenerales();
            
            // Actualizar labels
            actualizarStatCard(view.getLblTotalVentas(), "📊 Total Ventas", 
                stats.get("totalVentas").toString());
            
            actualizarStatCard(view.getLblVentasHoy(), "📅 Ventas Hoy", 
                stats.get("ventasHoy").toString());
            
            actualizarStatCard(view.getLblProductosStock(), "📦 Productos en Stock", 
                stats.get("productosStock").toString());
            
            actualizarStatCard(view.getLblProductosBajoStock(), "⚠️ Productos Bajo Stock", 
                stats.get("productosBajoStock").toString());
            
            BigDecimal ingresoTotal = (BigDecimal) stats.get("ingresoTotal");
            actualizarStatCard(view.getLblIngresoTotal(), "💰 Ingreso Total", 
                formatoMoneda.format(ingresoTotal));
            
            BigDecimal promedioVenta = (BigDecimal) stats.get("promedioVenta");
            actualizarStatCard(view.getLblPromedioVenta(), "📈 Promedio por Venta", 
                formatoMoneda.format(promedioVenta));
            
        } catch (Exception e) {
            view.mostrarError("Error al cargar estadísticas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Actualiza un card de estadística
     */
    private void actualizarStatCard(JLabel label, String titulo, String valor) {
        Color color = extraerColorDeLabel(label);
        label.setText(String.format("<html><div style='text-align: center;'>" +
            "<div style='font-size: 14px; color: #7f8c8d;'>%s</div>" +
            "<div style='font-size: 32px; font-weight: bold; color: rgb(%d,%d,%d); margin-top: 10px;'>%s</div>" +
            "</div></html>", titulo, color.getRed(), color.getGreen(), color.getBlue(), valor));
    }

    /**
     * Extrae el color de un label HTML
     */
    private Color extraerColorDeLabel(JLabel label) {
        // Color por defecto
        return new Color(41, 128, 185);
    }

    /**
     * Carga el reporte de ventas
     */
    public void cargarReporteVentas() {
        try {
            List<Venta> ventas = ventaService.obtenerTodasVentas();
            DefaultTableModel modelo = view.getModeloVentas();
            modelo.setRowCount(0);
            
            for (Venta venta : ventas) {
                Object[] fila = {
                    venta.getId(),
                    venta.getFechaVenta().format(formatoFecha),
                    venta.getUsuario().getNombre(),
                    venta.getCantidadItems(),
                    formatoMoneda.format(venta.getTotal())
                };
                modelo.addRow(fila);
            }
            
            view.mostrarMensaje("Reporte de ventas actualizado");
            
        } catch (Exception e) {
            view.mostrarError("Error al cargar reporte de ventas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga el reporte de productos
     */
    public void cargarReporteProductos() {
        try {
            List<Producto> productos = productoService.obtenerTodosProductos();
            DefaultTableModel modelo = view.getModeloProductos();
            modelo.setRowCount(0);
            
            for (Producto producto : productos) {
                Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getCantidad(),
                    formatoMoneda.format(producto.getPrecio()),
                    formatoMoneda.format(producto.getValorTotal())
                };
                modelo.addRow(fila);
            }
            
            view.mostrarMensaje("Reporte de productos actualizado");
            
        } catch (Exception e) {
            view.mostrarError("Error al cargar reporte de productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga el reporte de inventario
     */
    public void cargarReporteInventario() {
        try {
            List<Producto> productos = productoService.obtenerTodosProductos();
            DefaultTableModel modelo = view.getModeloInventario();
            modelo.setRowCount(0);
            
            int productosOk = 0;
            int totalProductos = productos.size();
            
            for (Producto producto : productos) {
                String estado;
                String accion;
                
                if (producto.getCantidad() == 0) {
                    estado = "❌ Sin Stock";
                    accion = "Reponer Urgente";
                } else if (producto.getCantidad() < 5) {
                    estado = "⚠️ Stock Bajo";
                    accion = "Reponer Pronto";
                } else if (producto.getCantidad() < 10) {
                    estado = "⚡ Stock Medio";
                    accion = "Revisar";
                } else {
                    estado = "✅ Stock OK";
                    accion = "Normal";
                    productosOk++;
                }
                
                Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad(),
                    estado,
                    accion
                };
                modelo.addRow(fila);
            }
            
            // Actualizar barra de progreso
            int porcentaje = totalProductos > 0 ? (productosOk * 100) / totalProductos : 0;
            JProgressBar progress = view.getProgressStock();
            progress.setValue(porcentaje);
            progress.setString(String.format("%d%% - %d de %d productos con stock adecuado", 
                porcentaje, productosOk, totalProductos));
            
            // Cambiar color según porcentaje
            if (porcentaje >= 70) {
                progress.setForeground(new Color(46, 204, 113)); // Verde
            } else if (porcentaje >= 40) {
                progress.setForeground(new Color(241, 196, 15)); // Amarillo
            } else {
                progress.setForeground(new Color(231, 76, 60)); // Rojo
            }
            
            view.mostrarMensaje("Reporte de inventario actualizado");
            
        } catch (Exception e) {
            view.mostrarError("Error al cargar reporte de inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga el reporte de productos más vendidos
     */
    public void cargarTopProductos() {
        try {
            Map<String, Integer> topProductos = reportesService.obtenerProductosMasVendidos(10);
            DefaultTableModel modelo = view.getModeloTopProductos();
            modelo.setRowCount(0);
            
            int posicion = 1;
            for (Map.Entry<String, Integer> entry : topProductos.entrySet()) {
                String productoCodigo = entry.getKey();
                Integer unidadesVendidas = entry.getValue();
                
                // Buscar información del producto
                Producto producto = productoService.obtenerProductoPorCodigo(productoCodigo);
                if (producto != null) {
                    BigDecimal ingresos = producto.getPrecio()
                        .multiply(BigDecimal.valueOf(unidadesVendidas));
                    
                    String medalla = "";
                    if (posicion == 1) medalla = "🥇";
                    else if (posicion == 2) medalla = "🥈";
                    else if (posicion == 3) medalla = "🥉";
                    else medalla = String.valueOf(posicion);
                    
                    Object[] fila = {
                        medalla,
                        producto.getCodigo(),
                        producto.getNombre(),
                        unidadesVendidas,
                        formatoMoneda.format(ingresos)
                    };
                    modelo.addRow(fila);
                    posicion++;
                }
            }
            
        } catch (Exception e) {
            view.mostrarError("Error al cargar top productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Exporta las ventas a un archivo
     */
    public void exportarVentas() {
        try {
            String resultado = reportesService.exportarVentasATexto();
            view.mostrarMensaje("Ventas exportadas exitosamente a:\n" + resultado);
        } catch (Exception e) {
            view.mostrarError("Error al exportar ventas: " + e.getMessage());
        }
    }

    /**
     * Exporta los productos a un archivo
     */
    public void exportarProductos() {
        try {
            String resultado = reportesService.exportarProductosATexto();
            view.mostrarMensaje("Productos exportados exitosamente a:\n" + resultado);
        } catch (Exception e) {
            view.mostrarError("Error al exportar productos: " + e.getMessage());
        }
    }

    /**
     * Maneja el cambio de pestaña
     */
    public void onTabChanged(int selectedIndex) {
        try {
            switch (selectedIndex) {
                case 0: // Dashboard
                    cargarEstadisticasGenerales();
                    break;
                case 1: // Ventas
                    cargarReporteVentas();
                    break;
                case 2: // Productos
                    cargarReporteProductos();
                    break;
                case 3: // Inventario
                    cargarReporteInventario();
                    break;
                case 4: // Top Ventas
                    cargarTopProductos();
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error al cambiar de pestaña: " + e.getMessage());
        }
    }
}
