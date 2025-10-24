package com.inventario.dao;

import com.inventario.database.DatabaseManager;
import com.inventario.model.Producto;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Data Access Object para la gestión de productos en la base de datos
 * Implementa operaciones CRUD para productos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class ProductoDAO {
    
    private static final Logger logger = Logger.getLogger(ProductoDAO.class.getName());
    private final DatabaseManager dbManager;

    public ProductoDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }

    /**
     * Obtiene todos los productos activos
     * 
     * @return Lista de productos
     */
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = TRUE ORDER BY id";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearResultSet(rs));
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener productos: " + e.getMessage());
        }

        return productos;
    }

    /**
     * Busca un producto por su ID
     * 
     * @param id ID del producto
     * @return Producto encontrado o null
     */
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id = ? AND activo = TRUE";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener producto por ID: " + e.getMessage());
        }

        return null;
    }

    /**
     * Busca productos por nombre y categoría
     * 
     * @param nombre Nombre a buscar (puede ser parcial)
     * @param categoria Categoría a filtrar
     * @return Lista de productos que coinciden
     */
    public List<Producto> buscar(String nombre, String categoria) {
        List<Producto> productos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM productos WHERE activo = TRUE");
        List<Object> params = new ArrayList<>();

        if (nombre != null && !nombre.trim().isEmpty()) {
            sql.append(" AND nombre LIKE ?");
            params.add("%" + nombre.trim() + "%");
        }

        if (categoria != null && !categoria.trim().isEmpty()) {
            sql.append(" AND categoria = ?");
            params.add(categoria.trim());
        }

        sql.append(" ORDER BY nombre");

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearResultSet(rs));
                }
            }

        } catch (SQLException e) {
            logger.severe("Error al buscar productos: " + e.getMessage());
        }

        return productos;
    }

    /**
     * Crea un nuevo producto
     * 
     * @param producto Producto a crear
     * @return true si se creó exitosamente
     */
    public boolean crear(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, cantidad, categoria, activo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setBigDecimal(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());
            stmt.setString(5, producto.getCategoria());
            stmt.setBoolean(6, producto.isActivo());

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            logger.severe("Error al crear producto: " + e.getMessage());
        }

        return false;
    }

    /**
     * Actualiza un producto existente
     * 
     * @param producto Producto a actualizar
     * @return true si se actualizó exitosamente
     */
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, cantidad = ?, categoria = ?, activo = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setBigDecimal(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());
            stmt.setString(5, producto.getCategoria());
            stmt.setBoolean(6, producto.isActivo());
            stmt.setInt(7, producto.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error al actualizar producto: " + e.getMessage());
        }

        return false;
    }

    /**
     * Elimina un producto (eliminación lógica)
     * 
     * @param id ID del producto a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean eliminar(int id) {
        String sql = "UPDATE productos SET activo = FALSE WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error al eliminar producto: " + e.getMessage());
        }

        return false;
    }

    /**
     * Obtiene productos con stock bajo
     * 
     * @param stockMinimo Stock mínimo (por defecto 5)
     * @return Lista de productos con stock bajo
     */
    public List<Producto> obtenerConStockBajo(int stockMinimo) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE cantidad < ? AND activo = TRUE ORDER BY cantidad ASC";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockMinimo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearResultSet(rs));
                }
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener productos con stock bajo: " + e.getMessage());
        }

        return productos;
    }

    /**
     * Obtiene productos por categoría
     * 
     * @param categoria Categoría a filtrar
     * @return Lista de productos de la categoría
     */
    public List<Producto> obtenerPorCategoria(String categoria) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE categoria = ? AND activo = TRUE ORDER BY nombre";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(mapearResultSet(rs));
                }
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener productos por categoría: " + e.getMessage());
        }

        return productos;
    }

    /**
     * Obtiene el valor total del inventario
     * 
     * @return Valor total del inventario
     */
    public BigDecimal obtenerValorTotalInventario() {
        String sql = "SELECT SUM(precio * cantidad) as total FROM productos WHERE activo = TRUE";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total");
                return total != null ? total : BigDecimal.ZERO;
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener valor total del inventario: " + e.getMessage());
        }

        return BigDecimal.ZERO;
    }

    /**
     * Reduce la cantidad de un producto
     * 
     * @param id ID del producto
     * @param cantidadReducir Cantidad a reducir
     * @return true si se redujo exitosamente
     */
    public boolean reducirCantidad(int id, int cantidadReducir) {
        String sql = "UPDATE productos SET cantidad = cantidad - ? WHERE id = ? AND cantidad >= ? AND activo = TRUE";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidadReducir);
            stmt.setInt(2, id);
            stmt.setInt(3, cantidadReducir);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error al reducir cantidad del producto: " + e.getMessage());
        }

        return false;
    }

    /**
     * Aumenta la cantidad de un producto
     * 
     * @param id ID del producto
     * @param cantidadAumentar Cantidad a aumentar
     * @return true si se aumentó exitosamente
     */
    public boolean aumentarCantidad(int id, int cantidadAumentar) {
        String sql = "UPDATE productos SET cantidad = cantidad + ? WHERE id = ? AND activo = TRUE";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cantidadAumentar);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error al aumentar cantidad del producto: " + e.getMessage());
        }

        return false;
    }

    /**
     * Mapea un ResultSet a un objeto Producto
     * 
     * @param rs ResultSet con los datos del producto
     * @return Producto mapeado
     * @throws SQLException Si hay error al mapear
     */
    private Producto mapearResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setCantidad(rs.getInt("cantidad"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setActivo(rs.getBoolean("activo"));
        
        // Mapear fecha de registro si existe
        Timestamp timestamp = rs.getTimestamp("fecha_registro");
        if (timestamp != null) {
            producto.setFechaRegistro(timestamp.toLocalDateTime());
        }
        
        return producto;
    }
}
