# 🛒 Módulo de Procesar Ventas

## 📋 Descripción General

El módulo de Procesar Ventas permite a los usuarios registrar y procesar ventas de productos del inventario de manera intuitiva y eficiente. Este módulo implementa un carrito de compras funcional que actualiza automáticamente el inventario al procesar cada venta.

## 🎯 Funcionalidades

### ✅ **Funcionalidades Principales**

1. **Selección de Productos**
   - ComboBox con lista de productos disponibles
   - Muestra código, nombre y stock de cada producto
   - Solo muestra productos con stock disponible (cantidad > 0)

2. **Gestión del Carrito**
   - Agregar productos con cantidad personalizada
   - Ver productos agregados en tabla interactiva
   - Quitar productos del carrito
   - Limpiar todo el carrito
   - Actualización automática de cantidades si se agrega el mismo producto

3. **Cálculo Automático**
   - Subtotal por producto (precio × cantidad)
   - Total general de la venta
   - Resumen con cantidad de items y unidades

4. **Validaciones**
   - Verificación de stock disponible
   - No permite cantidades mayores al stock
   - Validación de productos seleccionados
   - Confirmación antes de procesar venta

5. **Procesamiento de Venta**
   - Guarda la venta en la base de datos
   - Actualiza automáticamente el inventario
   - Genera ID único de venta
   - Registra fecha y hora
   - Asocia la venta con el usuario vendedor

## 🏗️ Arquitectura

### **Componentes del Módulo**

```
Modelo (Model)
├── Venta.java - Entidad principal de venta
├── ItemVenta.java - Items individuales de la venta
└── Producto.java - Información de productos

Vista (View)
└── VentaFrame.java - Interfaz gráfica del módulo

Controlador (Controller)
└── VentaController.java - Lógica de negocio

Servicio (Service)
└── VentaService.java - Operaciones de ventas

DAO (Data Access Object)
└── VentaDAO.java - Acceso a datos de ventas
```

## 📊 Base de Datos

### **Tabla: ventas**
```sql
CREATE TABLE ventas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario_id INTEGER NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
```

### **Tabla: items_venta**
```sql
CREATE TABLE items_venta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    venta_id INTEGER NOT NULL,
    producto_id INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES ventas(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);
```

## 💻 Interfaz de Usuario

### **Diseño Moderno**

La interfaz utiliza un diseño moderno y profesional con:

- **Colores Corporativos**
  - Primario (Azul): `#2980b9`
  - Éxito (Verde): `#2ecc71`
  - Peligro (Rojo): `#e74c3c`
  - Advertencia (Amarillo): `#f1c40f`

- **Componentes Principales**
  1. **Panel Superior**: Título y usuario actual
  2. **Panel de Productos**: Selección y cantidad
  3. **Tabla del Carrito**: Vista de items agregados
  4. **Panel de Resumen**: Información y total
  5. **Botones de Acción**: Procesar, Cancelar

### **Tabla del Carrito**

| Columna | Descripción |
|---------|-------------|
| **Producto** | Código y nombre del producto |
| **Precio Unit.** | Precio unitario formateado |
| **Cantidad** | Unidades a vender |
| **Subtotal** | Total por producto |

## 🔄 Flujo de Trabajo

### **Proceso de Venta**

```
1. Usuario abre "Procesar Venta"
   ↓
2. Sistema carga productos disponibles
   ↓
3. Usuario selecciona producto y cantidad
   ↓
4. Usuario hace clic en "Agregar al Carrito"
   ↓
5. Sistema valida stock disponible
   ↓
6. Producto se agrega a la tabla
   ↓
7. Se actualiza el total automáticamente
   ↓
8. Usuario repite pasos 3-7 según necesite
   ↓
9. Usuario hace clic en "Procesar Venta"
   ↓
10. Sistema solicita confirmación
    ↓
11. Usuario confirma
    ↓
12. Sistema guarda venta en BD
    ↓
13. Sistema actualiza inventario
    ↓
14. Muestra mensaje de éxito con ID de venta
    ↓
15. Carrito se limpia automáticamente
```

## 🔐 Permisos y Acceso

### **Roles con Acceso**

- ✅ **SUPER_ADMIN** - Acceso completo
- ✅ **ADMIN** - Acceso completo
- ✅ **VENDEDOR** - Acceso completo
- ✅ **CONSULTA** - Solo visualización

### **Restricciones**

- Solo usuarios autenticados pueden procesar ventas
- Cada venta queda asociada al usuario que la procesó
- No se puede eliminar una venta procesada (auditoría)

## 📝 Ejemplos de Uso

### **Ejemplo 1: Venta Simple**

```java
// El usuario selecciona:
Producto: PROD001 - Laptop HP (Stock: 5)
Cantidad: 2

// Sistema calcula:
Precio Unitario: $2,500,000
Subtotal: $5,000,000
Total Venta: $5,000,000

// Al procesar:
✅ Venta #1 registrada
✅ Stock actualizado: 5 → 3 unidades
```

### **Ejemplo 2: Venta Múltiple**

```java
// El usuario agrega varios productos:
1. Laptop HP × 1 = $2,500,000
2. iPhone 13 × 2 = $7,000,000
3. Camiseta Nike × 5 = $425,000

// Total: $9,925,000

// Al procesar:
✅ Venta #2 registrada con 3 items
✅ Inventario actualizado para los 3 productos
```

## ⚠️ Validaciones Implementadas

### **Validaciones de Negocio**

1. ✅ **Stock Suficiente**
   ```
   Si cantidad solicitada > stock disponible:
     → Mostrar error "Stock insuficiente"
   ```

2. ✅ **Producto Seleccionado**
   ```
   Si no hay producto seleccionado:
     → Mostrar error "Seleccione un producto"
   ```

3. ✅ **Carrito No Vacío**
   ```
   Si carrito está vacío al procesar:
     → Mostrar error "El carrito está vacío"
   ```

4. ✅ **Confirmación de Venta**
   ```
   Antes de procesar:
     → Solicitar confirmación con resumen
   ```

## 🎨 Características de UX

### **Elementos de Experiencia de Usuario**

1. **Feedback Visual**
   - Colores según tipo de acción
   - Hover effects en botones
   - Mensajes claros de éxito/error

2. **Información en Tiempo Real**
   - Stock actualizado en combo
   - Total calculado automáticamente
   - Resumen dinámico

3. **Navegación Intuitiva**
   - Flujo lineal claro
   - Botones con iconos descriptivos
   - Mensajes de confirmación

4. **Prevención de Errores**
   - Validaciones en tiempo real
   - Confirmaciones antes de acciones críticas
   - Mensajes de ayuda contextuales

## 🔧 Métodos Principales

### **VentaController.java**

```java
// Carga productos disponibles
public void cargarProductosDisponibles()

// Agrega producto al carrito
public void agregarProducto()

// Quita producto seleccionado
public void quitarProductoSeleccionado()

// Limpia todo el carrito
public void limpiarCarrito()

// Procesa la venta final
public void procesarVenta()

// Calcula total de la venta
private void calcularTotal()
```

### **VentaService.java**

```java
// Procesa venta completa (guarda y actualiza inventario)
public boolean procesarVenta(Venta venta)

// Obtiene todas las ventas
public List<Venta> obtenerTodasVentas()

// Obtiene venta por ID
public Venta obtenerVentaPorId(int id)

// Obtiene ventas por fecha
public List<Venta> obtenerVentasPorFecha(LocalDate fecha)

// Obtiene total de ventas del día
public double obtenerTotalVentasDelDia()
```

## 📈 Estadísticas y Reportes

### **Datos Disponibles**

El módulo prepara los datos para reportes futuros:

- **Total de ventas por día/mes/año**
- **Ventas por usuario (vendedor)**
- **Productos más vendidos**
- **Ingresos generados**
- **Promedio de venta**
- **Historial completo de transacciones**

## 🚀 Cómo Usar el Módulo

### **Paso 1: Acceder**
```
Menú Principal → Procesar Ventas
O
Botón "🛒 Procesar Ventas" en panel principal
```

### **Paso 2: Agregar Productos**
```
1. Seleccionar producto del combo
2. Ajustar cantidad con spinner
3. Clic en "➕ Agregar al Carrito"
4. Repetir para más productos
```

### **Paso 3: Revisar Carrito**
```
- Ver productos en tabla
- Verificar cantidades
- Quitar items si es necesario
- Revisar total
```

### **Paso 4: Procesar Venta**
```
1. Clic en "💰 Procesar Venta"
2. Confirmar en diálogo
3. Esperar mensaje de éxito
4. Listo! Inventario actualizado
```

## ⚡ Características Técnicas

### **Transacciones**

- ✅ Uso de transacciones SQL
- ✅ Rollback en caso de error
- ✅ Integridad referencial garantizada

### **Optimización**

- ✅ Consultas eficientes con índices
- ✅ Carga lazy de items de venta
- ✅ Caché de productos disponibles

### **Seguridad**

- ✅ Prepared Statements (prevención SQL Injection)
- ✅ Validación de permisos
- ✅ Auditoría de operaciones

## 🐛 Manejo de Errores

### **Errores Comunes**

| Error | Causa | Solución |
|-------|-------|----------|
| Stock insuficiente | Cantidad > disponible | Reducir cantidad |
| Producto no seleccionado | No hay selección en combo | Seleccionar producto |
| Carrito vacío | Sin items al procesar | Agregar productos |
| Error de BD | Problema de conexión | Reintentar operación |

## 📦 Dependencias

### **Clases Relacionadas**

- `VentaFrame.java` - Interfaz gráfica
- `VentaController.java` - Lógica de negocio
- `VentaService.java` - Servicios
- `VentaDAO.java` - Acceso a datos
- `Venta.java` - Modelo de venta
- `ItemVenta.java` - Modelo de item
- `Producto.java` - Modelo de producto
- `Usuario.java` - Modelo de usuario

## 🎯 Próximas Mejoras

### **Funcionalidades Futuras**

1. ⏳ **Método de Pago**
   - Efectivo
   - Tarjeta
   - Transferencia

2. ⏳ **Descuentos**
   - Porcentaje
   - Monto fijo
   - Cupones

3. ⏳ **Impuestos**
   - IVA
   - Otros impuestos

4. ⏳ **Facturación**
   - Generación automática de factura
   - Impresión
   - Envío por email

5. ⏳ **Devoluciones**
   - Registro de devoluciones
   - Reintegro al inventario
   - Notas de crédito

## ✅ Testing

### **Casos de Prueba**

1. ✅ Agregar producto con stock suficiente
2. ✅ Intentar agregar producto sin stock
3. ✅ Agregar mismo producto múltiples veces
4. ✅ Quitar producto del carrito
5. ✅ Limpiar carrito completo
6. ✅ Procesar venta exitosa
7. ✅ Verificar actualización de inventario
8. ✅ Cancelar venta sin procesar

## 📱 Responsividad

- ✅ Ventana redimensionable
- ✅ Componentes adaptables
- ✅ Tabla con scroll automático
- ✅ Layout flexible

## 🎓 Conceptos Aplicados

### **Patrones de Diseño**

- ✅ MVC (Model-View-Controller)
- ✅ DAO (Data Access Object)
- ✅ Singleton (DatabaseManager)

### **Principios SOLID**

- ✅ Single Responsibility
- ✅ Open/Closed
- ✅ Dependency Inversion

### **Buenas Prácticas**

- ✅ Separación de responsabilidades
- ✅ Código limpio y legible
- ✅ Manejo de excepciones
- ✅ Validaciones robustas

---

## 🎉 Conclusión

El módulo de Procesar Ventas es un sistema completo y robusto que permite:

- ✅ **Registrar ventas** de manera rápida y eficiente
- ✅ **Actualizar inventario** automáticamente
- ✅ **Mantener auditoría** de todas las transacciones
- ✅ **Proporcionar datos** para reportes y análisis

El sistema está **completamente funcional** y listo para uso en producción!

---

**Desarrollado por:** Equipo de Desarrollo  
**Módulo:** Procesar Ventas  
**Versión:** 1.0.0  
**Estado:** ✅ Completado y Funcional
