-- Script de inicialización de la base de datos
-- Sistema de Inventario

-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    cantidad INTEGER NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de ventas
CREATE TABLE IF NOT EXISTS ventas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario_id INTEGER NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Crear tabla de items de venta
CREATE TABLE IF NOT EXISTS items_venta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    venta_id INTEGER NOT NULL,
    producto_id INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES ventas(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- Insertar usuarios iniciales
INSERT OR IGNORE INTO usuarios (username, password, nombre, email, rol, activo) VALUES
('superadmin', 'superadmin123', 'Super Administrador', 'superadmin@inventario.com', 'SUPER_ADMIN', TRUE),
('admin', 'admin123', 'Administrador', 'admin@inventario.com', 'ADMIN', TRUE),
('vendedor', 'vendedor123', 'Vendedor', 'vendedor@inventario.com', 'VENDEDOR', TRUE),
('juan', 'juan123', 'Juan Pérez', 'juan@inventario.com', 'VENDEDOR', TRUE);

-- Insertar productos iniciales
INSERT OR IGNORE INTO productos (nombre, descripcion, precio, cantidad, categoria, activo) VALUES
('Laptop HP', 'Laptop HP Pavilion 15 pulgadas', 2500000.00, 5, 'Electrónica', TRUE),
('iPhone 13', 'Apple iPhone 13 128GB', 3500000.00, 3, 'Electrónica', TRUE),
('Camiseta Nike', 'Camiseta deportiva Nike Dri-FIT', 85000.00, 20, 'Ropa', TRUE),
('Sofá 3 Puestos', 'Sofá moderno 3 puestos color gris', 1200000.00, 2, 'Hogar', TRUE),
('Libro Java', 'Java: The Complete Reference', 150000.00, 10, 'Otros', TRUE);

-- Crear índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_usuarios_username ON usuarios(username);
CREATE INDEX IF NOT EXISTS idx_usuarios_rol ON usuarios(rol);
CREATE INDEX IF NOT EXISTS idx_productos_categoria ON productos(categoria);
CREATE INDEX IF NOT EXISTS idx_productos_activo ON productos(activo);
CREATE INDEX IF NOT EXISTS idx_ventas_fecha ON ventas(fecha_venta);
CREATE INDEX IF NOT EXISTS idx_items_venta_venta_id ON items_venta(venta_id);
