# 🏗️ Estructura Modular del Software

## 📋 Arquitectura del Sistema

### **Patrón MVC (Model-View-Controller)**
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      VIEW       │    │   CONTROLLER    │    │      MODEL      │
│                 │    │                 │    │                 │
│  - LoginFrame   │◄──►│ LoginController │◄──►│    Usuario      │
│  - MainFrame    │    │ ProductoCtrl    │    │    Producto     │
│  - ProductoFrame│    │ VentaController │    │    Factura      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 📁 Estructura de Paquetes

### **1. Model Package (`com.inventario.model`)**
```java
// Entidades de datos - Solo contienen datos y lógica básica
├── Producto.java          # Entidad producto
├── Usuario.java           # Entidad usuario  
├── Factura.java           # Entidad factura
├── Venta.java             # Entidad venta
└── ItemVenta.java         # Entidad item de venta
```

**Responsabilidades:**
- Representar datos del dominio
- Validaciones básicas de datos
- Métodos de cálculo simples
- Getters y setters

**Ejemplo:**
```java
public class Producto {
    private int id;
    private String nombre;
    private BigDecimal precio;
    private int cantidad;
    
    // Getters y setters
    // Métodos de cálculo
    public BigDecimal getValorTotal() {
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }
}
```

### **2. View Package (`com.inventario.view`)**
```java
// Interfaces gráficas - Solo presentación
├── LoginFrame.java        # Ventana de login
├── MainFrame.java         # Ventana principal
├── ProductoFrame.java     # Gestión de productos
├── VentaFrame.java       # Proceso de ventas
├── FacturaFrame.java     # Generación de facturas
└── ReporteFrame.java      # Reportes del sistema
```

**Responsabilidades:**
- Presentar datos al usuario
- Capturar entrada del usuario
- Manejar eventos de interfaz
- Validaciones de formato

**Ejemplo:**
```java
public class ProductoFrame extends JFrame {
    private JTextField txtNombre;
    private JButton btnGuardar;
    
    // Solo manejo de interfaz
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
```

### **3. Controller Package (`com.inventario.controller`)**
```java
// Controladores - Coordinación entre vista y modelo
├── LoginController.java   # Controlador de login
├── ProductoController.java # Controlador de productos
├── VentaController.java   # Controlador de ventas
├── FacturaController.java # Controlador de facturas
└── ReporteController.java  # Controlador de reportes
```

**Responsabilidades:**
- Coordinar entre vista y modelo
- Manejar lógica de flujo
- Validaciones de negocio
- Transformar datos entre capas

**Ejemplo:**
```java
public class ProductoController {
    private ProductoFrame view;
    private ProductoService service;
    
    public void guardarProducto() {
        // Validar datos de la vista
        if (!validarDatos()) return;
        
        // Crear modelo
        Producto producto = crearProducto();
        
        // Llamar servicio
        if (service.guardarProducto(producto)) {
            view.mostrarMensaje("Producto guardado");
        }
    }
}
```

### **4. Service Package (`com.inventario.service`)**
```java
// Servicios - Lógica de negocio
├── UsuarioService.java    # Lógica de usuarios
├── ProductoService.java   # Lógica de productos
├── VentaService.java      # Lógica de ventas
├── FacturaService.java    # Lógica de facturas
└── ReporteService.java    # Lógica de reportes
```

**Responsabilidades:**
- Implementar reglas de negocio
- Validaciones complejas
- Cálculos de negocio
- Integración con datos

**Ejemplo:**
```java
public class ProductoService {
    public boolean guardarProducto(Producto producto) {
        // Validar reglas de negocio
        if (!validarProducto(producto)) return false;
        
        // Verificar duplicados
        if (existeProducto(producto.getNombre())) return false;
        
        // Guardar en almacenamiento
        return repository.guardar(producto);
    }
}
```

### **5. Util Package (`com.inventario.util`)**
```java
// Utilidades - Funciones auxiliares
├── Validador.java         # Validaciones comunes
├── Formateador.java       # Formateo de datos
├── Constantes.java        # Constantes del sistema
├── Configuracion.java     # Configuración
└── Logger.java            # Logging
```

**Responsabilidades:**
- Funciones auxiliares
- Validaciones reutilizables
- Constantes del sistema
- Utilidades de formato

**Ejemplo:**
```java
public class Validador {
    public static boolean validarEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
    
    public static boolean validarPrecio(String precio) {
        try {
            BigDecimal valor = new BigDecimal(precio);
            return valor.compareTo(BigDecimal.ZERO) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
```

### **6. Main Package (`com.inventario.main`)**
```java
// Punto de entrada - Inicialización
└── InventarioApp.java     # Clase principal
```

**Responsabilidades:**
- Inicializar la aplicación
- Configurar Look and Feel
- Lanzar la ventana principal

## 🔄 Flujo de Datos

### **1. Flujo de Login**
```
LoginFrame → LoginController → UsuarioService → Usuario
     ↑                              ↓
     └─── MainFrame ←───────────────┘
```

### **2. Flujo de Productos**
```
ProductoFrame → ProductoController → ProductoService → Producto
     ↑                ↓                    ↓
     └─── Actualizar ←────────────────────┘
```

### **3. Flujo de Ventas**
```
VentaFrame → VentaController → VentaService → Producto + Venta
     ↑              ↓               ↓
     └─── Factura ←─────────────────┘
```

## 🎯 Principios de Diseño

### **1. Separación de Responsabilidades**
- **Model**: Solo datos y lógica básica
- **View**: Solo presentación
- **Controller**: Solo coordinación
- **Service**: Solo lógica de negocio

### **2. Inversión de Dependencias**
```java
// ✅ BUENO: Dependencia de abstracción
public class ProductoController {
    private ProductoService service; // Depende de interfaz
    
    public ProductoController(ProductoService service) {
        this.service = service;
    }
}

// ❌ MALO: Dependencia concreta
public class ProductoController {
    private ProductoService service = new ProductoService();
}
```

### **3. Principio de Responsabilidad Única**
```java
// ✅ BUENO: Una responsabilidad por clase
public class ProductoService {
    public boolean guardarProducto(Producto producto) {
        // Solo lógica de guardar producto
    }
}

// ❌ MALO: Múltiples responsabilidades
public class ProductoService {
    public boolean guardarProducto(Producto producto) { }
    public void enviarEmail(String email) { } // No pertenece aquí
    public void generarReporte() { } // No pertenece aquí
}
```

## 🔧 Configuración Modular

### **1. Archivo de Configuración**
```java
public class Configuracion {
    public static final String NOMBRE_APLICACION = "Sistema de Inventario";
    public static final String VERSION = "1.0.0";
    public static final Color COLOR_PRIMARY = new Color(0, 102, 204);
    public static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 18);
}
```

### **2. Factory Pattern para Servicios**
```java
public class ServiceFactory {
    public static UsuarioService crearUsuarioService() {
        return new UsuarioService();
    }
    
    public static ProductoService crearProductoService() {
        return new ProductoService();
    }
}
```

### **3. Singleton para Configuración**
```java
public class ConfiguracionSingleton {
    private static ConfiguracionSingleton instance;
    private Properties propiedades;
    
    private ConfiguracionSingleton() {
        cargarPropiedades();
    }
    
    public static ConfiguracionSingleton getInstance() {
        if (instance == null) {
            instance = new ConfiguracionSingleton();
        }
        return instance;
    }
}
```

## 📊 Ventajas de la Estructura Modular

### **1. Mantenibilidad**
- Código organizado y fácil de encontrar
- Cambios aislados en módulos específicos
- Fácil debugging y testing

### **2. Escalabilidad**
- Fácil agregar nuevas funcionalidades
- Reutilización de componentes
- Extensibilidad del sistema

### **3. Colaboración**
- Diferentes desarrolladores pueden trabajar en diferentes módulos
- Menos conflictos de merge
- Especialización por área

### **4. Testing**
- Pruebas unitarias por módulo
- Mocking de dependencias
- Cobertura de código específica

## 🚀 Mejores Prácticas

### **1. Naming Conventions**
```java
// Clases: PascalCase
public class ProductoService { }

// Métodos: camelCase
public void guardarProducto() { }

// Variables: camelCase
private Producto productoSeleccionado;

// Constantes: UPPER_SNAKE_CASE
public static final String NOMBRE_APLICACION = "Inventario";
```

### **2. Documentación**
```java
/**
 * Servicio para la gestión de productos
 * Implementa la lógica de negocio para CRUD de productos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 * @since 1.0
 */
public class ProductoService {
    /**
     * Guarda un producto en el sistema
     * 
     * @param producto Producto a guardar
     * @return true si se guardó exitosamente
     * @throws IllegalArgumentException si el producto es inválido
     */
    public boolean guardarProducto(Producto producto) {
        // implementación
    }
}
```

### **3. Manejo de Errores**
```java
public class ProductoService {
    public boolean guardarProducto(Producto producto) {
        try {
            // validaciones
            if (!validarProducto(producto)) {
                throw new IllegalArgumentException("Producto inválido");
            }
            
            // guardar
            return repository.guardar(producto);
            
        } catch (Exception e) {
            logger.error("Error al guardar producto", e);
            return false;
        }
    }
}
```

### **4. Logging**
```java
public class ProductoService {
    private static final Logger logger = Logger.getLogger(ProductoService.class);
    
    public boolean guardarProducto(Producto producto) {
        logger.info("Iniciando guardado de producto: " + producto.getNombre());
        
        try {
            // lógica
            logger.info("Producto guardado exitosamente");
            return true;
        } catch (Exception e) {
            logger.error("Error al guardar producto", e);
            return false;
        }
    }
}
```

## 📈 Métricas de Modularidad

### **1. Cohesión**
- Alta cohesión dentro de cada módulo
- Responsabilidades claras y específicas
- Métodos relacionados agrupados

### **2. Acoplamiento**
- Bajo acoplamiento entre módulos
- Dependencias mínimas
- Interfaces bien definidas

### **3. Reutilización**
- Componentes reutilizables
- Servicios independientes
- Utilidades compartidas

## 🔍 Testing Modular

### **1. Pruebas Unitarias por Módulo**
```java
@Test
public void testProductoService() {
    // Arrange
    ProductoService service = new ProductoService();
    Producto producto = new Producto();
    
    // Act
    boolean resultado = service.guardarProducto(producto);
    
    // Assert
    assertTrue(resultado);
}
```

### **2. Pruebas de Integración**
```java
@Test
public void testProductoController() {
    // Test de integración entre capas
    ProductoFrame view = new ProductoFrame();
    ProductoController controller = new ProductoController(view);
    
    // Test del flujo completo
    controller.guardarProducto();
}
```

### **3. Mocks y Stubs**
```java
@Test
public void testProductoControllerConMock() {
    // Mock del servicio
    ProductoService mockService = Mockito.mock(ProductoService.class);
    when(mockService.guardarProducto(any())).thenReturn(true);
    
    // Test del controlador
    ProductoController controller = new ProductoController(mockService);
    // ...
}
```

## 📚 Recursos Adicionales

- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
- [Design Patterns](https://refactoring.guru/design-patterns)
- [Java Best Practices](https://google.github.io/styleguide/javaguide.html)

