# 🚀 Guía Rápida - Módulo de Procesar Ventas

## ⚡ Inicio Rápido

### **1. Acceder al Sistema**
```
👤 Usuario: vendedor
🔐 Contraseña: vendedor123
📋 Tipo: Vendedor
```

### **2. Abrir Procesar Ventas**
- **Opción 1:** Menú Principal → "Ventas" → "Procesar Venta"
- **Opción 2:** Clic en botón "🛒 Procesar Ventas"

### **3. Realizar una Venta**

#### **Paso a Paso:**

```
1️⃣ Seleccionar Producto
   └─ Usar el ComboBox para elegir producto
   └─ Ver información: Código - Nombre (Stock: X)

2️⃣ Ajustar Cantidad
   └─ Usar spinner (flechas arriba/abajo)
   └─ O escribir número directamente

3️⃣ Agregar al Carrito
   └─ Clic en "➕ Agregar al Carrito"
   └─ El producto aparece en la tabla

4️⃣ Repetir para Más Productos
   └─ Agregar todos los productos necesarios
   └─ Ver total actualizándose automáticamente

5️⃣ Procesar Venta
   └─ Clic en "💰 Procesar Venta"
   └─ Confirmar en el diálogo
   └─ ¡Listo! Venta registrada
```

## 📊 Ejemplo Práctico

### **Venta de Ejemplo:**

```
Cliente quiere comprar:
- 2 Laptops HP
- 1 iPhone 13
- 5 Camisetas Nike

Proceso:
1. Seleccionar "PROD001 - Laptop HP (Stock: 5)"
2. Cambiar cantidad a 2
3. Clic en "Agregar al Carrito"
4. Seleccionar "PROD002 - iPhone 13 (Stock: 3)"
5. Dejar cantidad en 1
6. Clic en "Agregar al Carrito"
7. Seleccionar "PROD003 - Camiseta Nike (Stock: 20)"
8. Cambiar cantidad a 5
9. Clic en "Agregar al Carrito"

Carrito muestra:
┌─────────────────────┬──────────────┬──────────┬──────────────┐
│ Producto            │ Precio Unit. │ Cantidad │ Subtotal     │
├─────────────────────┼──────────────┼──────────┼──────────────┤
│ PROD001 - Laptop HP │ $2,500,000   │    2     │ $5,000,000   │
│ PROD002 - iPhone 13 │ $3,500,000   │    1     │ $3,500,000   │
│ PROD003 - Camiseta  │ $   85,000   │    5     │ $  425,000   │
└─────────────────────┴──────────────┴──────────┴──────────────┘

TOTAL: $8,925,000

10. Clic en "Procesar Venta"
11. Confirmar
12. Mensaje: "✅ ¡Venta procesada exitosamente! Venta #1"
```

## 🎯 Funciones de los Botones

| Botón | Icono | Función |
|-------|-------|---------|
| **Agregar al Carrito** | ➕ | Agrega producto seleccionado |
| **Quitar Seleccionado** | ➖ | Quita item del carrito |
| **Limpiar Carrito** | 🔄 | Borra todos los items |
| **Procesar Venta** | 💰 | Finaliza y guarda venta |
| **Cancelar** | ❌ | Cierra ventana sin procesar |

## ⚠️ Errores Comunes y Soluciones

### **Error: "Stock insuficiente"**
```
Problema: Cantidad solicitada > stock disponible
Solución: Reducir la cantidad o elegir otro producto
```

### **Error: "Por favor, seleccione un producto"**
```
Problema: No hay producto seleccionado en el combo
Solución: Seleccionar un producto de la lista
```

### **Error: "El carrito está vacío"**
```
Problema: Intentó procesar venta sin productos
Solución: Agregar al menos un producto al carrito
```

### **Error: "No hay productos disponibles"**
```
Problema: No hay productos con stock en el inventario
Solución: Ir a "Gestión de Productos" y agregar/reponer
```

## 💡 Tips y Trucos

### **Tip 1: Agregar Mismo Producto Múltiples Veces**
```
✅ PERMITIDO: El sistema suma las cantidades automáticamente
Ejemplo:
  - Agregar Laptop × 2
  - Agregar Laptop × 1
  → Resultado: Laptop × 3 en el carrito
```

### **Tip 2: Revisar Antes de Procesar**
```
✅ RECOMENDADO: Verificar tabla antes de confirmar
  - Productos correctos
  - Cantidades correctas
  - Total correcto
```

### **Tip 3: Quitar Item Específico**
```
✅ CÓMO:
  1. Clic en la fila del producto en la tabla
  2. Clic en "Quitar Seleccionado"
  3. Confirmar
```

### **Tip 4: Limpiar Todo Rápidamente**
```
✅ CÓMO:
  1. Clic en "Limpiar Carrito"
  2. Confirmar
  → Todo el carrito se borra
```

## 📱 Atajos de Teclado

| Tecla | Acción |
|-------|--------|
| **Tab** | Navegar entre campos |
| **Enter** | Confirmar en diálogos |
| **Esc** | Cancelar diálogos |
| **↑/↓** | Cambiar cantidad en spinner |

## 🔍 Información de la Pantalla

### **Secciones de la Interfaz:**

```
┌─────────────────────────────────────────┐
│  🛒 Procesar Venta                      │
│  Vendedor: [Nombre del Usuario]         │
├─────────────────────────────────────────┤
│  [Seleccionar Producto]                 │
│  Producto: [ComboBox] Cantidad: [1]     │
│  [➕ Agregar al Carrito]                │
├─────────────────────────────────────────┤
│  Carrito de Compras                     │
│  ┌─────────────────────────────────┐   │
│  │ Producto │ Precio │ Cant │ Sub  │   │
│  ├──────────┼────────┼──────┼──────┤   │
│  │ ...      │ ...    │ ...  │ ...  │   │
│  └─────────────────────────────────┘   │
│  [➖ Quitar] [🔄 Limpiar]              │
├─────────────────────────────────────────┤
│  📊 Resumen de Venta                    │
│  Items diferentes: 3                    │
│  Total de unidades: 8                   │
│                                          │
│  TOTAL: $8,925,000                      │
├─────────────────────────────────────────┤
│  [💰 Procesar Venta] [❌ Cancelar]     │
└─────────────────────────────────────────┘
```

## 🎨 Colores de los Botones

| Color | Significado |
|-------|-------------|
| 🟢 **Verde** | Acción positiva (Agregar) |
| 🔵 **Azul** | Acción principal (Procesar) |
| 🔴 **Rojo** | Acción destructiva (Quitar, Cancelar) |
| 🟡 **Amarillo** | Acción de advertencia (Limpiar) |

## 📋 Checklist de Venta

Antes de procesar, verificar:

- [ ] ¿Todos los productos son correctos?
- [ ] ¿Las cantidades son correctas?
- [ ] ¿El total es correcto?
- [ ] ¿El cliente está de acuerdo?

Si todo está ✅, entonces:
- [ ] Clic en "Procesar Venta"
- [ ] Confirmar
- [ ] Esperar mensaje de éxito

## 🆘 Soporte

### **Si algo sale mal:**

1. **Verificar stock** en "Gestión de Productos"
2. **Revisar productos** en la lista
3. **Cerrar y reabrir** la ventana de ventas
4. **Reiniciar** la aplicación si es necesario

### **Contactar soporte si:**
- ❌ La venta se procesó pero el stock no cambió
- ❌ Aparece un error de base de datos
- ❌ Los productos no se cargan

## 🎓 Permisos por Rol

| Rol | Puede Vender |
|-----|--------------|
| ✅ **SUPER_ADMIN** | Sí |
| ✅ **ADMIN** | Sí |
| ✅ **VENDEDOR** | Sí |
| ❌ **CONSULTA** | No |

## 🚀 Comenzar Ahora

### **¿Listo para tu primera venta?**

```
1. Login como vendedor
2. Abrir "Procesar Ventas"
3. Seleccionar producto de prueba
4. Cantidad: 1
5. Agregar al carrito
6. Procesar venta
7. ¡Felicidades! Primera venta completada 🎉
```

---

## 📞 Ayuda Rápida

**¿Preguntas frecuentes?**

**P: ¿Puedo cancelar una venta procesada?**  
R: No. Una vez procesada, la venta queda registrada para auditoría.

**P: ¿Puedo editar cantidades en el carrito?**  
R: No directamente. Quita el item y agrégalo de nuevo con la cantidad correcta.

**P: ¿Qué pasa si me equivoco?**  
R: Antes de procesar, puedes limpiar el carrito o quitar items. Después de procesar, no se puede deshacer.

**P: ¿Puedo ver las ventas anteriores?**  
R: Sí, en el módulo de "Reportes" (próximamente).

**P: ¿Se actualiza el inventario automáticamente?**  
R: ¡Sí! Al procesar la venta, el stock se reduce automáticamente.

---

**¡Listo para vender!** 🚀

Esta guía te ayudará a procesar ventas rápida y eficientemente.

**Recuerda:** Siempre verifica antes de procesar. ✅

---

**Versión:** 1.0  
**Última actualización:** 24 de Octubre, 2025
