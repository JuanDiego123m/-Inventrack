package com.inventario.service;

import com.inventario.model.Producto;
import com.inventario.dao.ProductoDAO;
import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio para la gestión de productos
 * Implementa la lógica de negocio para CRUD de productos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class ProductoService {
    
    private final ProductoDAO productoDAO;

    public ProductoService() {
        this.productoDAO = new ProductoDAO();
    }

    // Los productos iniciales se crean automáticamente en DatabaseManager

    /**
     * Obtiene todos los productos activos
     * 
     * @return Lista de productos activos
     */
    public List<Producto> obtenerTodosProductos() {
        return productoDAO.obtenerTodos();
    }

    /**
     * Obtiene un producto por su ID
     * 
     * @param id ID del producto
     * @return Producto encontrado o null
     */
    public Producto obtenerProductoPorId(int id) {
        return productoDAO.obtenerPorId(id);
    }

    /**
     * Busca productos por nombre y categoría
     * 
     * @param nombre Nombre a buscar (puede ser parcial)
     * @param categoria Categoría a filtrar
     * @return Lista de productos que coinciden
     */
    public List<Producto> buscarProductos(String nombre, String categoria) {
        return productoDAO.buscar(nombre, categoria);
    }

    /**
     * Guarda un nuevo producto
     * 
     * @param producto Producto a guardar
     * @return true si se guardó exitosamente
     */
    public boolean guardarProducto(Producto producto) {
        if (!validarProducto(producto)) {
            return false;
        }
        
        return productoDAO.crear(producto);
    }

    /**
     * Actualiza un producto existente
     * 
     * @param producto Producto actualizado
     * @return true si se actualizó exitosamente
     */
    public boolean actualizarProducto(Producto producto) {
        if (!validarProducto(producto)) {
            return false;
        }
        
        return productoDAO.actualizar(producto);
    }

    /**
     * Elimina un producto (eliminación lógica)
     * 
     * @param id ID del producto a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean eliminarProducto(int id) {
        return productoDAO.eliminar(id);
    }

    /**
     * Valida un producto según las reglas de negocio
     * 
     * @param producto Producto a validar
     * @return true si el producto es válido
     */
    public boolean validarProducto(Producto producto) {
        if (producto == null) {
            return false;
        }
        
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            return false;
        }
        
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        
        if (producto.getCantidad() < 0) {
            return false;
        }
        
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            return false;
        }
        
        return true;
    }

    /**
     * Obtiene productos con stock bajo (menos de 5 unidades)
     * 
     * @return Lista de productos con stock bajo
     */
    public List<Producto> obtenerProductosStockBajo() {
        return productoDAO.obtenerConStockBajo(5);
    }

    /**
     * Obtiene productos por categoría
     * 
     * @param categoria Categoría a filtrar
     * @return Lista de productos de la categoría
     */
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        return productoDAO.obtenerPorCategoria(categoria);
    }

    /**
     * Obtiene el valor total del inventario
     * 
     * @return Valor total del inventario
     */
    public BigDecimal obtenerValorTotalInventario() {
        return productoDAO.obtenerValorTotalInventario();
    }

    /**
     * Obtiene estadísticas del inventario
     * 
     * @return Array con [totalProductos, valorTotal, productosStockBajo]
     */
    public Object[] obtenerEstadisticas() {
        List<Producto> productos = obtenerTodosProductos();
        int totalProductos = productos.size();
        BigDecimal valorTotal = obtenerValorTotalInventario();
        int stockBajo = obtenerProductosStockBajo().size();
        
        return new Object[]{totalProductos, valorTotal, stockBajo};
    }

    /**
     * Reduce la cantidad de un producto (para ventas)
     * 
     * @param id ID del producto
     * @param cantidadVendida Cantidad a reducir
     * @return true si se redujo exitosamente
     */
    public boolean reducirCantidad(int id, int cantidadVendida) {
        return productoDAO.reducirCantidad(id, cantidadVendida);
    }

    /**
     * Aumenta la cantidad de un producto (para reposición)
     * 
     * @param id ID del producto
     * @param cantidadReposicion Cantidad a agregar
     * @return true si se aumentó exitosamente
     */
    public boolean aumentarCantidad(int id, int cantidadReposicion) {
        return productoDAO.aumentarCantidad(id, cantidadReposicion);
    }
}

