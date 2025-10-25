# 📄 Módulo de Generar Facturas

## 📋 Descripción General

El módulo de Generar Facturas permite crear facturas profesionales a partir de las ventas registradas en el sistema. Incluye selección de ventas, captura de datos del cliente, vista previa, generación de archivo y opciones de impresión.

## 🎯 Funcionalidades

### ✅ **Funcionalidades Principales**

1. **Selección de Ventas**
   - Tabla con todas las ventas registradas
   - Checkbox para seleccionar venta
   - Información completa (ID, Fecha, Vendedor, Items, Total)

2. **Datos del Cliente**
   - Nombre del cliente
   - Documento de identidad
   - Opción de incluir/excluir IVA (19%)

3. **Vista Previa**
   - Visualización completa de la factura
   - Formato profesional
   - Detalles de productos
   - Cálculos de IVA y total

4. **Generación y Guardado**
   - Guardar factura en archivo .txt
   - Nombre único con timestamp
   - Formato estructurado y legible

5. **Simulación de Impresión**
   - Opción de "imprimir" factura
   - Genera archivo para enviar a impresora

## 🏗️ Arquitectura

### **Componentes del Módulo**

```
Modelo (Model)
└── Factura.java
    ├── Datos de la factura
    ├── Información del cliente
    ├── Cálculo de IVA
    └── Número de factura único

Vista (View)
└── FacturaFrame.java
    ├── Panel de ventas (tabla)
    ├── Panel de datos del cliente
    └── Panel de vista previa

Controlador (Controller)
└── FacturaController.java
    ├── Carga de ventas
    ├── Generación de vista previa
    ├── Guardado de factura
    └── Simulación de impresión

Servicio (Service)
└── FacturaService.java
    ├── Generación de texto
    ├── Cálculos de IVA
    ├── Guardado en archivo
    └── Validaciones
```

## 💻 Interfaz de Usuario

### **Diseño con Split Pane**

La interfaz está dividida en dos secciones principales:

```
┌─────────────────────────────────────────────────────┐
│ 📄 Generar Facturas                                 │
│ Usuario: [Nombre del Usuario]                       │
├──────────────────┬──────────────────────────────────┤
│ VENTAS           │ VISTA PREVIA                     │
│ DISPONIBLES      │                                  │
│                  │                                  │
│ [Tabla con       │ [Vista previa                    │
│  checkbox]       │  de factura]                     │
│                  │                                  │
│ DATOS CLIENTE    │                                  │
│ Nombre: [____]   │                                  │
│ Doc: [____]      │                                  │
│ [x] IVA 19%      │                                  │
│                  │                                  │
│ [🔄][📄 Generar]│ [💾 Guardar][🖨️ Imprimir][🔄]│
└──────────────────┴──────────────────────────────────┘
```

### **Colores del Sistema**

| Color | Uso | RGB |
|-------|-----|-----|
| 🔵 **Azul** | Primario | `#2980b9` |
| 🟢 **Verde** | Éxito | `#2ecc71` |
| 🔴 **Rojo** | Peligro/Cancelar | `#e74c3c` |
| 🔷 **Azul Claro** | Información | `#3498db` |

## 📄 Formato de Factura

### **Estructura Completa**

```text
═══════════════════════════════════════════════════════════════════
                         FACTURA DE VENTA
═══════════════════════════════════════════════════════════════════

Empresa:                      SISTEMA DE INVENTARIO S.A.S
NIT:                          900.123.456-7
Dirección:                    Calle 123 #45-67, Medellín
Teléfono:                     (604) 123-4567
Email:                        ventas@inventario.com

───────────────────────────────────────────────────────────────────

Número de Factura:            FACT-20251024-0001
Fecha de Emisión:             24/10/2025 15:30:00
Vendedor:                     Juan Pérez

DATOS DEL CLIENTE:
Nombre:                       Cliente General
Documento:                    N/A

───────────────────────────────────────────────────────────────────

DETALLE DE LA COMPRA:

PRODUCTO                                 CANT.   PRECIO UNIT.       SUBTOTAL
───────────────────────────────────────────────────────────────────
Laptop HP Pavilion 15 pulgadas              2    $2,500,000    $5,000,000
iPhone 13 128GB                             1    $3,500,000    $3,500,000
Camiseta Nike Dri-FIT                       5       $85,000      $425,000
───────────────────────────────────────────────────────────────────

TOTALES:

Subtotal:                                             $8,925,000
IVA (19%):                                            $1,695,750
───────────────────────────────────────────────────────────────────
TOTAL A PAGAR:                                       $10,620,750

═══════════════════════════════════════════════════════════════════

                     ¡GRACIAS POR SU COMPRA!

        Esta factura es un documento válido para efectos legales
               y tributarios según la legislación vigente.

───────────────────────────────────────────────────────────────────
                Sistema de Inventario v1.0 - 2025
═══════════════════════════════════════════════════════════════════
```

## 🔄 Flujo de Trabajo

### **Proceso de Generación**

```
1. Usuario abre "Generar Facturas"
   ↓
2. Sistema carga todas las ventas
   ↓
3. Usuario selecciona una venta (checkbox)
   ↓
4. Usuario ingresa datos del cliente
   ↓
5. Usuario decide incluir/excluir IVA
   ↓
6. Usuario hace clic en "Generar Factura"
   ↓
7. Sistema genera vista previa
   ↓
8. Usuario revisa la factura
   ↓
9. Usuario hace clic en "Guardar"
   ↓
10. Sistema crea archivo .txt
    ↓
11. Muestra confirmación con nombre del archivo
    ↓
12. Archivo guardado en carpeta raíz
```

## ✨ Características Especiales

### **Número de Factura Único**

```
Formato: FACT-YYYYMMDD-NNNN

Ejemplos:
- FACT-20251024-0001
- FACT-20251024-0002
- FACT-20251025-0001

Componentes:
- FACT = Prefijo
- 20251024 = Fecha (YYYYMMDD)
- 0001 = Número secuencial
```

### **Cálculo de IVA**

```java
// IVA del 19% sobre el subtotal
IVA = Subtotal × 0.19
Total = Subtotal + IVA

Ejemplo:
Subtotal: $8,925,000
IVA (19%): $1,695,750
Total: $10,620,750
```

### **Opción Sin IVA**

```
Si el usuario desmarca "Incluir IVA":
- No se calcula IVA
- Total = Subtotal
- Factura indica "Sin IVA"
```

## 🎨 Componentes de la Interfaz

### **Panel Izquierdo**

#### **Tabla de Ventas**

| Columna | Descripción |
|---------|-------------|
| ☑️ Seleccionar | Checkbox para elegir venta |
| ID | Número de la venta |
| Fecha | Fecha y hora de venta |
| Vendedor | Usuario que procesó la venta |
| Items | Cantidad de productos |
| Total | Monto total de la venta |

#### **Datos del Cliente**

- **Nombre** - TextField para nombre completo
- **Documento** - TextField para identificación
- **IVA** - Checkbox para incluir/excluir IVA

### **Panel Derecho - Vista Previa**

- **Área de Texto** - Muestra factura completa
- **Fuente** - Courier New (monoespaciada)
- **Formato** - Texto plano estructurado
- **Scroll** - Para facturas largas

### **Botones de Acción**

| Botón | Icono | Función | Color |
|-------|-------|---------|-------|
| Actualizar | 🔄 | Recarga ventas | Azul |
| Generar Factura | 📄 | Crea vista previa | Verde |
| Guardar | 💾 | Guarda en archivo | Verde |
| Imprimir | 🖨️ | Simula impresión | Azul |
| Limpiar | 🔄 | Limpia vista previa | Rojo |

## 📁 Archivos Generados

### **Ubicación**
```
proyecto/
├── factura_FACT_20251024_0001_20251024_153045.txt
├── factura_FACT_20251024_0002_20251024_154230.txt
└── ...
```

### **Nombre del Archivo**
```
factura_[NUMERO]_[TIMESTAMP].txt

Ejemplo:
factura_FACT_20251024_0001_20251024_153045.txt

Componentes:
- factura_ = Prefijo
- FACT_20251024_0001 = Número de factura
- 20251024_153045 = Fecha y hora de generación
- .txt = Extensión
```

## 🔐 Validaciones Implementadas

### **Validaciones de Usuario**

1. ✅ **Venta Seleccionada**
   ```
   Si no hay venta seleccionada:
     → "Por favor, seleccione una venta"
   ```

2. ✅ **Nombre del Cliente**
   ```
   Si nombre está vacío:
     → "Ingrese el nombre del cliente"
   ```

3. ✅ **Confirmación de Guardado**
   ```
   Antes de guardar:
     → "¿Desea guardar esta factura?"
   ```

4. ✅ **Confirmación de Impresión**
   ```
   Antes de imprimir:
     → "¿Desea imprimir esta factura?"
   ```

### **Validaciones de Datos**

```java
// El servicio valida:
- Factura no nula
- Venta asociada existe
- Cliente tiene nombre
- Total es mayor a cero
```

## 🔧 Métodos Principales

### **FacturaService.java**

```java
// Generación de texto
String generarFacturaTexto(Factura factura, boolean incluirIVA)

// Guardado en archivo
String guardarFacturaArchivo(Factura factura, boolean incluirIVA)

// Resumen corto
String generarResumen(Factura factura)

// Validación
boolean validarFactura(Factura factura)

// Cálculo con descuento
BigDecimal calcularTotalConDescuento(Factura factura, BigDecimal porcentaje)
```

### **FacturaController.java**

```java
// Carga ventas disponibles
void cargarVentas()

// Genera vista previa
void generarVistaPrevia()

// Guarda factura
void guardarFactura()

// Simula impresión
void imprimirFactura()

// Limpia vista previa
void limpiarVistaPrevia()
```

## 💡 Casos de Uso

### **Caso 1: Factura Simple**

```
1. Cliente: "Cliente General"
2. Documento: "N/A"
3. IVA: ✅ Incluido
4. Seleccionar Venta #1
5. Generar Factura
6. Guardar

Resultado:
✅ Archivo: factura_FACT_20251024_0001_20251024_153000.txt
✅ Total con IVA: $10,620,750
```

### **Caso 2: Factura Sin IVA**

```
1. Cliente: "Empresa Exenta Ltda"
2. Documento: "890.123.456-1"
3. IVA: ❌ No incluido
4. Seleccionar Venta #2
5. Generar Factura
6. Guardar

Resultado:
✅ Archivo: factura_FACT_20251024_0002_20251024_154500.txt
✅ Total sin IVA: $8,925,000
```

### **Caso 3: Vista Previa y Edición**

```
1. Seleccionar venta
2. Generar Factura
3. Revisar vista previa
4. Detectar error en nombre
5. Limpiar
6. Corregir nombre
7. Generar nuevamente
8. Guardar

Resultado:
✅ Factura correcta guardada
```

## 🎯 Características de UX

### **Experiencia de Usuario**

1. **Split Pane Ajustable**
   - Usuario puede ajustar el tamaño de los paneles
   - Vista flexible según preferencia

2. **Vista Previa Instantánea**
   - Factura se genera en tiempo real
   - Usuario ve resultado antes de guardar

3. **Feedback Visual**
   - Botones cambian de color al pasar mouse
   - Mensajes claros de confirmación
   - Botones deshabilitados cuando no aplican

4. **Navegación Intuitiva**
   - Flujo lineal claro
   - Botones con iconos descriptivos
   - Validaciones amigables

## 📊 Información de la Empresa

### **Datos Configurables**

```java
// En FacturaService.java
NOMBRE_EMPRESA = "SISTEMA DE INVENTARIO S.A.S"
NIT_EMPRESA = "900.123.456-7"
DIRECCION_EMPRESA = "Calle 123 #45-67, Medellín"
TELEFONO_EMPRESA = "(604) 123-4567"
EMAIL_EMPRESA = "ventas@inventario.com"
```

### **Personalización**

Para cambiar los datos de la empresa:
1. Abrir `FacturaService.java`
2. Modificar las constantes al inicio
3. Recompilar el proyecto

## 🚀 Cómo Usar el Módulo

### **Paso 1: Acceder**
```
Menú Principal → Generar Facturas
O
Botón "📄 Facturas" en panel principal
```

### **Paso 2: Seleccionar Venta**
```
1. Ver tabla de ventas
2. Marcar checkbox de la venta deseada
3. Solo una venta puede estar seleccionada
```

### **Paso 3: Ingresar Datos del Cliente**
```
1. Escribir nombre del cliente
2. Escribir documento (opcional)
3. Marcar/desmarcar checkbox de IVA
```

### **Paso 4: Generar Vista Previa**
```
1. Clic en "📄 Generar Factura"
2. Ver vista previa en panel derecho
3. Revisar todos los datos
```

### **Paso 5: Guardar**
```
1. Clic en "💾 Guardar"
2. Confirmar en diálogo
3. Ver mensaje con nombre del archivo
4. Listo! Archivo en carpeta raíz
```

## ⚠️ Consideraciones Importantes

### **Limitaciones Actuales**

1. **Una Venta a la Vez**
   - Solo se puede facturar una venta por vez
   - Para múltiples facturas, repetir proceso

2. **Formato de Texto**
   - Facturas se generan en .txt
   - No incluye formato PDF (futuro)

3. **Impresión Simulada**
   - "Imprimir" solo guarda archivo
   - No envía a impresora real

4. **Sin Edición Posterior**
   - Factura guardada no es editable
   - Debe generar nueva si hay error

### **Buenas Prácticas**

1. ✅ **Revisar Vista Previa**
   - Siempre revisar antes de guardar
   - Verificar nombre del cliente
   - Confirmar cálculos

2. ✅ **Datos del Cliente**
   - Usar nombres completos
   - Incluir documento si es empresa
   - Verificar correctamente

3. ✅ **Archivos**
   - No eliminar archivos generados
   - Mantener para auditoría
   - Respaldar periódicamente

## 🔐 Permisos y Acceso

### **Roles con Acceso**

| Rol | Puede Generar Facturas |
|-----|------------------------|
| ✅ SUPER_ADMIN | Sí |
| ✅ ADMIN | Sí |
| ✅ VENDEDOR | Sí |
| ❌ CONSULTA | No |

### **Restricciones**

- Usuario debe estar autenticado
- Debe haber ventas registradas
- Solo facturas de ventas propias (o todas si es admin)

## 📈 Mejoras Futuras

### **Funcionalidades Propuestas**

1. ⏳ **PDF Generation**
   - Facturas en formato PDF
   - Con logo y estilos
   - Firma digital

2. ⏳ **Envío por Email**
   - Enviar factura al cliente
   - Adjuntar PDF
   - Confirmación automática

3. ⏳ **Impresión Real**
   - Integración con impresoras
   - Configuración de impresora
   - Vista previa de impresión

4. ⏳ **Descuentos**
   - Aplicar descuentos
   - Porcentaje o monto fijo
   - Justificación de descuento

5. ⏳ **Notas de Crédito**
   - Anular facturas
   - Generar notas de crédito
   - Devoluciones

6. ⏳ **Historial de Facturas**
   - Ver facturas generadas
   - Buscar por número
   - Reimprimir facturas

## ✅ Testing

### **Casos de Prueba**

1. ✅ Generar factura con IVA
2. ✅ Generar factura sin IVA
3. ✅ Intentar generar sin seleccionar venta
4. ✅ Intentar generar sin nombre de cliente
5. ✅ Guardar factura exitosamente
6. ✅ Simular impresión
7. ✅ Limpiar vista previa
8. ✅ Verificar formato de archivo

## 🎓 Conceptos Aplicados

### **Patrones de Diseño**

- ✅ MVC (Modelo-Vista-Controlador)
- ✅ Service Layer
- ✅ Template Method (Generación de factura)

### **Programación Orientada a Objetos**

- ✅ Encapsulación
- ✅ Composición (Factura tiene Venta)
- ✅ Métodos auxiliares

### **Java Swing**

- ✅ JSplitPane - División de paneles
- ✅ JTextArea - Vista previa
- ✅ JCheckBox - Selección de IVA
- ✅ DefaultTableModel - Tabla dinámica

## 🎉 Beneficios

### **Para el Negocio**

- ✅ Facturación rápida y profesional
- ✅ Cumplimiento normativo
- ✅ Auditoría completa
- ✅ Respaldo documental

### **Para el Usuario**

- ✅ Proceso simple e intuitivo
- ✅ Vista previa antes de guardar
- ✅ Formato profesional
- ✅ Archivos organizados

---

## 🎯 Conclusión

El módulo de **Generar Facturas** proporciona:

- ✅ **Facturación profesional** con formato estándar
- ✅ **Cálculo automático** de IVA y totales
- ✅ **Vista previa** para verificación
- ✅ **Archivos** listos para imprimir
- ✅ **Cumplimiento** normativo

**¡Todo lo necesario para una facturación eficiente!** 📄

---

**Desarrollado por:** Equipo de Desarrollo  
**Módulo:** Generar Facturas  
**Versión:** 1.0.0  
**Estado:** ✅ Completado y Funcional
