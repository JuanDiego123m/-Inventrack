package com.inventario.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Gestor de la base de datos SQLite
 * Maneja la conexión y configuración de la base de datos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class DatabaseManager {
    
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private static final String DB_URL = "jdbc:sqlite:inventario.db";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        initializeDatabase();
    }

    /**
     * Obtiene la instancia singleton del DatabaseManager
     * 
     * @return Instancia del DatabaseManager
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Obtiene la conexión a la base de datos
     * 
     * @return Conexión a la base de datos
     * @throws SQLException Si hay error al conectar
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.severe("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    /**
     * Inicializa la base de datos y crea las tablas necesarias
     */
    private void initializeDatabase() {
        try (Connection conn = getConnection()) {
            createTables(conn);
            insertInitialData(conn);
            logger.info("Base de datos inicializada correctamente");
        } catch (SQLException e) {
            logger.severe("Error al inicializar la base de datos: " + e.getMessage());
        }
    }

    /**
     * Crea las tablas necesarias en la base de datos
     * 
     * @param conn Conexión a la base de datos
     * @throws SQLException Si hay error al crear las tablas
     */
    private void createTables(Connection conn) throws SQLException {
        String createUsuariosTable = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(100) NOT NULL,
                nombre VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                rol VARCHAR(20) NOT NULL,
                activo BOOLEAN DEFAULT TRUE,
                fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """;

        String createProductosTable = """
            CREATE TABLE IF NOT EXISTS productos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100) NOT NULL,
                descripcion TEXT,
                precio DECIMAL(10,2) NOT NULL,
                cantidad INTEGER NOT NULL,
                categoria VARCHAR(50) NOT NULL,
                activo BOOLEAN DEFAULT TRUE,
                fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """;

        String createVentasTable = """
            CREATE TABLE IF NOT EXISTS ventas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario_id INTEGER NOT NULL,
                total DECIMAL(10,2) NOT NULL,
                fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
            )
        """;

        String createItemsVentaTable = """
            CREATE TABLE IF NOT EXISTS items_venta (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                venta_id INTEGER NOT NULL,
                producto_id INTEGER NOT NULL,
                cantidad INTEGER NOT NULL,
                precio_unitario DECIMAL(10,2) NOT NULL,
                subtotal DECIMAL(10,2) NOT NULL,
                FOREIGN KEY (venta_id) REFERENCES ventas(id),
                FOREIGN KEY (producto_id) REFERENCES productos(id)
            )
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createUsuariosTable);
            stmt.execute(createProductosTable);
            stmt.execute(createVentasTable);
            stmt.execute(createItemsVentaTable);
            logger.info("Tablas creadas correctamente");
        }
    }

    /**
     * Inserta datos iniciales en la base de datos
     * 
     * @param conn Conexión a la base de datos
     * @throws SQLException Si hay error al insertar datos
     */
    private void insertInitialData(Connection conn) throws SQLException {
        // Verificar si ya existen datos
        String checkUsuarios = "SELECT COUNT(*) FROM usuarios";
        try (var stmt = conn.createStatement();
             var rs = stmt.executeQuery(checkUsuarios)) {
            
            if (rs.next() && rs.getInt(1) > 0) {
                logger.info("Datos iniciales ya existen, omitiendo inserción");
                return;
            }
        }

        // Insertar usuarios iniciales
        String insertUsuarios = """
            INSERT INTO usuarios (username, password, nombre, email, rol, activo) VALUES
            ('superadmin', 'superadmin123', 'Super Administrador', 'superadmin@inventario.com', 'SUPER_ADMIN', TRUE),
            ('admin', 'admin123', 'Administrador', 'admin@inventario.com', 'ADMIN', TRUE),
            ('vendedor', 'vendedor123', 'Vendedor', 'vendedor@inventario.com', 'VENDEDOR', TRUE),
            ('juan', 'juan123', 'Juan Pérez', 'juan@inventario.com', 'VENDEDOR', TRUE)
        """;

        // Insertar productos iniciales
        String insertProductos = """
            INSERT INTO productos (nombre, descripcion, precio, cantidad, categoria, activo) VALUES
            ('Laptop HP', 'Laptop HP Pavilion 15 pulgadas', 2500000.00, 5, 'Electrónica', TRUE),
            ('iPhone 13', 'Apple iPhone 13 128GB', 3500000.00, 3, 'Electrónica', TRUE),
            ('Camiseta Nike', 'Camiseta deportiva Nike Dri-FIT', 85000.00, 20, 'Ropa', TRUE),
            ('Sofá 3 Puestos', 'Sofá moderno 3 puestos color gris', 1200000.00, 2, 'Hogar', TRUE),
            ('Libro Java', 'Java: The Complete Reference', 150000.00, 10, 'Otros', TRUE)
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertUsuarios);
            stmt.execute(insertProductos);
            logger.info("Datos iniciales insertados correctamente");
        }
    }

    /**
     * Verifica si la base de datos está funcionando correctamente
     * 
     * @return true si la base de datos está funcionando
     */
    public boolean isDatabaseWorking() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            logger.severe("Error al verificar la base de datos: " + e.getMessage());
            return false;
        }
    }

    /**
     * Ejecuta una consulta de prueba
     * 
     * @return true si la consulta se ejecuta correctamente
     */
    public boolean testConnection() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT 1")) {
            return rs.next();
        } catch (SQLException e) {
            logger.severe("Error en la prueba de conexión: " + e.getMessage());
            return false;
        }
    }
}
