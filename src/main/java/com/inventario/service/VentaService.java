package com.inventario.service;

import com.inventario.dao.VentaDAO;
import com.inventario.model.Venta;
import com.inventario.model.ItemVenta;
import com.inventario.model.Producto;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio para la gestión de ventas
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class VentaService {
    
    private VentaDAO ventaDAO;
    private ProductoService productoService;

    public VentaService() {
        this.ventaDAO = new VentaDAO();
        this.productoService = new ProductoService();
    }

    /**
     * Procesa una venta completa
     */
    public boolean procesarVenta(Venta venta) {
        try {
            System.out.println("\n🔄 ===== INICIANDO PROCESO DE VENTA =====");
            System.out.println("Usuario: " + venta.getUsuario().getUsername());
            System.out.println("Total: $" + venta.getTotal());
            System.out.println("Items: " + venta.getItems().size());
            
            // Validar que la venta tiene items
            if (!venta.tieneItems()) {
                System.err.println("❌ Error: La venta no tiene items");
                return false;
            }
            System.out.println("✅ Venta tiene items");
            
            // IMPORTANTE: Validar stock recargando productos desde BD
            System.out.println("\n📦 Validando stock de productos...");
            for (ItemVenta item : venta.getItems()) {
                System.out.println("\n  Producto: " + item.getProducto().getNombre());
                System.out.println("  Código: " + item.getProducto().getCodigo());
                System.out.println("  Cantidad solicitada: " + item.getCantidad());
                System.out.println("  Stock en memoria: " + item.getProducto().getCantidad());
                
                // Recargar producto desde BD para tener stock actualizado
                Producto productoActualizado = productoService.obtenerProductoPorCodigo(
                    item.getProducto().getCodigo()
                );
                
                if (productoActualizado == null) {
                    System.err.println("  ❌ Error: Producto no encontrado en BD - " + item.getProducto().getCodigo());
                    return false;
                }
                
                System.out.println("  Stock en BD: " + productoActualizado.getCantidad());
                
                // Validar stock con datos actualizados
                if (productoActualizado.getCantidad() < item.getCantidad()) {
                    System.err.println(String.format(
                        "  ❌ Error: Stock insuficiente para %s\n" +
                        "     Disponible en BD: %d\n" +
                        "     Requerido: %d",
                        productoActualizado.getNombre(),
                        productoActualizado.getCantidad(),
                        item.getCantidad()
                    ));
                    return false;
                }
                
                System.out.println("  ✅ Stock suficiente");
                
                // Actualizar referencia del producto en el item con datos actualizados
                item.setProducto(productoActualizado);
            }
            
            System.out.println("\n✅ Validación de stock completada");
            
            // Guardar la venta en la base de datos
            System.out.println("\n💾 Guardando venta en base de datos...");
            boolean ventaGuardada = ventaDAO.crear(venta);
            
            if (ventaGuardada) {
                System.out.println("✅ Venta guardada en BD con ID: " + venta.getId());
                
                // Actualizar inventario (descontar stock)
                System.out.println("\n📉 Actualizando inventario...");
                for (ItemVenta item : venta.getItems()) {
                    Producto producto = item.getProducto();
                    int stockAnterior = producto.getCantidad();
                    int nuevaCantidad = producto.getCantidad() - item.getCantidad();
                    
                    System.out.println("\n  Producto: " + producto.getNombre());
                    System.out.println("  Stock anterior: " + stockAnterior);
                    System.out.println("  Cantidad vendida: " + item.getCantidad());
                    System.out.println("  Nuevo stock: " + nuevaCantidad);
                    
                    if (nuevaCantidad < 0) {
                        System.err.println("  ❌ Error: Cantidad negativa al descontar stock");
                        return false;
                    }
                    
                    producto.setCantidad(nuevaCantidad);
                    boolean actualizado = productoService.actualizarProducto(producto);
                    
                    if (!actualizado) {
                        System.err.println("  ❌ Error: No se pudo actualizar el stock del producto " + producto.getNombre());
                        return false;
                    }
                    
                    System.out.println("  ✅ Stock actualizado correctamente");
                }
                
                System.out.println("\n✅✅✅ VENTA PROCESADA EXITOSAMENTE ✅✅✅");
                System.out.println("ID de Venta: " + venta.getId());
                System.out.println("Total: $" + venta.getTotal());
                System.out.println("=========================================\n");
                return true;
            }
            
            System.err.println("\n❌ Error: No se pudo guardar la venta en la base de datos");
            System.err.println("Revisa los logs del VentaDAO arriba para más detalles");
            return false;
            
        } catch (Exception e) {
            System.err.println("\n❌❌❌ EXCEPCIÓN AL PROCESAR VENTA ❌❌❌");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("Tipo: " + e.getClass().getName());
            e.printStackTrace();
            System.err.println("=========================================\n");
            return false;
        }
    }

    /**
     * Obtiene todas las ventas
     */
    public List<Venta> obtenerTodasVentas() {
        return ventaDAO.obtenerTodos();
    }

    /**
     * Obtiene una venta por ID
     */
    public Venta obtenerVentaPorId(int id) {
        return ventaDAO.obtenerPorId(id);
    }

    /**
     * Obtiene ventas por fecha
     */
    public List<Venta> obtenerVentasPorFecha(LocalDate fecha) {
        return ventaDAO.obtenerPorFecha(fecha);
    }

    /**
     * Obtiene ventas por usuario
     */
    public List<Venta> obtenerVentasPorUsuario(int usuarioId) {
        return ventaDAO.obtenerPorUsuario(usuarioId);
    }

    /**
     * Obtiene el total de ventas del día
     */
    public double obtenerTotalVentasDelDia() {
        List<Venta> ventasHoy = obtenerVentasPorFecha(LocalDate.now());
        return ventasHoy.stream()
            .mapToDouble(v -> v.getTotal().doubleValue())
            .sum();
    }

    /**
     * Obtiene la cantidad de ventas del día
     */
    public int obtenerCantidadVentasDelDia() {
        return obtenerVentasPorFecha(LocalDate.now()).size();
    }
}
