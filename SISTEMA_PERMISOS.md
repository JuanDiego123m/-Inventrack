# 🛡️ Sistema de Permisos y Roles
Sistema de control de acceso basado en roles y niveles de permisos, implementado en Java.
Permite gestionar usuarios con diferentes privilegios para garantizar la seguridad y jerarquía dentro del sistema.

# 👑 Jerarquía de Roles

### **🧠 SUPER_ADMIN (Super Administrador)**

- Nivel de Permiso: 100 (Máximo)

- Credenciales: superadmin / superadmin123

- Permisos Exclusivos:

✅ Crear cualquier tipo de usuario (incluidos otros SUPER_ADMIN)

✅ Gestionar todos los productos

✅ Procesar todas las ventas

✅ Generar todos los reportes

✅ Acceso completo al sistema

✅ Modificar configuración del sistema

### **👨‍💼 ADMIN (Administrador)**

- Nivel de Permiso: 50

- Credenciales: admin / admin123

Permisos:

❌ No puede crear usuarios

✅ Gestionar productos

✅ Procesar ventas

✅ Generar reportes

✅ Consultar datos

### **👨‍💻 VENDEDOR (Vendedor)**

- Nivel de Permiso: 10

- Credenciales: vendedor / vendedor123

Permisos:

❌ No puede crear usuarios

❌ No puede gestionar productos

✅ Procesar ventas

❌ No puede generar reportes

✅ Consultar datos

### **👁️ CONSULTA (Solo Consulta)**
- Nivel de Permiso: 1 (Mínimo)

Permisos:

❌ No puede crear usuarios

❌ No puede gestionar productos

❌ No puede procesar ventas

❌ No puede generar reportes

✅ Solo consultar datos

## 🔒 **Control de Acceso**

### **🧩 Registro de Usuarios**
```java
// Solo SUPER_ADMIN puede crear usuarios
if (!usuarioActual.esSuperAdministrador()) {
    mostrarError("No tiene permisos para crear usuarios");
    return;
}
```

### **✅Verificación de Permisos**
```java
// Verificar si puede realizar una acción
if (usuario.puedeRealizarAccion("CREAR_USUARIOS")) {
    // Permitir acción
} else {
    // Denegar acceso
}
```

### **🧱Creación de Usuarios con Permisos**
```java
// Verificar que puede crear este tipo de usuario
if (usuarioActual.puedeCrearUsuario(Rol.ADMIN)) {
    // Permitir crear administrador
} else {
    // Denegar creación
}
```

## 🛡️ **Seguridad Implementada**

### **1. Verificación de Permisos en Controlador**
```java
public void registrarUsuario() {
    // Verificar permisos antes de proceder
    if (usuarioActual == null || !usuarioActual.esSuperAdministrador()) {
        view.mostrarError("No tiene permisos para crear usuarios");
        return;
    }
    // ... resto del código
}
```

### **2. Verificación en Servicio**
```java
public boolean crearUsuario(Usuario usuario, Usuario usuarioCreador) {
    // Solo SUPER_ADMIN puede crear usuarios
    if (usuarioCreador == null || !usuarioCreador.esSuperAdministrador()) {
        return false;
    }
    // ... resto del código
}
```

### **3. Verificación en Interfaz**
```java
private void abrirRegistrarUsuario() {
    // Verificar permisos antes de abrir la ventana
    if (!usuarioActual.esSuperAdministrador()) {
        JOptionPane.showMessageDialog(this, "No tiene permisos");
        return;
    }
    // ... abrir ventana
}
```

## 👤 **Usuario Principal del Sistema**

### **SUPER_ADMIN - Usuario Maestro**
- **Username**: `superadmin`
- **Password**: `superadmin123`
- **Nombre**: Super Administrador
- **Email**: superadmin@inventario.com
- **Rol**: SUPER_ADMIN

### **Características del SUPER_ADMIN**:
1. **Único usuario** que puede crear otros usuarios
2. **Acceso total** al sistema
3. **Puede crear** cualquier tipo de usuario
4. **Responsable** de la gestión de usuarios
5. **Usuario principal** del sistema

## 🚀 **Flujo de Trabajo**

### **🧭 Primera Configuración**
```
1. Iniciar sesión como SUPER_ADMIN
2. Crear usuarios ADMIN según necesidad
3. Crear usuarios VENDEDOR para operaciones
4. Asignar permisos según roles
```

### **⚙️Gestión Diaria**
```
1. SUPER_ADMIN: Gestiona usuarios y configuración
2. ADMIN: Gestiona productos y reportes
3. VENDEDOR: Procesa ventas y consultas
```

### **📈Escalabilidad**
```
1. SUPER_ADMIN puede crear más ADMIN
2. Cada ADMIN puede gestionar su área
3. VENDEDOR se enfoca en ventas
```

## 🔧 **Implementación Técnica**

### **🧱Enum de Roles**
```java
public enum Rol {
    SUPER_ADMIN("SUPER_ADMIN", "Super Administrador", 100),
    ADMIN("ADMIN", "Administrador", 50),
    VENDEDOR("VENDEDOR", "Vendedor", 10),
    CONSULTA("CONSULTA", "Solo Consulta", 1);
}
```

### **🔍Verificación de Permisos**
```java
public boolean puedeRealizarAccion(String accion) {
    switch (accion) {
        case "CREAR_USUARIOS":
            return this == SUPER_ADMIN;
        case "GESTIONAR_PRODUCTOS":
            return this == SUPER_ADMIN || this == ADMIN;
        // ... más casos
    }
}
```

### **🔐Control de Acceso**
```java
public boolean puedeCrearUsuario(Rol rolDestino) {
    // Solo SUPER_ADMIN puede crear cualquier tipo de usuario
    return this == SUPER_ADMIN;
}
```

## 📊 **Matriz de Permisos**

| Acción | SUPER_ADMIN | ADMIN | VENDEDOR | CONSULTA |
|--------|-------------|-------|----------|----------|
| Crear Usuarios | ✅ | ❌ | ❌ | ❌ |
| Gestionar Productos | ✅ | ✅ | ❌ | ❌ |
| Procesar Ventas | ✅ | ✅ | ✅ | ❌ |
| Generar Reportes | ✅ | ✅ | ❌ | ❌ |
| Consultar Datos | ✅ | ✅ | ✅ | ✅ |

## 🧑‍💻 **Configuración de Usuarios**

### **Crear SUPER_ADMIN (Solo en código)**
```java
// En UsuarioService.java
usuarios.add(new Usuario("superadmin", "superadmin123", 
    "Super Administrador", "superadmin@inventario.com", Rol.SUPER_ADMIN));
```

### **Crear ADMIN (Desde SUPER_ADMIN)**
```
1. Iniciar sesión como SUPER_ADMIN
2. Ir a "Usuario" → "Registrar Usuario"
3. Completar formulario
4. Seleccionar "Administrador"
5. Guardar usuario
```

### **Crear VENDEDOR (Desde SUPER_ADMIN)**
```
1. Iniciar sesión como SUPER_ADMIN
2. Ir a "Usuario" → "Registrar Usuario"
3. Completar formulario
4. Seleccionar "Vendedor"
5. Guardar usuario
```

## 🔍 **Verificación de Seguridad**

### **🚨Logs de Seguridad**
```java
// Registrar intentos de acceso no autorizado
if (!usuario.puedeRealizarAccion("CREAR_USUARIOS")) {
    logger.warn("Intento de creación de usuario no autorizado: " + usuario.getUsername());
    return false;
}
```

### **📅Auditoría de Usuarios**
```java
// Registrar quién creó cada usuario
public class Usuario {
    private Usuario creadoPor;
    private LocalDateTime fechaCreacion;
    // ... otros campos
}
```

### 🔐 **Buenas Prácticas de Seguridad**

### **🔸 Contraseñas Seguras**
- **Mínimo 8 caracteres**
- **Incluir números y símbolos**
- **No usar contraseñas obvias**
- **Cambiar contraseñas regularmente**

### **🔸 Gestión de Usuarios**
- **Solo SUPER_ADMIN** puede crear usuarios
- **Revisar permisos** regularmente
- **Desactivar usuarios** inactivos
- **Auditar accesos** periódicamente

### **🔸 Configuración del Sistema**
- **Mantener credenciales** del SUPER_ADMIN seguras
- **Documentar cambios** de permisos
- **Respaldar configuración** de usuarios
- **Monitorear accesos** sospechosos

## 📚 **Documentación de Usuarios**

### **Credenciales por Defecto**
```
SUPER_ADMIN:
- Usuario: superadmin
- Contraseña: superadmin123
- Rol: Super Administrador

ADMIN:
- Usuario: admin
- Contraseña: admin123
- Rol: Administrador

VENDEDOR:
- Usuario: vendedor
- Contraseña: vendedor123
- Rol: Vendedor
```

### **Cambio de Contraseñas**
```
1. Iniciar sesión con usuario actual
2. Ir a "Usuario" → "Cambiar Contraseña"
3. Ingresar contraseña actual
4. Ingresar nueva contraseña
5. Confirmar nueva contraseña
6. Guardar cambios
```

## 🎯 **Recomendaciones**

### **Para el SUPER_ADMIN**:
1. **Cambiar contraseña** por defecto inmediatamente
2. **Crear usuarios ADMIN** según necesidad
3. **Asignar permisos** específicos
4. **Monitorear accesos** regularmente

### **Para Administradores**:
1. **No intentar crear usuarios** (no tienen permisos)
2. **Enfocarse en gestión** de productos
3. **Generar reportes** según necesidad
4. **Contactar SUPER_ADMIN** para nuevos usuarios

### **Para Vendedores**:
1. **Procesar ventas** eficientemente
2. **Consultar productos** disponibles
3. **Generar facturas** para clientes
4. **Reportar problemas** al ADMIN

## 🔒 **Seguridad Avanzada (Futuro)**

### **Mejoras Planificadas**:
1. **Encriptación de contraseñas**
2. **Autenticación de dos factores**
3. **Sesiones con tiempo límite**
4. **Logs de auditoría detallados**
5. **Integración con LDAP/Active Directory**

¡El sistema de permisos está completamente implementado y funcional! 🎉
