# 🎨 Guía de Diseño Profesional - Sistema de Inventario 

##  Índice
1. [Paleta de Colores](#paleta-de-colores)
2. [Tipografía](#tipografía)
3. [Componentes Rediseñados](#componentes-rediseñados)
4. [Sistema de Navegación](#sistema-de-navegación)
5. [Principios de Diseño](#principios-de-diseño)
6. [Guía de Uso](#guía-de-uso)

---

##  🎨 Paleta de Colores

### Colores Principales

```java
// Colores corporativos consistentes en todo el sistema
PRIMARY_COLOR    = #2980B9  // Azul Profesional
SECONDARY_COLOR  = #34495E  // Gris Oscuro
SUCCESS_COLOR    = #2ECC71  // Verde Éxito
WARNING_COLOR    = #F1C40F  // Amarillo Advertencia
DANGER_COLOR     = #E74C3C  // Rojo Peligro
INFO_COLOR       = #3498DB  // Azul Información
BACKGROUND_COLOR = #ECF0F1  // Gris Claro (Fondo)
CARD_COLOR       = #FFFFFF  // Blanco (Cards)
```

### Aplicación por Componente

| Componente | Color | Uso |
|------------|-------|-----|
| **Botones Primarios** | PRIMARY_COLOR (#2980B9) | Acciones principales, actualizar |
| **Botones Éxito** | SUCCESS_COLOR (#2ECC71) | Guardar, procesar, confirmar |
| **Botones Advertencia** | WARNING_COLOR (#F1C40F) | Limpiar, resetear |
| **Botones Peligro** | DANGER_COLOR (#E74C3C) | Eliminar, cancelar |
| **Botones Navegación** | SECONDARY_COLOR (#34495E) | Regresar, volver |
| **Fondos** | BACKGROUND_COLOR (#ECF0F1) | Fondo general de ventanas |
| **Cards/Paneles** | CARD_COLOR (#FFFFFF) | Formularios, paneles |
| **Headers de Tabla** | PRIMARY_COLOR (#2980B9) | Encabezados de tablas |

---

## Tipografía

### Fuente Principal: **Segoe UI**

```java
// Jerarquía tipográfica consistente
TITLE_FONT    = Segoe UI Bold 24px    // Títulos principales
SUBTITLE_FONT = Segoe UI Plain 16px   // Subtítulos
LABEL_FONT    = Segoe UI Bold 13px    // Labels de formularios
BUTTON_FONT   = Segoe UI Bold 12px    // Texto en botones
INPUT_FONT    = Segoe UI Plain 12px   // Campos de entrada
```

### Aplicación

- **Títulos de Ventana**: 24px Bold
- **Subtítulos**: 16px Plain
- **Labels**: 13px Bold
- **Botones**: 12px Bold
- **Inputs/Tables**: 12px Plain

---

##  Componentes Rediseñados

### 1. MainFrame - Menú Principal

**Características:**
- Layout con BorderLayout
- Cards individuales para cada botón
- Botones grandes (280x120px) con iconos emoji
- Header con título y subtítulo
- Footer informativo
- Menu bar oscuro

**Estructura Visual:**
```
┌─────────────────────────────────────────────────┐
│ [Menú Bar - Gris Oscuro]                       │
├─────────────────────────────────────────────────┤
│           Sistema de Inventario                 │
│     Bienvenido, Usuario | Rol                   │
├─────────────────────────────────────────────────┤
│                                                  │
│  ┌───────────────┐    ┌───────────────┐        │
│  │  📦 Gestión   │    │  🛒 Procesar  │        │
│  │  de Productos │    │     Ventas     │        │
│  └───────────────┘    └───────────────┘        │
│                                                  │
│  ┌───────────────┐    ┌───────────────┐        │
│  │  📄 Generar   │    │  📊 Reportes   │        │
│  │   Facturas    │    │                │        │
│  └───────────────┘    └───────────────┘        │
│                                                  │
├─────────────────────────────────────────────────┤
│     Sistema de Inventario v2.0 | © 2025        │
└─────────────────────────────────────────────────┘
```

### 2. ProductoFrame - Gestión de Productos

**Diseño:**
- Layout dividido: Formulario (izquierda) | Tabla (derecha)
- Formulario en card blanco con campos verticales
- Botones de acción con colores semánticos
- Tabla con header azul
- Botón "Regresar" en el header

**Botones:**
- **Guardar** (Verde)
- **Actualizar** (Azul)
- **Eliminar** (Rojo)
- **Limpiar** (Amarillo)
- **Regresar al Menú** (Gris)

**Estructura:**
```
┌──────────────────────────────────────────────────┐
│ 📦 Gestión de Productos    [⬅️ Regresar al Menú]│
├──────────────┬───────────────────────────────────┤
│ FORMULARIO   │    TABLA DE PRODUCTOS             │
│              │                                   │
│ Código:      │  [Tabla con productos]            │
│ [________]   │                                   │
│              │  Código | Nombre | Desc...        │
│ Nombre:      │  --------|--------|-------        │
│ [________]   │  P001   | Mouse  | Mouse...       │
│              │  P002   | Teclado| Teclado...     │
│ ...          │  ...                              │
│              │                                   │
│ [💾 Guardar] │                                   │
│ [✏️ Actualizar]                                  │
│ [🗑️ Eliminar]                                   │
│ [🔄 Limpiar] │                                   │
└──────────────┴───────────────────────────────────┘
```

### 3. VentaFrame - Procesar Ventas

**Características:**
- Panel de selección de productos
- Tabla de carrito de compras
- Resumen de venta con total
- Botones de acción + Regresar

**Botones:**
-  **Agregar al Carrito** (Verde)
-  **Quitar Seleccionado** (Rojo)
-  **Procesar Venta** (Azul)
-  **Limpiar Carrito** (Amarillo)
-  **Regresar al Menú** (Gris)

### 4. FacturaFrame - Generar Facturas

**Características:**
- Split pane: Lista de ventas | Vista previa
- Formulario de datos del cliente
- Checkbox para incluir IVA
- Vista previa de factura en tiempo real

**Botones:**
- **Guardar** (Verde)
- **Imprimir** (Azul)
- **Limpiar** (Rojo)
- **Menú** (Gris)

### 5. ReportesFrame - Informes

**Características:**
- Tabs con diferentes tipos de reportes
- Dashboard con estadísticas
- Tablas de datos
- Botón regresar en el header superior

**Tabs:**
-  Dashboard
-  Ventas
-  Productos
-  Inventario
-  Top Ventas

---

## Sistema de Navegación

### Flujo de Navegación Mejorado

```
┌─────────────┐
│   Login     │
└──────┬──────┘
       │
       v
┌─────────────┐
│ Menú Princ. │◄──────────┐
└──────┬──────┘           │
       │                   │
       ├─────────┬─────────┼─────────┬────────┐
       v         v         │         v        v
   ┌────────┐ ┌────────┐  │   ┌────────┐ ┌────────┐
   │Producto│ │ Ventas │  │   │Facturas│ │Reportes│
   └────┬───┘ └────┬───┘  │   └────┬───┘ └────┬───┘
        │          │       │        │          │
        └──────────┴───────┴────────┴──────────┘
             [⬅️ Botón Regresar]
```

### Características de Navegación

1. **Botón Regresar en Todas las Ventanas**
   - Ubicación: Header superior (derecha)
   - Color: Gris oscuro (SECONDARY_COLOR)
   - Icono: ⬅️
   - Acción: Cierra ventana actual y muestra menú principal

2. **Cierre Controlado**
   - Al cerrar (X): No sale del sistema, vuelve al menú
   - Evita ventanas flotantes
   - Experiencia de usuario consistente

3. **Flujo Sin Interrupciones**
   - Siempre hay forma de volver al menú
   - No se pierden las ventanas
   - Navegación intuitiva

---

## Principios de Diseño

### 1. Consistencia Visual

- **Colores**: Paleta única en todo el sistema
- **Tipografía**: Segoe UI en todas las ventanas
- **Espaciado**: Márgenes y padding consistentes
- **Botones**: Mismo estilo en todos los módulos

### 2. Jerarquía Visual

```
Título Principal (24px Bold, Gris Oscuro)
    └── Subtítulo (16px Plain, Azul)
        └── Secciones (13px Bold, Negro)
            └── Contenido (12px Plain, Negro)
```

### 3. Feedback Visual

- **Hover**: Los botones se oscurecen al pasar el mouse
- **Colores Semánticos**: Verde = Éxito, Rojo = Peligro
- **Cursor**: Cambia a "mano" en elementos clickeables
- **Iconos**: Emoji descriptivos para rápida identificación

### 4. Espaciado y Respiración

- Padding interno: 15-25px
- Margen entre elementos: 10-15px
- Separación de secciones: 20px
- Bordes sutiles: 1-2px

### 5. Accesibilidad

- Contraste adecuado entre texto y fondo
- Botones suficientemente grandes (min 32px altura)
- Labels descriptivos
- Tooltips donde sea necesario

---

## Responsive y Adaptabilidad

### Tamaños de Ventana

| Ventana | Ancho | Alto | Redimensionable |
|---------|-------|------|-----------------|
| MainFrame | 900px | 700px |  Sí |
| ProductoFrame | 1200px | 700px |  Sí |
| VentaFrame | 950px | 700px |  Sí |
| FacturaFrame | 1200px | 700px |  Sí |
| ReportesFrame | 1100px | 700px |  Sí |

### Adaptación de Contenido

- Scroll automático en tablas
- Paneles redimensionables (Split Pane)
- Layout flexible con BorderLayout y BoxLayout

---

## Guía de Implementación

### Estructura de un Botón Moderno

```java
private JButton crearBotonModerno(String texto, Color color) {
    JButton boton = new JButton(texto);
    boton.setFont(BUTTON_FONT);
    boton.setBackground(color);
    boton.setForeground(Color.WHITE);
    boton.setFocusPainted(false);
    boton.setBorderPainted(false);
    boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    
    // Efecto hover
    boton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            boton.setBackground(color.darker());
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            boton.setBackground(color);
        }
    });
    
    return boton;
}
```

### Estructura de un Panel Card

```java
JPanel card = new JPanel(new BorderLayout());
card.setBackground(CARD_COLOR);
card.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
    BorderFactory.createEmptyBorder(20, 20, 20, 20)
));
```

### Header con Título y Botón Regresar

```java
JPanel panelSuperior = new JPanel(new BorderLayout());
panelSuperior.setBackground(CARD_COLOR);
panelSuperior.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY_COLOR),
    BorderFactory.createEmptyBorder(20, 20, 20, 20)
));

JLabel lblTitulo = new JLabel("📦 Título del Módulo");
lblTitulo.setFont(TITLE_FONT);
lblTitulo.setForeground(SECONDARY_COLOR);

panelSuperior.add(lblTitulo, BorderLayout.WEST);
panelSuperior.add(btnRegresar, BorderLayout.EAST);
```

---

##  Ejemplos Visuales

### Botones por Tipo de Acción

```
[ Guardar]     Verde  - Confirmar creación/guardado
[ Actualizar]  Azul   - Modificar existente
[ Eliminar]    Rojo   - Borrar registro
[ Limpiar]     Amarillo - Resetear formulario
[ Regresar]    Gris   - Volver al menú
[ Agregar]     Verde  - Añadir a lista
[ Quitar]      Rojo   - Remover de lista
[ Procesar]    Azul   - Ejecutar acción principal
[ Reporte]     Amarillo - Ver estadísticas
[ Guardar]     Verde  - Guardar archivo
[ Imprimir]    Azul   - Imprimir documento
```

### Tabla con Header Azul

```
┌────────────────────────────────────────┐
│ [Código] [Nombre] [Descripción] [...]  │ ← Header Azul
├────────────────────────────────────────┤
│  P001     Mouse    Mouse USB...        │
│  P002     Teclado  Teclado inal...     │
│  P003     Monitor  Monitor 24"...      │
│  ...                                    │
└────────────────────────────────────────┘
```

---

## Checklist de Diseño

###  Para Cada Ventana Nueva

-  Usa la paleta de colores definida
-  Aplica la tipografía Segoe UI
-  Incluye botón "Regresar al Menú"
-  Configura cierre controlado (vuelve al menú)
-  Botones con colores semánticos
-  Efectos hover en botones
-  Padding y márgenes consistentes
-  Headers de tabla en azul
-  Iconos emoji en botones
-  Cursor tipo "mano" en clickeables

###  Para Cada Botón

-  Usa `crearBotonModerno()` o método equivalente
-  Color según función (verde/azul/rojo/amarillo/gris)
-  Icono emoji descriptivo
-  Efecto hover implementado
-  Cursor: HAND_CURSOR
-  FocusPainted: false
-  BorderPainted: false

---

##  Ventajas del Nuevo Diseño

### Para el Usuario

- **Navegación Clara**: Botones grandes y visibles
- **Identificación Rápida**: Iconos y colores semánticos
- **Sin Confusiones**: Siempre sabe cómo volver
- **Profesional**: Aspecto moderno y corporativo
- **Consistente**: Misma experiencia en todo el sistema

### Para el Desarrollador

- **Mantenible**: Constantes centralizadas
- **Escalable**: Fácil agregar nuevos módulos
- **Reutilizable**: Métodos helper para componentes
- **Documentado**: Guía clara de implementación
- **Estándar**: Sigue principios de diseño UX

---

## Soporte y Mantenimiento

### Modificar Colores

Para cambiar la paleta de colores, edita las constantes al inicio de cada Frame:

```java
private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
// etc...
```

### Agregar Nuevo Módulo

1. Crear nueva clase que extienda `JFrame`
2. Incluir las constantes de colores y fuentes
3. Agregar `MainFrame mainFrame` como parámetro del constructor
4. Implementar botón "Regresar" que llame a `mainFrame.mostrar()`
5. Usar `crearBotonModerno()` para todos los botones
6. Aplicar la paleta de colores consistente

### Personalización

El diseño es modular y permite personalizaciones:

- **Colores**: Cambiar constantes de color
- **Fuentes**: Modificar constantes de Font
- **Iconos**: Cambiar emojis por iconos PNG/SVG
- **Tamaños**: Ajustar dimensiones de ventanas
- **Layouts**: Reorganizar paneles según necesidad

---

##  Referencias

- **Paleta Inspirada en**: Material Design y Flat UI
- **Tipografía**: Segoe UI (Microsoft)
- **Principios**: Material Design Guidelines
- **Accesibilidad**: WCAG 2.1 Level AA

---

##  Conclusión

Este diseño profesional proporciona:

-  Experiencia de usuario consistente y moderna
-  Navegación intuitiva sin confusiones
-  Aspecto profesional y corporativo
-  Código mantenible y escalable
-  Base sólida para futuras mejoras

**El sistema ahora cuenta con un diseño de nivel profesional, listo para producción.**





