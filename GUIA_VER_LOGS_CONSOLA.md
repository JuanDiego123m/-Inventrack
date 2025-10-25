# 🔍 Guía: Cómo Ver los Logs en la Consola

**Fecha:** 25 de octubre de 2025  
**Propósito:** Diagnosticar el error "No se pudo procesar la venta. Verifique el stock"

---

## 🎯 **¿Por Qué Necesito Ver los Logs?**

He agregado **logging exhaustivo** en el código que te dirá **EXACTAMENTE** por qué falla la venta:

- ✅ Qué usuario está procesando la venta
- ✅ Qué productos están en el carrito
- ✅ Cuánto stock hay disponible
- ✅ Dónde exactamente falla el proceso
- ✅ Qué error específico ocurre

**Sin los logs, es imposible saber qué está pasando.**

---

## 📋 **PASO A PASO: Ver Logs en tu IDE**

### **Opción 1: Si usas Eclipse**

#### **1. Abrir la Vista de Consola**
```
Menú: Window → Show View → Console
```
O presiona: `Alt + Shift + Q, C`

#### **2. Ejecutar la Aplicación**
```
Click derecho en InventarioApp.java → Run As → Java Application
```

#### **3. La consola aparecerá abajo**
- Verás una pestaña llamada **"Console"**
- Ahí aparecerán todos los logs

#### **4. Limpiar la Consola (Opcional)**
```
Click en el ícono de "Clear Console" (🗑️) en la barra de la consola
```

---

### **Opción 2: Si usas IntelliJ IDEA**

#### **1. Ejecutar la Aplicación**
```
Click derecho en InventarioApp.java → Run 'InventarioApp.main()'
```
O presiona: `Shift + F10`

#### **2. La consola aparece automáticamente**
- Verás una pestaña llamada **"Run"** o **"Console"** en la parte inferior
- Ahí aparecerán todos los logs

#### **3. Limpiar la Consola (Opcional)**
```
Click en el ícono de "Clear All" (🗑️) en la barra izquierda de la consola
```

---

### **Opción 3: Si usas NetBeans**

#### **1. Ejecutar la Aplicación**
```
Click derecho en InventarioApp.java → Run File
```
O presiona: `Shift + F6`

#### **2. La consola aparece automáticamente**
- Verás una pestaña llamada **"Output"** en la parte inferior
- Ahí aparecerán todos los logs

#### **3. Limpiar la Consola (Opcional)**
```
Click derecho en el output → Clear
```

---

### **Opción 4: Si usas Visual Studio Code**

#### **1. Abrir el Terminal Integrado**
```
Menú: View → Terminal
```
O presiona: `Ctrl + ñ` (o `Ctrl + backtick`)

#### **2. Ejecutar la Aplicación**
```
En el terminal, escribe:
cd "C:\Users\juanf\OneDrive - Tecnologico de Antioquia Institucion Universitaria\Escritorio\Contruccion software 1"
mvn clean compile exec:java -Dexec.mainClass="com.inventario.main.InventarioApp"
```

#### **3. Los logs aparecerán en el terminal**

---

### **Opción 5: Desde PowerShell (Si no tienes IDE abierto)**

#### **1. Abrir PowerShell**
```
Presiona: Windows + X → Windows PowerShell
```

#### **2. Navegar a la carpeta del proyecto**
```powershell
cd "C:\Users\juanf\OneDrive - Tecnologico de Antioquia Institucion Universitaria\Escritorio\Contruccion software 1"
```

#### **3. Compilar y ejecutar**
```powershell
mvn clean compile exec:java -Dexec.mainClass="com.inventario.main.InventarioApp"
```

#### **4. Los logs aparecerán en PowerShell**

---

## 🧪 **PASO A PASO: Reproducir el Error y Ver Logs**

### **1. Ejecuta la Aplicación desde tu IDE**
- **Eclipse:** Run As → Java Application
- **IntelliJ:** Run 'InventarioApp.main()'
- **NetBeans:** Run File
- **VSCode:** Usa el terminal con Maven

### **2. Asegúrate de que la Consola esté Visible**
- Debe aparecer automáticamente al ejecutar
- Si no, abre la vista de consola (ver arriba)

### **3. Limpia la Consola (Recomendado)**
- Click en el ícono de "Clear" o "Limpiar"
- Esto elimina logs viejos

### **4. Inicia Sesión en la Aplicación**
```
Usuario: vendedor
Contraseña: vendedor123
```

### **5. Ve a "Procesar Ventas"**

### **6. Agrega un Producto al Carrito**
- Selecciona un producto
- Ingresa cantidad: 2
- Click "➕ Agregar al Carrito"

### **7. Click "💰 Procesar Venta"**

### **8. Confirma la Venta (Click "Yes")**

### **9. INMEDIATAMENTE Mira la Consola**

---

## 📊 **Qué Verás en la Consola**

### **✅ Logs Completos (Todo el Flujo)**

```
🛒 ===== VENTACONTROLLER: INICIANDO PROCESO =====
📋 Información de la venta:
   Usuario: vendedor
   Usuario ID: 2
   Items en carrito: 1
   Total: 100000.00

📦 Items en la venta:
   - Producto: Laptop HP
     ID: 1
     Código: PROD001
     Cantidad: 2
     Precio unitario: 50000.00
     Subtotal: 100000.00

✅ Usuario confirmó la venta. Llamando a VentaService...

🔄 ===== INICIANDO PROCESO DE VENTA =====
Usuario: vendedor
Total: $100000.00
Items: 1
✅ Venta tiene items

📦 Validando stock de productos...

  Producto: Laptop HP
  Código: PROD001
  Cantidad solicitada: 2
  Stock en memoria: 10
  Stock en BD: 10
  ✅ Stock suficiente

✅ Validación de stock completada

💾 Guardando venta en base de datos...
📝 Iniciando creación de venta...
   Usuario ID: 2
   Total: 100000.00
   Items: 1
✅ Conexión obtenida, transacción iniciada
📤 Ejecutando INSERT de venta...
✅ Venta insertada. Filas afectadas: 1
✅ ID de venta generado: 1
📤 Insertando items de venta...
   - Producto ID: 1, Cantidad: 2, Subtotal: 100000.00
✅ Items insertados: 1
✅ Transacción confirmada exitosamente
✅ Venta guardada en BD con ID: 1

📉 Actualizando inventario...

  Producto: Laptop HP
  Stock anterior: 10
  Cantidad vendida: 2
  Nuevo stock: 8
  ✅ Stock actualizado correctamente

✅✅✅ VENTA PROCESADA EXITOSAMENTE ✅✅✅
ID de Venta: 1
Total: $100000.00
=========================================

📊 Resultado de VentaService.procesarVenta(): true
```

### **❌ Si Hay Error, Verás Algo Como:**

```
🛒 ===== VENTACONTROLLER: INICIANDO PROCESO =====
📋 Información de la venta:
   Usuario: vendedor
   Usuario ID: 0  ← ⚠️ PROBLEMA: ID es 0 (inválido)
   Items en carrito: 1
   Total: 100000.00

[... más logs ...]

❌ ERROR SQL al crear venta:
   Mensaje: NOT NULL constraint failed: ventas.usuario_id
   SQL State: 23502
   Error Code: 1299

❌ VentaService retornó FALSE
Revisa los logs arriba para ver el motivo específico
```

O:

```
📦 Validando stock de productos...

  Producto: Laptop HP
  Código: PROD001
  Cantidad solicitada: 5
  Stock en memoria: 10
  Stock en BD: 2  ← ⚠️ PROBLEMA: Solo hay 2 en BD
  ❌ Error: Stock insuficiente para Laptop HP
     Disponible en BD: 2
     Requerido: 5
```

---

## 📝 **QUÉ NECESITO QUE HAGAS**

### **1. Ejecuta la aplicación desde tu IDE**
### **2. Reproduce el error (agregar producto → procesar venta)**
### **3. Copia TODOS los logs de la consola**

**Desde esta línea:**
```
🛒 ===== VENTACONTROLLER: INICIANDO PROCESO =====
```

**Hasta esta línea:**
```
=========================================
```

O hasta donde termine el error.

### **4. Pégalos aquí o en un archivo de texto**

---

## 🎯 **Ejemplo de Cómo Copiar los Logs**

### **En Eclipse:**
1. Click derecho en la consola
2. Select All (o Ctrl + A)
3. Copy (o Ctrl + C)
4. Pegar en un mensaje o archivo

### **En IntelliJ:**
1. Click en la consola
2. Ctrl + A (seleccionar todo)
3. Ctrl + C (copiar)
4. Pegar en un mensaje o archivo

### **En NetBeans:**
1. Click derecho en Output
2. Select All
3. Copy
4. Pegar en un mensaje o archivo

---

## 💡 **Importante**

- ✅ **Ejecuta SIEMPRE desde el IDE** (no desde .jar)
- ✅ **Limpia la consola** antes de reproducir el error
- ✅ **Copia TODOS los logs** (no solo una parte)
- ✅ **Incluye desde "🛒 =====" hasta "========="**

---

## 🔍 **Interpretación Rápida**

Busca en los logs:

| Mensaje | Significado |
|---------|-------------|
| `Usuario ID: 0` | ❌ Usuario no válido |
| `Usuario ID: 2` | ✅ Usuario válido |
| `Stock en BD: 0` | ❌ No hay stock |
| `Stock en BD: 10` | ✅ Hay stock |
| `ERROR SQL` | ❌ Problema en base de datos |
| `✅ VENTA PROCESADA EXITOSAMENTE` | ✅ Todo funcionó |
| `❌ Error: Stock insuficiente` | ❌ No hay suficiente stock |
| `Producto ID: 0` | ❌ Producto inválido |

---

## 🎉 **Una Vez que Tengas los Logs**

Compártelos y podré decirte:

1. ✅ **Exactamente** dónde está el problema
2. ✅ **Por qué** está fallando
3. ✅ **Cómo** solucionarlo

**¡Los logs son la clave para resolver el problema!** 🔑✨

---

## 📞 **¿Necesitas Ayuda?**

Si tienes problemas para ver los logs:

1. Dime qué IDE estás usando
2. Dime si ves alguna consola/output
3. Envía un screenshot de tu IDE

**¡Vamos a resolver esto juntos!** 💪

