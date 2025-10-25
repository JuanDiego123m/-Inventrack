# 🚀 Guía Rápida - Módulo de Generar Facturas

## ⚡ Inicio Rápido

### **1. Acceder al Módulo**
```
Opción 1: Menú Principal → "Ventas" → "Generar Factura"
Opción 2: Clic en botón "📄 Facturas"
```

### **2. Generar una Factura en 5 Pasos**

```
Paso 1: Seleccionar venta ☑️
Paso 2: Ingresar nombre del cliente 📝
Paso 3: Decidir incluir IVA ✅/❌
Paso 4: Generar vista previa 📄
Paso 5: Guardar factura 💾
```

## 📋 Interfaz del Módulo

### **Distribución de Pantalla**

```
┌─────────────────────────────────────────────┐
│ 📄 Generar Facturas                         │
├──────────────────┬──────────────────────────┤
│ IZQUIERDA        │ DERECHA                  │
│                  │                          │
│ • Tabla Ventas   │ • Vista Previa           │
│ • Datos Cliente  │   de Factura             │
│ • Botones        │ • Botones de Acción      │
└──────────────────┴──────────────────────────┘
```

## 📊 Panel Izquierdo - Ventas

### **Tabla de Ventas**

| ☑️ | ID | Fecha | Vendedor | Items | Total |
|---|----|----|----------|-------|-------|
| ☐ | 1 | 24/10/25 10:30 | Juan | 3 | $8,925,000 |
| ☑ | 2 | 24/10/25 14:15 | Admin | 2 | $6,000,000 |

### **Cómo Seleccionar**

```
✅ Hacer clic en el checkbox (☑️)
✅ Solo UNA venta puede estar seleccionada
✅ Clic nuevamente para deseleccionar
```

### **Datos del Cliente**

```
┌─────────────────────────────┐
│ Nombre:    [Cliente General] │
│ Documento: [N/A]            │
│ ☑️ Incluir IVA (19%)       │
└─────────────────────────────┘
```

**Campos:**
- **Nombre** - Requerido
- **Documento** - Opcional
- **IVA** - Checkbox (marcado por defecto)

## 📄 Panel Derecho - Vista Previa

### **Vista Previa de Factura**

Muestra la factura completa antes de guardar:

```text
═══════════════════════════════════════════
          FACTURA DE VENTA
═══════════════════════════════════════════

Empresa:          SISTEMA DE INVENTARIO S.A.S
NIT:              900.123.456-7
...

Número: FACT-20251024-0001
Fecha:  24/10/2025 15:30:00
...

CLIENTE:
Nombre: Cliente General
...

DETALLE:
Producto                 Cant.  P.Unit.  Subtotal
Laptop HP                   2  $2,500k  $5,000k
...

TOTAL A PAGAR:                          $10,620,750
```

## 🔘 Botones y Acciones

### **Botones del Panel Izquierdo**

| Botón | Función |
|-------|---------|
| 🔄 **Actualizar** | Recarga la lista de ventas |
| 📄 **Generar Factura** | Crea la vista previa |

### **Botones del Panel Derecho**

| Botón | Función | Estado Inicial |
|-------|---------|----------------|
| 💾 **Guardar** | Guarda la factura en archivo | Deshabilitado |
| 🖨️ **Imprimir** | Simula impresión | Deshabilitado |
| 🔄 **Limpiar** | Limpia la vista previa | Habilitado |

**Nota:** Los botones Guardar e Imprimir se habilitan solo después de generar la vista previa.

## ✨ Proceso Paso a Paso

### **Escenario 1: Factura Simple**

```
1️⃣ Abrir "Generar Facturas"
2️⃣ Marcar checkbox de Venta #1
3️⃣ Verificar nombre: "Cliente General"
4️⃣ Verificar IVA: ✅ Marcado
5️⃣ Clic en "📄 Generar Factura"
6️⃣ Revisar vista previa
7️⃣ Clic en "💾 Guardar"
8️⃣ Confirmar en diálogo
9️⃣ Ver mensaje de éxito
🔟 ¡Listo! Archivo guardado
```

### **Escenario 2: Factura Sin IVA**

```
1️⃣ Seleccionar venta
2️⃣ Ingresar: "Empresa Exenta Ltda"
3️⃣ Documento: "890.123.456-1"
4️⃣ DESMARCAR ❌ checkbox IVA
5️⃣ Generar factura
6️⃣ Ver que NO se calcula IVA
7️⃣ Guardar
8️⃣ ¡Listo!
```

### **Escenario 3: Imprimir Factura**

```
1️⃣ Generar factura (pasos 1-5 anteriores)
2️⃣ Clic en "🖨️ Imprimir"
3️⃣ Confirmar
4️⃣ Sistema guarda archivo
5️⃣ Mensaje: "Lista para imprimir"
6️⃣ Abrir archivo y enviar a impresora
```

## 💰 IVA - ¿Incluir o No?

### **CON IVA (Checkbox Marcado)**

```
Subtotal:   $8,925,000
IVA (19%):  $1,695,750
────────────────────────
TOTAL:     $10,620,750
```

### **SIN IVA (Checkbox Desmarcado)**

```
Subtotal:   $8,925,000
────────────────────────
TOTAL:      $8,925,000
```

### **¿Cuándo Usar Sin IVA?**

- Cliente es empresa exenta
- Venta está exenta de IVA
- Régimen especial
- Cliente no responsable de IVA

## 📁 Archivos Generados

### **Ubicación**

```
📁 Carpeta del proyecto
  ├── src/
  ├── pom.xml
  ├── 📄 factura_FACT_20251024_0001_20251024_153045.txt ← Aquí
  └── 📄 factura_FACT_20251024_0002_20251024_154230.txt ← Aquí
```

### **Nombre del Archivo**

```
factura_FACT_20251024_0001_20251024_153045.txt
         └──────┬──────┘ └──────┬──────┘
         Número Factura  Fecha/Hora Generación

Formato: factura_[NUMERO]_[TIMESTAMP].txt
```

### **Abrir el Archivo**

```
Windows:  Doble clic → Abre con Notepad
Mac:      Doble clic → Abre con TextEdit
Linux:    Doble clic → Abre con editor de texto

O arrastrar a Word/LibreOffice para mejor formato
```

## 💡 Tips y Trucos

### **Tip 1: Verificar Antes de Guardar**

```
✅ SIEMPRE revisar vista previa
✅ Confirmar nombre del cliente
✅ Verificar cálculos
✅ Revisar IVA correcto
```

### **Tip 2: Datos del Cliente**

```
✅ BUENA PRÁCTICA:
- Usar nombre completo
- Si es empresa, incluir NIT en documento
- Para clientes frecuentes, mantener consistencia
```

### **Tip 3: Organizar Archivos**

```
✅ RECOMENDADO:
- Crear carpeta "Facturas"
- Mover archivos generados
- Organizar por mes: "Facturas/2025-10/"
- Respaldar semanalmente
```

### **Tip 4: Reimprimir**

```
⚠️ NO SE PUEDE editar factura guardada

Si necesita:
1. Abrir archivo .txt
2. Copiar contenido
3. Pegar en Word
4. Imprimir desde Word

O generar nueva factura
```

## ⚠️ Errores Comunes

### **Error 1: "Seleccione una venta"**

```
Problema: No marcó el checkbox
Solución: Hacer clic en ☑️ de la venta deseada
```

### **Error 2: "Ingrese el nombre del cliente"**

```
Problema: Campo nombre está vacío
Solución: Escribir al menos un nombre
```

### **Error 3: Botones deshabilitados**

```
Problema: No generó vista previa
Solución: Clic en "📄 Generar Factura" primero
```

### **Error 4: "No se pudo cargar venta"**

```
Problema: Venta no existe en BD
Solución:
1. Clic en "🔄 Actualizar"
2. Seleccionar venta existente
3. Si persiste, reiniciar módulo
```

## 🔍 Verificar Factura Guardada

### **Checklist de Verificación**

```
✅ Archivo existe en carpeta
✅ Nombre tiene formato correcto
✅ Tamaño > 0 bytes
✅ Se puede abrir sin errores
✅ Contiene todos los datos
✅ Cálculos son correctos
```

### **Contenido Esperado**

```
Debe incluir:
✅ Encabezado con datos empresa
✅ Número de factura único
✅ Fecha y hora
✅ Datos del cliente
✅ Lista de productos
✅ Subtotal, IVA, Total
✅ Pie de factura
```

## 📊 Ejemplos Prácticos

### **Ejemplo 1: Cliente Particular**

```
Venta: #1
Cliente: "María García"
Documento: "1234567890"
IVA: ✅ Sí

Resultado:
FACT-20251024-0001
Total con IVA: $10,620,750
```

### **Ejemplo 2: Empresa**

```
Venta: #2
Cliente: "ACME Corporación S.A."
Documento: "890.123.456-7"
IVA: ❌ No (exenta)

Resultado:
FACT-20251024-0002
Total sin IVA: $6,000,000
```

### **Ejemplo 3: Cliente General**

```
Venta: #3
Cliente: "Cliente General"
Documento: "N/A"
IVA: ✅ Sí

Resultado:
FACT-20251024-0003
Total: $2,975,000
```

## 🎯 Preguntas Frecuentes

**P: ¿Puedo facturar la misma venta dos veces?**  
R: Sí, pero cada factura tendrá número único. Útil para reimpresiones.

**P: ¿Puedo editar una factura guardada?**  
R: No directamente. Debe abrir el .txt y editarlo manualmente, o generar nueva.

**P: ¿Las facturas se guardan en la base de datos?**  
R: No, solo se generan archivos .txt. Futuro: guardar en BD.

**P: ¿Puedo cambiar los datos de la empresa?**  
R: Sí, editando `FacturaService.java` y recompilando.

**P: ¿Qué pasa si cierro sin guardar?**  
R: La vista previa se pierde. Debe generarla nuevamente.

**P: ¿Puedo generar PDF?**  
R: No actualmente. Solo formato .txt. PDF en próxima versión.

**P: ¿Cómo envío la factura al cliente?**  
R: Adjuntar archivo .txt al email, o imprimir y entregar físicamente.

## 🔐 Permisos

| Usuario | Puede Generar Facturas |
|---------|------------------------|
| ✅ SUPER_ADMIN | Sí |
| ✅ ADMIN | Sí |
| ✅ VENDEDOR | Sí |
| ❌ CONSULTA | No |

## ⌨️ Atajos

| Acción | Método |
|--------|--------|
| Seleccionar venta | Clic en checkbox |
| Generar | Clic botón o Enter en nombre |
| Guardar | Clic botón o Ctrl+S (si implementado) |
| Limpiar | Clic botón |

## 🎉 ¡Listo para Facturar!

Con este módulo puedes:

- ✅ **Generar facturas** profesionales
- ✅ **Personalizar** datos del cliente  
- ✅ **Incluir/excluir** IVA según necesidad
- ✅ **Guardar** archivos organizados
- ✅ **Cumplir** con normativas

---

**¡Facturación rápida y profesional!** 📄💼

---

**Versión:** 1.0  
**Última actualización:** 24 de Octubre, 2025
