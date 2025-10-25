package com.inventario.service;

import com.inventario.model.Venta;
import com.inventario.model.ItemVenta;
import com.inventario.model.Producto;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio para generación de reportes y estadísticas
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class ReportesService {
    
    private VentaService ventaService;
    private ProductoService productoService;

    public ReportesService() {
        this.ventaService = new VentaService();
        this.productoService = new ProductoService();
    }

    /**
     * Obtiene estadísticas generales del sistema
     */
    public Map<String, Object> obtenerEstadisticasGenerales() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Total de ventas
            List<Venta> todasVentas = ventaService.obtenerTodasVentas();
            stats.put("totalVentas", todasVentas.size());
            
            // Ventas de hoy
            List<Venta> ventasHoy = ventaService.obtenerVentasPorFecha(LocalDate.now());
            stats.put("ventasHoy", ventasHoy.size());
            
            // Productos en stock
            List<Producto> todosProductos = productoService.obtenerTodosProductos();
            long productosConStock = todosProductos.stream()
                .filter(p -> p.getCantidad() > 0)
                .count();
            stats.put("productosStock", productosConStock);
            
            // Productos con bajo stock (menos de 5 unidades)
            long productosBajoStock = todosProductos.stream()
                .filter(p -> p.getCantidad() > 0 && p.getCantidad() < 5)
                .count();
            stats.put("productosBajoStock", productosBajoStock);
            
            // Ingreso total
            BigDecimal ingresoTotal = todasVentas.stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            stats.put("ingresoTotal", ingresoTotal);
            
            // Promedio por venta
            BigDecimal promedioVenta = BigDecimal.ZERO;
            if (!todasVentas.isEmpty()) {
                promedioVenta = ingresoTotal.divide(
                    BigDecimal.valueOf(todasVentas.size()), 
                    2, 
                    RoundingMode.HALF_UP
                );
            }
            stats.put("promedioVenta", promedioVenta);
            
        } catch (Exception e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
            e.printStackTrace();
            // Valores por defecto en caso de error
            stats.put("totalVentas", 0);
            stats.put("ventasHoy", 0);
            stats.put("productosStock", 0);
            stats.put("productosBajoStock", 0);
            stats.put("ingresoTotal", BigDecimal.ZERO);
            stats.put("promedioVenta", BigDecimal.ZERO);
        }
        
        return stats;
    }

    /**
     * Obtiene los productos más vendidos
     */
    public Map<String, Integer> obtenerProductosMasVendidos(int limite) {
        Map<String, Integer> ventasPorProducto = new HashMap<>();
        
        try {
            List<Venta> todasVentas = ventaService.obtenerTodasVentas();
            
            // Contar unidades vendidas por producto
            for (Venta venta : todasVentas) {
                for (ItemVenta item : venta.getItems()) {
                    String codigo = item.getProducto().getCodigo();
                    int cantidad = item.getCantidad();
                    
                    ventasPorProducto.put(codigo, 
                        ventasPorProducto.getOrDefault(codigo, 0) + cantidad);
                }
            }
            
            // Ordenar por cantidad vendida (descendente) y limitar
            return ventasPorProducto.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limite)
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
                
        } catch (Exception e) {
            System.err.println("Error al obtener productos más vendidos: " + e.getMessage());
            e.printStackTrace();
            return new LinkedHashMap<>();
        }
    }

    /**
     * Obtiene ventas por rango de fechas
     */
    public List<Venta> obtenerVentasPorRango(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Venta> todasVentas = ventaService.obtenerTodasVentas();
        
        return todasVentas.stream()
            .filter(v -> {
                LocalDate fechaVenta = v.getFechaVenta().toLocalDate();
                return !fechaVenta.isBefore(fechaInicio) && !fechaVenta.isAfter(fechaFin);
            })
            .collect(Collectors.toList());
    }

    /**
     * Calcula el total de ingresos por rango de fechas
     */
    public BigDecimal calcularIngresosPorRango(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Venta> ventasRango = obtenerVentasPorRango(fechaInicio, fechaFin);
        
        return ventasRango.stream()
            .map(Venta::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Obtiene productos con stock crítico (menos de 5 unidades)
     */
    public List<Producto> obtenerProductosStockCritico() {
        List<Producto> todosProductos = productoService.obtenerTodosProductos();
        
        return todosProductos.stream()
            .filter(p -> p.getCantidad() > 0 && p.getCantidad() < 5)
            .sorted(Comparator.comparingInt(Producto::getCantidad))
            .collect(Collectors.toList());
    }

    /**
     * Obtiene productos sin stock
     */
    public List<Producto> obtenerProductosSinStock() {
        List<Producto> todosProductos = productoService.obtenerTodosProductos();
        
        return todosProductos.stream()
            .filter(p -> p.getCantidad() == 0)
            .collect(Collectors.toList());
    }

    /**
     * Calcula el valor total del inventario
     */
    public BigDecimal calcularValorTotalInventario() {
        List<Producto> todosProductos = productoService.obtenerTodosProductos();
        
        return todosProductos.stream()
            .map(Producto::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Obtiene ventas por vendedor
     */
    public Map<String, Integer> obtenerVentasPorVendedor() {
        Map<String, Integer> ventasPorVendedor = new HashMap<>();
        
        try {
            List<Venta> todasVentas = ventaService.obtenerTodasVentas();
            
            for (Venta venta : todasVentas) {
                String vendedor = venta.getUsuario().getNombre();
                ventasPorVendedor.put(vendedor, 
                    ventasPorVendedor.getOrDefault(vendedor, 0) + 1);
            }
            
        } catch (Exception e) {
            System.err.println("Error al obtener ventas por vendedor: " + e.getMessage());
        }
        
        return ventasPorVendedor;
    }

    /**
     * Obtiene ingresos por vendedor
     */
    public Map<String, BigDecimal> obtenerIngresosPorVendedor() {
        Map<String, BigDecimal> ingresosPorVendedor = new HashMap<>();
        
        try {
            List<Venta> todasVentas = ventaService.obtenerTodasVentas();
            
            for (Venta venta : todasVentas) {
                String vendedor = venta.getUsuario().getNombre();
                BigDecimal ingresoActual = ingresosPorVendedor.getOrDefault(
                    vendedor, BigDecimal.ZERO);
                ingresosPorVendedor.put(vendedor, ingresoActual.add(venta.getTotal()));
            }
            
        } catch (Exception e) {
            System.err.println("Error al obtener ingresos por vendedor: " + e.getMessage());
        }
        
        return ingresosPorVendedor;
    }

    /**
     * Exporta ventas a archivo de texto
     */
    public String exportarVentasATexto() throws IOException {
        List<Venta> ventas = ventaService.obtenerTodasVentas();
        String nombreArchivo = "reporte_ventas_" + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("========================================\n");
            writer.write("    REPORTE DE VENTAS\n");
            writer.write("========================================\n");
            writer.write("Fecha de generación: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            writer.write("Total de ventas: " + ventas.size() + "\n");
            writer.write("========================================\n\n");
            
            for (Venta venta : ventas) {
                writer.write(String.format("Venta #%d\n", venta.getId()));
                writer.write(String.format("Fecha: %s\n", 
                    venta.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
                writer.write(String.format("Vendedor: %s\n", venta.getUsuario().getNombre()));
                writer.write(String.format("Items: %d\n", venta.getCantidadItems()));
                writer.write(String.format("Total: $%,.2f\n", venta.getTotal()));
                writer.write("----------------------------------------\n");
                
                for (ItemVenta item : venta.getItems()) {
                    writer.write(String.format("  - %s x%d = $%,.2f\n",
                        item.getProducto().getNombre(),
                        item.getCantidad(),
                        item.getSubtotal()));
                }
                writer.write("\n");
            }
            
            writer.write("========================================\n");
            writer.write("FIN DEL REPORTE\n");
            writer.write("========================================\n");
        }
        
        return nombreArchivo;
    }

    /**
     * Exporta productos a archivo de texto
     */
    public String exportarProductosATexto() throws IOException {
        List<Producto> productos = productoService.obtenerTodosProductos();
        String nombreArchivo = "reporte_productos_" + 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("========================================\n");
            writer.write("    REPORTE DE PRODUCTOS\n");
            writer.write("========================================\n");
            writer.write("Fecha de generación: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n");
            writer.write("Total de productos: " + productos.size() + "\n");
            writer.write("========================================\n\n");
            
            for (Producto producto : productos) {
                writer.write(String.format("Código: %s\n", producto.getCodigo()));
                writer.write(String.format("Nombre: %s\n", producto.getNombre()));
                writer.write(String.format("Categoría: %s\n", producto.getCategoria()));
                writer.write(String.format("Stock: %d unidades\n", producto.getCantidad()));
                writer.write(String.format("Precio: $%,.2f\n", producto.getPrecio()));
                writer.write(String.format("Valor Total: $%,.2f\n", producto.getValorTotal()));
                
                String estado = producto.getCantidad() == 0 ? "SIN STOCK" :
                               producto.getCantidad() < 5 ? "STOCK BAJO" :
                               producto.getCantidad() < 10 ? "STOCK MEDIO" : "STOCK OK";
                writer.write(String.format("Estado: %s\n", estado));
                writer.write("----------------------------------------\n\n");
            }
            
            writer.write("========================================\n");
            writer.write("FIN DEL REPORTE\n");
            writer.write("========================================\n");
        }
        
        return nombreArchivo;
    }
}
