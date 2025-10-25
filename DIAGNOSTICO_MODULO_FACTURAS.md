# 🔍 Diagnóstico: Módulo de Facturas

**Fecha:** 25 de octubre de 2025  
**Problema:** Factura se genera pero sin valores correctos (sin IVA, sin datos)  
**Módulo:** Generar Facturas

---

## 🐛 **Problema Reportado**

El usuario reporta que:
1. ✅ La factura se genera (aparece)
2. ❌ No muestra el descuento con IVA o sin IVA
3. ❌ No muestra los valores asignados correctamente

---

## 🛠️ **Logging Implementado**

He agregado **logging exhaustivo** en `FacturaController.generarVistaPrevia()` para diagnosticar el problema.

### **Logs que Verás:**

```
📄 ===== GENERANDO FACTURA =====
✅ Venta seleccionada ID: [número]

✅ Venta cargada:
   ID: [número]
   Usuario: [nombre]
   Total: [monto]
   Fecha: [fecha]
   Items: [cantidad]

📦 Items de la venta:
   1. [Producto]
      Cantidad: [cantidad]
      Precio unitario: [precio]
      Subtotal: [subtotal]
   2. [Producto]
      ...

👤 Datos del cliente:
   Nombre: [nombre]
   Documento: [documento]
   Incluir IVA: [true/false]

📝 Creando factura...
✅ Factura creada:
   Número: [número]
   Subtotal: [monto]
   IVA: [monto]
   Total: [monto]

🖨️ Generando texto de factura...
✅ Texto generado ([número] caracteres)

✅✅✅ FACTURA GENERADA EXITOSAMENTE ✅✅✅
```

---

## 🎯 **Cómo Diagnosticar**

### **Paso 1: Ejecutar desde IDE**

Ejecuta la aplicación desde tu IDE para ver los logs en la consola.

### **Paso 2: Generar Factura**

1. Inicia sesión (admin/admin123)
2. Ve a **"Generar Facturas"**
3. Selecciona una venta (checkbox)
4. Ingresa datos del cliente:
   - Nombre: Juan Pérez
   - Documento: 123456789
5. Marca o desmarca **"Incluir IVA (19%)"**
6. Click **"📄 Generar Factura"**

### **Paso 3: Revisar Logs**

Mira la consola y busca:

#### **✅ Si Todo Funciona:**
```
✅ Venta cargada:
   Items: 2  ← Debe ser > 0

📦 Items de la venta:
   1. Laptop HP
      Cantidad: 1
      Precio unitario: 2500000
      Subtotal: 2500000
   2. iPhone 13
      Cantidad: 1
      Precio unitario: 3500000
      Subtotal: 3500000

✅ Factura creada:
   Subtotal: 6000000.00
   IVA: 1140000.00  ← Si incluir IVA está marcado
   Total: 7140000.00
```

#### **❌ Si Hay Problema:**
```
✅ Venta cargada:
   Items: 0  ← ❌ PROBLEMA: No hay items

⚠️ La venta NO tiene items cargados
```

O:

```
✅ Venta cargada:
   Items: NULL  ← ❌ PROBLEMA: Items es NULL
```

---

## 🔍 **Posibles Causas**

### **Causa 1: Venta Sin Items**

La venta fue procesada pero los items no se guardaron en la BD.

**Solución:**
- Verifica que las ventas se estén procesando correctamente
- Revisa la tabla `items_venta` en la BD

### **Causa 2: Items No Se Cargan**

El método `VentaDAO.cargarItemsVenta()` no está funcionando.

**Solución:**
- Revisa los logs del VentaDAO
- Verifica la estructura de la tabla `items_venta`

### **Causa 3: Checkbox IVA No Funciona**

El checkbox de IVA no está enviando el valor correcto.

**Solución:**
- Revisa los logs: `Incluir IVA: [true/false]`
- Si siempre es `false`, hay problema en la vista

---

## 📊 **Formato de Factura Esperado**

### **Sin IVA:**

```
═══════════════════════════════════════════════════════════════════
                         FACTURA DE VENTA
═══════════════════════════════════════════════════════════════════

Empresa:                      SISTEMA DE INVENTARIO S.A.S
NIT:                          900.123.456-7
Dirección:                    Calle 123 #45-67, Medellín
Teléfono:                     (604) 123-4567
Email:                        ventas@inventario.com

───────────────────────────────────────────────────────────────────

Número de Factura:            FACT-20251025-001
Fecha de Emisión:             25/10/2025 14:30:00
Vendedor:                     Admin Usuario

DATOS DEL CLIENTE:
Nombre:                       Juan Pérez
Documento:                    123456789

───────────────────────────────────────────────────────────────────

DETALLE DE LA COMPRA:

PRODUCTO                                     CANT.   PRECIO UNIT.         SUBTOTAL
───────────────────────────────────────────────────────────────────
Laptop HP                                        1    $2,500,000.00    $2,500,000.00
iPhone 13                                        1    $3,500,000.00    $3,500,000.00
───────────────────────────────────────────────────────────────────

TOTALES:

Subtotal:                                                          $6,000,000.00
───────────────────────────────────────────────────────────────────
TOTAL A PAGAR:                                                     $6,000,000.00

═══════════════════════════════════════════════════════════════════

                     ¡GRACIAS POR SU COMPRA!

        Esta factura es un documento válido para efectos legales
               y tributarios según la legislación vigente.

───────────────────────────────────────────────────────────────────
                Sistema de Inventario v1.0 - 2025
═══════════════════════════════════════════════════════════════════
```

### **Con IVA (19%):**

```
TOTALES:

Subtotal:                                                          $6,000,000.00
IVA (19%):                                                         $1,140,000.00
───────────────────────────────────────────────────────────────────
TOTAL A PAGAR:                                                     $7,140,000.00
```

---

## 🧪 **Pasos para Probar**

### **Test 1: Factura Sin IVA**

1. Ejecuta la app desde IDE
2. Inicia sesión (admin/admin123)
3. Ve a "Generar Facturas"
4. Selecciona una venta
5. Ingresa:
   - Nombre: Juan Pérez
   - Documento: 123456789
6. **DESMARCA** "Incluir IVA (19%)"
7. Click "📄 Generar Factura"
8. **Revisa la consola**
9. **Revisa la factura en pantalla**

**Esperado:**
- ✅ Muestra productos con precios
- ✅ Muestra subtotal
- ✅ NO muestra línea de IVA
- ✅ Total = Subtotal

### **Test 2: Factura Con IVA**

1. Repite pasos 1-5
2. **MARCA** "Incluir IVA (19%)"
3. Click "📄 Generar Factura"
4. **Revisa la consola**
5. **Revisa la factura en pantalla**

**Esperado:**
- ✅ Muestra productos con precios
- ✅ Muestra subtotal
- ✅ Muestra línea de IVA (19%)
- ✅ Total = Subtotal + IVA

---

## 📝 **Qué Necesito de Ti**

Por favor, ejecuta la app desde el IDE y:

1. **Genera una factura** (con o sin IVA)
2. **Copia TODOS los logs** desde:
   ```
   📄 ===== GENERANDO FACTURA =====
   ```
   Hasta:
   ```
   ✅✅✅ FACTURA GENERADA EXITOSAMENTE ✅✅✅
   ```
3. **Toma un screenshot** de la factura generada
4. **Comparte ambos** (logs + screenshot)

Con esa información podré decirte **exactamente** qué está fallando.

---

## 🔧 **Archivos Modificados**

- ✅ `FacturaController.java` - Logging detallado en `generarVistaPrevia()`

---

## 💡 **Checklist de Validación**

Cuando veas los logs, verifica:

- [ ] `Items: [número > 0]` ✅
- [ ] Aparecen los productos en `📦 Items de la venta` ✅
- [ ] `Incluir IVA: true` o `false` según lo que marcaste ✅
- [ ] `Subtotal: [monto]` tiene valor ✅
- [ ] `IVA: [monto]` tiene valor si marcaste IVA ✅
- [ ] `Total: [monto]` tiene valor ✅
- [ ] `Texto generado ([número] caracteres)` > 1000 ✅

Si alguno falla, ese es el problema.

---

**¡Los logs te dirán exactamente qué está pasando!** 🔍✨

**Por favor, comparte los logs y un screenshot de la factura.** 📋📸

