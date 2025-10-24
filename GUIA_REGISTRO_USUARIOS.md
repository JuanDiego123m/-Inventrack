# 👤 Guía de Registro de Usuarios

## 📋 Cómo Registrar un Usuario

### **1. Acceder al Sistema de Registro**

#### **Opción 1: Desde el Menú Principal**
1. Iniciar sesión en el sistema
2. Ir al menú **"Usuario"** en la barra de menú
3. Seleccionar **"Registrar Usuario"**
4. Se abrirá la ventana de registro

#### **Opción 2: Desde el Login (Futuro)**
- Agregar botón "Registrar Usuario" en la pantalla de login
- Para usuarios que no tienen cuenta

### **2. Completar el Formulario de Registro**

#### **Campos Obligatorios:**
- **Usuario**: Nombre de usuario único (mínimo 3 caracteres)
- **Contraseña**: Mínimo 6 caracteres
- **Confirmar Contraseña**: Debe coincidir con la contraseña
- **Nombre Completo**: Nombre real del usuario
- **Email**: Dirección de correo electrónico válida
- **Tipo de Usuario**: Administrador o Vendedor

#### **Validaciones Implementadas:**
```java
// Username: Solo letras, números y guiones bajos
if (!username.matches("^[a-zA-Z0-9_]+$")) {
    // Error: Solo letras, números y guiones bajos
}

// Password: Mínimo 6 caracteres
if (password.length() < 6) {
    // Error: Mínimo 6 caracteres
}

// Email: Formato válido
if (!Validador.validarEmail(email)) {
    // Error: Formato de email inválido
}
```

### **3. Proceso de Registro**

#### **Paso 1: Validación de Datos**
```java
// Verificar campos obligatorios
if (username.isEmpty() || password.isEmpty() || nombre.isEmpty() || email.isEmpty()) {
    mostrarError("Todos los campos son obligatorios");
    return;
}

// Verificar formato de datos
if (!validarFormatoDatos(username, password, nombre, email)) {
    return;
}
```

#### **Paso 2: Verificar Usuario Existente**
```java
// Verificar que el usuario no exista
if (usuarioService.buscarUsuario(username) != null) {
    mostrarError("El nombre de usuario ya existe");
    return;
}
```

#### **Paso 3: Crear Usuario**
```java
// Crear nuevo usuario
Usuario nuevoUsuario = new Usuario();
nuevoUsuario.setUsername(username);
nuevoUsuario.setPassword(password);
nuevoUsuario.setNombre(nombre);
nuevoUsuario.setEmail(email);
nuevoUsuario.setRol(esAdmin ? "ADMIN" : "VENDEDOR");
nuevoUsuario.setActivo(true);
```

#### **Paso 4: Guardar en el Sistema**
```java
// Guardar usuario
if (usuarioService.crearUsuario(nuevoUsuario)) {
    mostrarMensaje("Usuario registrado exitosamente");
    limpiarFormulario();
} else {
    mostrarError("Error al registrar el usuario");
}
```

## 🎯 **Tipos de Usuario**

### **👨‍💼 Administrador**
- **Acceso completo** al sistema
- Puede **registrar otros usuarios**
- Puede **gestionar productos**
- Puede **procesar ventas**
- Puede **generar reportes**
- Puede **cambiar contraseñas**

### **👨‍💻 Vendedor**
- **Acceso limitado** al sistema
- Puede **procesar ventas**
- Puede **consultar productos**
- Puede **generar facturas**
- **No puede** registrar usuarios
- **No puede** modificar productos

## 🔐 **Seguridad del Sistema**

### **Validaciones de Seguridad:**
1. **Username único**: No se permiten duplicados
2. **Password seguro**: Mínimo 6 caracteres
3. **Email válido**: Formato correcto requerido
4. **Datos obligatorios**: Todos los campos son requeridos

### **Contraseñas:**
- **Mínimo 6 caracteres**
- **Confirmación requerida**
- **Almacenamiento seguro** (en memoria para este ejemplo)

## 📝 **Ejemplo de Registro**

### **Datos de Ejemplo:**
```
Usuario: juan_perez
Contraseña: 123456
Confirmar Contraseña: 123456
Nombre Completo: Juan Pérez
Email: juan.perez@empresa.com
Tipo de Usuario: Vendedor
```

### **Resultado:**
```
✅ Usuario registrado exitosamente
Usuario: juan_perez
Rol: Vendedor
```

## 🚀 **Usuarios Predefinidos**

### **Administrador por Defecto:**
```
Usuario: admin
Contraseña: admin123
Rol: Administrador
```

### **Vendedor por Defecto:**
```
Usuario: vendedor
Contraseña: vendedor123
Rol: Vendedor
```

## 🔧 **Configuración Avanzada**

### **Personalizar Validaciones:**
```java
// En Validador.java
public static boolean validarPassword(String password) {
    if (password == null || password.length() < 6) {
        return false;
    }
    // Agregar más validaciones según necesidad
    return true;
}
```

### **Agregar Campos Adicionales:**
```java
// En RegistroUsuarioFrame.java
private JTextField txtTelefono;
private JTextField txtDireccion;

// Agregar validaciones correspondientes
```

## 📊 **Monitoreo de Usuarios**

### **Ver Usuarios Registrados:**
```java
// En UsuarioService.java
public List<Usuario> obtenerTodosUsuarios() {
    return new ArrayList<>(usuarios);
}
```

### **Estadísticas de Usuarios:**
```java
// Contar usuarios por rol
public int contarUsuariosPorRol(String rol) {
    return (int) usuarios.stream()
            .filter(u -> u.getRol().equals(rol))
            .count();
}
```

## 🐛 **Solución de Problemas**

### **Error: "Usuario ya existe"**
- **Causa**: El nombre de usuario ya está registrado
- **Solución**: Elegir un nombre de usuario diferente

### **Error: "Email inválido"**
- **Causa**: Formato de email incorrecto
- **Solución**: Usar formato válido (ejemplo@dominio.com)

### **Error: "Contraseñas no coinciden"**
- **Causa**: Los campos de contraseña son diferentes
- **Solución**: Escribir la misma contraseña en ambos campos

### **Error: "Campos obligatorios"**
- **Causa**: Algún campo requerido está vacío
- **Solución**: Completar todos los campos marcados como obligatorios

## 🎨 **Personalización de la Interfaz**

### **Cambiar Colores:**
```java
// En RegistroUsuarioFrame.java
btnRegistrar.setBackground(new Color(76, 175, 80)); // Verde
btnCancelar.setBackground(new Color(244, 67, 54));  // Rojo
```

### **Agregar Iconos:**
```java
// Agregar iconos a los botones
btnRegistrar.setIcon(new ImageIcon("save.png"));
btnCancelar.setIcon(new ImageIcon("cancel.png"));
```

## 📚 **Código de Ejemplo**

### **Registro Completo:**
```java
// Crear ventana de registro
RegistroUsuarioFrame registroFrame = new RegistroUsuarioFrame();
registroFrame.setVisible(true);

// El controlador maneja toda la lógica
RegistroUsuarioController controller = new RegistroUsuarioController(registroFrame);
```

### **Validación Personalizada:**
```java
// Agregar validación personalizada
public boolean validarUsuarioEspecial(String username) {
    // Validar que el username no contenga palabras reservadas
    String[] palabrasReservadas = {"admin", "root", "system"};
    for (String palabra : palabrasReservadas) {
        if (username.toLowerCase().contains(palabra)) {
            return false;
        }
    }
    return true;
}
```

## 🚀 **Próximas Mejoras**

1. **Encriptación de contraseñas**
2. **Validación de email por envío de código**
3. **Historial de usuarios registrados**
4. **Roles personalizados**
5. **Integración con base de datos**
6. **Auditoría de registros**

## 📞 **Soporte**

Si tienes problemas con el registro de usuarios:

1. **Verificar logs** de error en la consola
2. **Revisar validaciones** implementadas
3. **Consultar documentación** del código
4. **Contactar al equipo** de desarrollo

¡El sistema de registro de usuarios está listo para usar! 🎉
