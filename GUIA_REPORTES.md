# Guía Rápida - Módulo de Reportes

##  Inicio Rápido

### **1. Acceder al Módulo**
```
Opción 1: Menú Principal → "Reportes" → "Ver Reportes"
Opción 2: Clic en botón "📊 Reportes"
```

### **2. Pestañas Disponibles**

```
📊 Dashboard    - Estadísticas generales
💰 Ventas       - Listado de ventas
📦 Productos    - Catálogo completo
📋 Inventario   - Estado de stock
🏆 Top Ventas   - Productos más vendidos
```

##  Pestaña 1: Dashboard

### **¿Qué muestra?**

Vista rápida de 6 indicadores clave:

```
┌──────────────────┬──────────────────┐
│ 📊 Total Ventas  │ 📅 Ventas Hoy   │
│       15         │        3         │
└──────────────────┴──────────────────┘
┌──────────────────┬──────────────────┐
│ 📦 Productos     │ ⚠️ Bajo Stock   │
│   en Stock: 5    │         2        │
└──────────────────┴──────────────────┘
┌──────────────────┬──────────────────┐
│ 💰 Ingreso Total │ 📈 Promedio     │
│  $45,000,000     │   $3,000,000    │
└──────────────────┴──────────────────┘
```

### **¿Cómo usar?**

-  **Ver** - Los datos se cargan automáticamente
-  **Actualizar** - Cambiar a otra pestaña y volver

##  Pestaña 2: Ventas

### **¿Qué muestra?**

Tabla con todas las ventas registradas:

| ID | Fecha | Vendedor | Items | Total |
|----|-------|----------|-------|-------|
| 1 | 24/10/2025 10:30 | Juan | 3 | $8,925,000 |
| 2 | 24/10/2025 14:15 | Admin | 2 | $6,000,000 |

### **¿Cómo usar?**

1. **Ver ventas**
   - La tabla se llena automáticamente
   - Scroll para ver todas las ventas

2. **Actualizar**
   - Clic en " Actualizar"

3. **Exportar**
   - Clic en " Exportar"
   - Archivo .txt se guarda en carpeta raíz
   - Contiene todas las ventas con detalles

##  Pestaña 3: Productos

### **¿Qué muestra?**

Tabla con todos los productos del catálogo:

| Código | Nombre | Categoría | Stock | Precio | Valor Total |
|--------|--------|-----------|-------|--------|-------------|
| PROD001 | Laptop HP | Electrónica | 3 | $2,500,000 | $7,500,000 |

### **¿Cómo usar?**

1. **Ver productos**
   - Automático al abrir pestaña

2. **Actualizar**
   - Clic en " Actualizar"

3. **Exportar**
   - Clic en " Exportar"
   - Archivo .txt con todos los productos

##  Pestaña 4: Inventario

### **¿Qué muestra?**

Estado detallado del inventario:

```
Barra de Progreso:
[████████░░] 80% - 4 de 5 productos con stock adecuado
```

| Código | Producto | Stock | Estado | Acción |
|--------|----------|-------|--------|--------|
| PROD001 | Laptop HP | 3 | ⚠️ Stock Bajo | Reponer Pronto |
| PROD002 | iPhone 13 | 15 | ✅ Stock OK | Normal |
| PROD004 | Sofá | 0 | ❌ Sin Stock | Reponer Urgente |

### **Estados de Stock**

| Icono | Estado | Unidades | Color |
|-------|--------|----------|-------|
| ✅ | Stock OK | >= 10 | Verde |
| ⚡ | Stock Medio | 5-9 | Azul |
| ⚠️ | Stock Bajo | 1-4 | Amarillo |
| ❌ | Sin Stock | 0 | Rojo |

### **¿Cómo usar?**

1. **Revisar estado**
   - Ver barra de progreso general
   - Identificar productos críticos

2. **Tomar acción**
   - ❌ Sin Stock → Reponer urgente
   - ⚠️ Stock Bajo → Reponer pronto
   - ⚡ Stock Medio → Revisar
   - ✅ Stock OK → Normal

3. **Actualizar**
   - Clic en " Actualizar"

##  Pestaña 5: Top Ventas

### **¿Qué muestra?**

Los 10 productos más vendidos:

| # | Código | Producto | Unidades Vendidas | Ingresos |
|---|--------|----------|-------------------|----------|
| 🥇 | PROD003 | Camiseta Nike | 45 | $3,825,000 |
| 🥈 | PROD001 | Laptop HP | 12 | $30,000,000 |
| 🥉 | PROD002 | iPhone 13 | 8 | $28,000,000 |
| 4 | PROD005 | Libro Java | 6 | $900,000 |

### **Medallas**

- 🥇 **Oro** - 1er lugar
- 🥈 **Plata** - 2do lugar
- 🥉 **Bronce** - 3er lugar
- **Número** - Del 4to al 10mo

### **¿Cómo usar?**

1. **Analizar rendimiento**
   - Ver qué productos se venden más
   - Identificar productos estrella
   - Detectar tendencias

2. **Tomar decisiones**
   - Aumentar stock de más vendidos
   - Promocionar productos rezagados
   - Optimizar inventario

##  Botones Comunes

| Botón | Función |
|-------|---------|
| 🔄 **Actualizar** | Recarga los datos de la pestaña actual |
| 📄 **Exportar** | Guarda datos en archivo .txt (solo Ventas/Productos) |

## 📄 Exportación de Datos

### **¿Cómo exportar?**

1. Ir a pestaña "Ventas" o "Productos"
2. Clic en "📄 Exportar"
3. Ver mensaje de confirmación
4. Archivo guardado en carpeta raíz

### **Nombre del archivo**

```
reporte_ventas_20251024_153000.txt
reporte_productos_20251024_154500.txt

Formato: reporte_[tipo]_[fecha]_[hora].txt
```

### **¿Dónde encontrar el archivo?**

```
📁 Carpeta del proyecto
  ├── src/
  ├── pom.xml
  ├── 📄 reporte_ventas_20251024_153000.txt ← Aquí
  └── 📄 reporte_productos_20251024_154500.txt ← Aquí
```

### **Contenido del archivo**

```text
========================================
    REPORTE DE VENTAS
========================================
Fecha de generación: 24/10/2025 15:30:00
Total de ventas: 15
========================================

Venta #1
Fecha: 24/10/2025 10:30
Vendedor: Juan Pérez
Items: 3
Total: $8,925,000.00
----------------------------------------
  - Laptop HP x2 = $5,000,000.00
  - iPhone 13 x1 = $3,500,000.00
  - Camiseta Nike x5 = $425,000.00
...
```

##  Tips y Trucos

### **Tip 1: Monitoreo de Stock**
```
RECOMENDADO:
- Revisar "Inventario" diariamente
- Actuar cuando aparezca ⚠️ o ❌
- Mantener barra de progreso en verde (>70%)
```

### **Tip 2: Análisis de Ventas**
```
 BUENA PRÁCTICA:
1. Revisar "Dashboard" al inicio del día
2. Ver "Ventas" para detalles
3. Analizar "Top Ventas" semanalmente
4. Exportar reportes mensualmente
```

### **Tip 3: Reposición Inteligente**
```
 ESTRATEGIA:
- Priorizar productos con ❌ Sin Stock
- Reponer rápido productos 🥇🥈🥉
- Revisar semanalmente ⚠️ Stock Bajo
```

### **Tip 4: Usar Exportaciones**
```
 ÚTIL PARA:
- Auditorías
- Respaldos
- Análisis en Excel
- Reportes a gerencia
- Cumplimiento normativo
```

##  Interpretación de Datos

### **Dashboard**

```
Si ves:
- Ventas Hoy = 0 → ¡Incentiva las ventas!
- Bajo Stock > 3 → ¡Reponer urgente!
- Promedio Venta bajo → ¿Ofrecer combos?
```

### **Inventario**

```
Barra Verde (>70%):
✅ Estado saludable, inventario equilibrado

Barra Amarilla (40-69%):
⚠️ Necesita atención, revisar productos

Barra Roja (<40%):
❌ Estado crítico, reponer urgentemente
```

### **Top Ventas**

```
Si un producto baja de posición:
→ ¿Bajó demanda?
→ ¿Falta stock?
→ ¿Necesita promoción?

Si un producto sube:
→ ¡Mantener stock alto!
→ ¿Oportunidad de promoción?
```

##  Casos de Uso

### **Caso 1: Inicio de Día**

```
1. Abrir "Reportes"
2. Ver "Dashboard"
3. Verificar "Ventas Hoy" = 0 (normal al inicio)
4. Ir a "Inventario"
5. Identificar problemas de stock
6. Planificar reposiciones
```

### **Caso 2: Fin de Semana**

```
1. Ir a "Ventas"
2. Revisar ventas de la semana
3. Exportar reporte
4. Ir a "Top Ventas"
5. Identificar tendencias
6. Ajustar inventario para próxima semana
```

### **Caso 3: Auditoría**

```
1. Ir a "Ventas" → Exportar
2. Ir a "Productos" → Exportar
3. Guardar archivos con fecha
4. Revisar inconsistencias
5. Generar informe
```

##  Errores 

### **Error: "Datos no aparecen"**
```
Solución:
1. Clic en "🔄 Actualizar"
2. Cambiar de pestaña y volver
3. Cerrar y reabrir módulo
```

### **Error: "Archivo no se exporta"**
```
Verificar:
- ¿Hay ventas/productos para exportar?
- ¿Tienes permisos de escritura?
- ¿Hay espacio en disco?
```

##  Permisos

| Usuario | Puede Ver | Puede Exportar |
|---------|-----------|----------------|
| SUPER_ADMIN | ✅ Todo | ✅ Sí |
| ADMIN | ✅ Todo | ✅ Sí |
| VENDEDOR | ✅ Todo | ✅ Sí |
| CONSULTA | ✅ Todo | ❌ No |

##  Atajos

| Acción | Cómo |
|--------|------|
| Cambiar pestaña | Clic en nombre de pestaña |
| Actualizar | Clic en "🔄" |
| Exportar | Clic en "📄" |
| Cerrar | Clic en "X" de la ventana |

##  Preguntas Frecuentes

**P: ¿Los datos son en tiempo real?**  
R: Sí, se actualizan cada vez que cambias de pestaña o haces clic en "Actualizar".

**P: ¿Puedo ver ventas de fechas específicas?**  
R: Actualmente muestra todas las ventas. Filtros por fecha en próxima versión.

**P: ¿Qué hago si un producto está en rojo?**  
R:  Sin Stock = Reponer urgente en "Gestión de Productos".

**P: ¿Los archivos exportados se sobrescriben?**  
R: No, cada exportación crea un archivo nuevo con fecha y hora.

**P: ¿Cuántos productos muestra el Top?**  
R: Los 10 más vendidos.

##  Listo para Analizar

Con este módulo puedes:

-  **Monitorear** el negocio en tiempo real
-  **Detectar** problemas de stock
-  **Analizar** tendencias de venta
-  **Exportar** datos para auditorías
-  **Tomar decisiones** informadas

