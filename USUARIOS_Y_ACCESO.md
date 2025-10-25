# 👤 Usuarios y Acceso al Sistema

## 🔐 Credenciales de Acceso

### **🌟 Super Administrador (SUPER_ADMIN)**
```
Usuario: superadmin
Contraseña: superadmin123
Tipo: Seleccionar "Administrador" en el login
```
**Permisos:**
- ✅ Crear y gestionar usuarios
- ✅ Gestionar productos
- ✅ Procesar ventas
- ✅ Generar reportes
- ✅ Acceso total al sistema

### **👨‍💼 Administrador (ADMIN)**
```
Usuario: admin
Contraseña: admin123
Tipo: Seleccionar "Administrador" en el login
```
**Permisos:**
- ❌ NO puede crear usuarios
- ✅ Gestionar productos
- ✅ Procesar ventas
- ✅ Generar reportes

### **👨‍💻 Vendedor (VENDEDOR)**
```
Usuario: vendedor
Contraseña: vendedor123
Tipo: Seleccionar "Vendedor" en el login
```
**Permisos:**
- ❌ NO puede crear usuarios
- ❌ NO puede gestionar productos
- ✅ Procesar ventas
- ✅ Consultar información

### **👤 Usuario de Prueba**
```
Usuario: juan
Contraseña: juan123
Tipo: Seleccionar "Vendedor" en el login
```
**Permisos:**
- Mismos permisos que VENDEDOR

## 🚀 Cómo Iniciar Sesión

### **Paso 1: Abrir la Aplicación**
- Ejecutar `InventarioApp.java`
- Se abrirá la ventana de login moderna

### **Paso 2: Ingresar Credenciales**
1. **Escribir usuario** en el campo "Usuario"
2. **Escribir contraseña** en el campo "Contraseña"
3. **Seleccionar tipo de usuario:**
   - "Administrador" para SUPER_ADMIN y ADMIN
   - "Vendedor" para VENDEDOR
4. **Clic en "INICIAR SESIÓN"**

### **Paso 3: Acceder al Sistema**
- Si las credenciales son correctas, verás el menú principal
- Tendrás acceso según tu rol

## ❌ Errores Comunes

### **Error: "Usuario o contraseña incorrectos"**
**Causas:**
1. Usuario o contraseña mal escrito
2. Tipo de usuario incorrecto seleccionado
3. Usuario no existe en la base de datos

**Solución:**
- Verificar que escribes exactamente: `superadmin` / `superadmin123`
- Asegurarte de seleccionar "Administrador" para superadmin
- Verificar que la base de datos `inventario.db` existe

### **Error: "Base de datos no inicializada"**
**Solución:**
- La base de datos se crea automáticamente al iniciar
- Si falla, eliminar `inventario.db` y reiniciar la aplicación

## 🔧 Funcionalidades Disponibles

### **📦 Gestión de Productos**
- **SUPER_ADMIN**: ✅ Acceso completo
- **ADMIN**: ✅ Acceso completo
- **VENDEDOR**: ❌ Sin acceso

**Funcionalidades:**
- Registrar nuevos productos
- Actualizar información de productos
- Eliminar productos
- Buscar y filtrar productos
- Ver stock disponible

### **🛒 Procesar Ventas**
- **SUPER_ADMIN**: ✅ Acceso completo
- **ADMIN**: ✅ Acceso completo
- **VENDEDOR**: ✅ Acceso completo

**Funcionalidades:**
- Módulo en desarrollo
- Próximamente disponible

### **🧾 Generar Facturas**
- **SUPER_ADMIN**: ✅ Acceso completo
- **ADMIN**: ✅ Acceso completo
- **VENDEDOR**: ❌ Sin acceso

**Funcionalidades:**
- Módulo en desarrollo
- Próximamente disponible

### **📊 Reportes**
- **SUPER_ADMIN**: ✅ Acceso completo
- **ADMIN**: ✅ Acceso completo
- **VENDEDOR**: ❌ Sin acceso

**Funcionalidades:**
- Módulo en desarrollo
- Próximamente disponible

### **👥 Registrar Usuarios**
- **SUPER_ADMIN**: ✅ Acceso completo
- **ADMIN**: ❌ Sin acceso
- **VENDEDOR**: ❌ Sin acceso

**Funcionalidades:**
- Solo SUPER_ADMIN puede crear usuarios
- Crear usuarios ADMIN y VENDEDOR
- No se puede crear otro SUPER_ADMIN

## 🎯 Casos de Uso

### **Caso 1: Primer Inicio del Sistema**
```
1. Ejecutar aplicación
2. Iniciar sesión como SUPER_ADMIN
   Usuario: superadmin
   Password: superadmin123
   Tipo: Administrador
3. Crear usuarios necesarios
4. Configurar productos iniciales
```

### **Caso 2: Operación Diaria**
```
1. Vendedor inicia sesión
   Usuario: vendedor
   Password: vendedor123
   Tipo: Vendedor
2. Accede al módulo de ventas
3. Procesa transacciones
4. Cierra sesión al terminar
```

### **Caso 3: Administración**
```
1. Admin inicia sesión
   Usuario: admin
   Password: admin123
   Tipo: Administrador
2. Gestiona productos
3. Revisa reportes
4. Actualiza inventario
```

## 🔄 Cambio de Contraseña

**Módulo en desarrollo**
- Próximamente disponible
- Requerirá contraseña actual
- Nueva contraseña con confirmación

## 📱 Interfaz del Login

### **Diseño Moderno**
- 🎨 Colores modernos y profesionales
- 📱 Diseño limpio y minimalista
- ✨ Efectos visuales en botones
- 🔒 Campos de entrada claros

### **Elementos de la Interfaz**
- 📦 Icono del sistema
- 📝 Campo de usuario
- 🔑 Campo de contraseña
- 👥 Selector de tipo de usuario
- ✅ Opción "Recordar usuario"
- 🔵 Botón "INICIAR SESIÓN"
- 🔴 Botón "CANCELAR"

## 🐛 Solución de Problemas

### **Problema: La aplicación no inicia**
**Solución:**
```
1. Verificar que Java 8+ está instalado
2. Compilar el proyecto: mvn clean compile
3. Verificar que sqlite-jdbc está en las dependencias
4. Revisar logs de error en consola
```

### **Problema: "Cannot connect to database"**
**Solución:**
```
1. Verificar permisos de escritura en el directorio
2. Eliminar inventario.db si está corrupto
3. Reiniciar la aplicación
4. La BD se recreará automáticamente
```

### **Problema: Login no funciona con credenciales correctas**
**Solución:**
```
1. Verificar que el tipo de usuario es correcto:
   - SUPER_ADMIN y ADMIN → Seleccionar "Administrador"
   - VENDEDOR → Seleccionar "Vendedor"
2. Verificar que la BD tiene datos iniciales
3. Revisar logs en consola para errores
```

## 📞 Soporte

Si tienes problemas adicionales:

1. **Revisar logs** en la consola
2. **Verificar base de datos** `inventario.db` existe
3. **Consultar documentación** del proyecto
4. **Contactar al equipo** de desarrollo

## 🎉 ¡Listo para Usar!

El sistema está completamente funcional con:
- ✅ Login moderno y seguro
- ✅ Gestión de productos funcional
- ✅ Sistema de permisos robusto
- ✅ Base de datos SQLite persistente
- ✅ Interfaz intuitiva y profesional

¡Disfruta usando el Sistema de Inventario! 🚀
