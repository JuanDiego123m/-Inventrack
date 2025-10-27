package com.inventario.dao;

import com.inventario.database.DatabaseManager;
import com.inventario.model.Venta;
import com.inventario.model.ItemVenta;
import com.inventario.model.Usuario;
import com.inventario.model.Producto;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones de ventas en la base de datos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class VentaDAO {
    
    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;

    public VentaDAO() {
        this.usuarioDAO = new UsuarioDAO();
        this.productoDAO = new ProductoDAO();
    }

    /**
     * Crea una nueva venta con sus items
     */
    public boolean crear(Venta venta) {
        String sqlVenta = "INSERT INTO ventas (usuario_id, total, fecha_venta) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO items_venta (venta_id, producto_id, cantidad, precio_unitario, subtotal) " +
                        "VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = null;
        
        try {
            System.out.println("📝 Iniciando creación de venta...");
            System.out.println("   Usuario: " + (venta.getUsuario() != null ? venta.getUsuario().getUsername() : "NULL"));
            System.out.println("   Usuario ID: " + (venta.getUsuario() != null ? venta.getUsuario().getId() : "NULL"));
            System.out.println("   Total: " + venta.getTotal());
            System.out.println("   Items: " + venta.getItems().size());
            
            // Validar que el usuario tiene ID válido
            if (venta.getUsuario() == null) {
                System.err.println("❌ ERROR CRÍTICO: El usuario es NULL");
                return false;
            }
            
            if (venta.getUsuario().getId() <= 0) {
                System.err.println("❌ ERROR CRÍTICO: El usuario tiene ID inválido: " + venta.getUsuario().getId());
                System.err.println("   Usuario: " + venta.getUsuario().getUsername());
                System.err.println("   Esto indica que el usuario no fue cargado correctamente desde la BD");
                return false;
            }
            
            // Validar que todos los productos tienen IDs válidos
            for (ItemVenta item : venta.getItems()) {
                if (item.getProducto() == null) {
                    System.err.println("❌ ERROR CRÍTICO: Un item tiene producto NULL");
                    return false;
                }
                if (item.getProducto().getId() <= 0) {
                    System.err.println("❌ ERROR CRÍTICO: Producto tiene ID inválido: " + item.getProducto().getId());
                    System.err.println("   Producto: " + item.getProducto().getNombre());
                    System.err.println("   Código: " + item.getProducto().getCodigo());
                    return false;
                }
            }
            
            System.out.println("✅ Validaciones iniciales pasadas");
            
            conn = DatabaseManager.getInstance().getConnection();
            conn.setAutoCommit(false); // Iniciar transacción
            System.out.println("✅ Conexión obtenida, transacción iniciada");
            
            // Insertar venta
            try (PreparedStatement pstmt = conn.prepareStatement(sqlVenta)) {
                pstmt.setInt(1, venta.getUsuario().getId());
                pstmt.setBigDecimal(2, venta.getTotal());
                pstmt.setString(3, venta.getFechaVenta().toString());
                
                System.out.println("📤 Ejecutando INSERT de venta...");
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("✅ Venta insertada. Filas afectadas: " + rowsAffected);
            }
            
            // Obtener el ID generado usando last_insert_rowid() de SQLite
            System.out.println("🔍 Obteniendo ID de venta generado...");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    venta.setId(rs.getInt(1));
                    System.out.println("✅ ID de venta generado: " + venta.getId());
                } else {
                    System.err.println("❌ No se pudo obtener el ID generado");
                    throw new SQLException("No se pudo obtener el ID de la venta");
                }
            }
            
            // Insertar items
            System.out.println("📤 Insertando items de venta...");
            try (PreparedStatement pstmt = conn.prepareStatement(sqlItem)) {
                for (ItemVenta item : venta.getItems()) {
                    System.out.println("   - Producto ID: " + item.getProducto().getId() + 
                                     ", Cantidad: " + item.getCantidad() + 
                                     ", Subtotal: " + item.getSubtotal());
                    
                    pstmt.setInt(1, venta.getId());
                    pstmt.setInt(2, item.getProducto().getId());
                    pstmt.setInt(3, item.getCantidad());
                    pstmt.setBigDecimal(4, item.getPrecioUnitario());
                    pstmt.setBigDecimal(5, item.getSubtotal());
                    
                    pstmt.addBatch();
                }
                
                int[] batchResults = pstmt.executeBatch();
                System.out.println("✅ Items insertados: " + batchResults.length);
            }
            
            conn.commit(); // Confirmar transacción
            System.out.println("✅ Transacción confirmada exitosamente");
            return true;
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR SQL al crear venta:");
            System.err.println("   Mensaje: " + e.getMessage());
            System.err.println("   SQL State: " + e.getSQLState());
            System.err.println("   Error Code: " + e.getErrorCode());
            
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir cambios
                    System.err.println("🔄 Transacción revertida (rollback)");
                } catch (SQLException ex) {
                    System.err.println("❌ Error al hacer rollback: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
            
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Obtiene todas las ventas
     */
    public List<Venta> obtenerTodos() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas ORDER BY fecha_venta DESC";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Primero, leer todos los datos del ResultSet
            while (rs.next()) {
                Venta venta = mapearVentaSinRelaciones(rs);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Luego, cargar las relaciones (usuario e items) fuera del ResultSet
        for (Venta venta : ventas) {
            cargarRelacionesVenta(venta);
        }
        
        return ventas;
    }

    /**
     * Obtiene una venta por ID
     */
    public Venta obtenerPorId(int id) {
        String sql = "SELECT * FROM ventas WHERE id = ?";
        Venta venta = null;
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    venta = mapearVentaSinRelaciones(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener venta: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Cargar relaciones fuera del ResultSet
        if (venta != null) {
            cargarRelacionesVenta(venta);
        }
        
        return venta;
    }

    /**
     * Obtiene ventas por fecha
     */
    public List<Venta> obtenerPorFecha(LocalDate fecha) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE DATE(fecha_venta) = ? " +
                    "ORDER BY fecha_venta DESC";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, fecha.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Venta venta = mapearVentaSinRelaciones(rs);
                    ventas.add(venta);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas por fecha: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Cargar relaciones fuera del ResultSet
        for (Venta venta : ventas) {
            cargarRelacionesVenta(venta);
        }
        
        return ventas;
    }

    /**
     * Obtiene ventas por usuario
     */
    public List<Venta> obtenerPorUsuario(int usuarioId) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM ventas WHERE usuario_id = ? " +
                    "ORDER BY fecha_venta DESC";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, usuarioId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Venta venta = mapearVentaSinRelaciones(rs);
                    ventas.add(venta);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas por usuario: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Cargar relaciones fuera del ResultSet
        for (Venta venta : ventas) {
            cargarRelacionesVenta(venta);
        }
        
        return ventas;
    }

    /**
     * Mapea un ResultSet a un objeto Venta sin cargar relaciones
     * (para evitar stmt pointer is closed)
     */
    private Venta mapearVentaSinRelaciones(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId(rs.getInt("id"));
        venta.setTotal(rs.getBigDecimal("total"));
        venta.setFechaVenta(LocalDateTime.parse(rs.getString("fecha_venta")));
        
        // Guardar solo el ID del usuario para cargarlo después
        int usuarioId = rs.getInt("usuario_id");
        Usuario usuarioTemp = new Usuario();
        usuarioTemp.setId(usuarioId);
        venta.setUsuario(usuarioTemp);
        
        return venta;
    }
    
    /**
     * Carga las relaciones de una venta (usuario e items)
     * Se ejecuta fuera del ResultSet original para evitar conflictos
     */
    private void cargarRelacionesVenta(Venta venta) {
        // Cargar usuario completo
        int usuarioId = venta.getUsuario().getId();
        Usuario usuario = usuarioDAO.obtenerPorId(usuarioId);
        if (usuario != null) {
            venta.setUsuario(usuario);
        }
        
        // Cargar items de la venta
        cargarItemsVenta(venta);
    }

    /**
     * Carga los items de una venta
     */
    private void cargarItemsVenta(Venta venta) {
        String sql = "SELECT * FROM items_venta WHERE venta_id = ?";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, venta.getId());
            
            List<ItemVenta> items = new ArrayList<>();
            
            try (ResultSet rs = pstmt.executeQuery()) {
                // Primero, leer todos los datos del ResultSet
                while (rs.next()) {
                    ItemVenta item = new ItemVenta();
                    item.setId(rs.getInt("id"));
                    item.setVenta(venta);
                    item.setCantidad(rs.getInt("cantidad"));
                    item.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
                    item.setSubtotal(rs.getBigDecimal("subtotal"));
                    
                    // Guardar el ID del producto para cargarlo después
                    int productoId = rs.getInt("producto_id");
                    
                    // Crear un producto temporal con solo el ID
                    Producto productoTemp = new Producto();
                    productoTemp.setId(productoId);
                    item.setProducto(productoTemp);
                    
                    items.add(item);
                }
            }
            
            // Ahora, cargar los productos fuera del ResultSet
            for (ItemVenta item : items) {
                int productoId = item.getProducto().getId();
                Producto producto = productoDAO.obtenerPorId(productoId);
                if (producto != null) {
                    item.setProducto(producto);
                } else {
                    System.err.println("⚠️ Advertencia: Producto con ID " + productoId + " no encontrado");
                }
            }
            
            venta.setItems(items);
            
        } catch (SQLException e) {
            System.err.println("Error al cargar items de venta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina una venta (eliminación física)
     * NOTA: Esto eliminará también los items_venta por CASCADE
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM ventas WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar venta: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
