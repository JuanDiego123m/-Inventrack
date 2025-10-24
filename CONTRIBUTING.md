# 🤝 Guía de Contribución - Sistema de Inventario

## 📋 Cómo Contribuir

### 1. **Fork del Repositorio**
1. Ve al repositorio principal
2. Haz clic en "Fork" en la esquina superior derecha
3. Clona tu fork localmente:
```bash
git clone https://github.com/tu-usuario/sistema-inventario.git
cd sistema-inventario
```

### 2. **Configurar Upstream**
```bash
git remote add upstream https://github.com/repositorio-original/sistema-inventario.git
```

### 3. **Crear una Rama de Feature**
```bash
git checkout -b feature/nombre-funcionalidad
```

### 4. **Hacer Cambios**
- Escribe código limpio y bien documentado
- Sigue las convenciones de código del proyecto
- Haz commits frecuentes y descriptivos

### 5. **Push y Pull Request**
```bash
git push origin feature/nombre-funcionalidad
```
Luego crea un Pull Request en GitHub.

## 🎯 Estándares de Código

### **Java Style Guide**
```java
// ✅ BUENO: Nombres descriptivos
private JButton btnGuardarProducto;
private ProductoService productoService;

// ✅ BUENO: Métodos con propósito claro
public void guardarProducto() {
    // implementación
}

// ✅ BUENO: Documentación JavaDoc
/**
 * Guarda un producto en el sistema
 * @param producto Producto a guardar
 * @return true si se guardó exitosamente
 */
public boolean guardarProducto(Producto producto) {
    // implementación
}
```

### **Convenciones de Naming**
- **Clases**: PascalCase (`ProductoFrame`, `UsuarioService`)
- **Métodos**: camelCase (`guardarProducto`, `obtenerUsuario`)
- **Variables**: camelCase (`productoSeleccionado`, `usuarioActual`)
- **Constantes**: UPPER_SNAKE_CASE (`MAX_PRODUCTOS`, `COLOR_PRIMARY`)

### **Estructura de Archivos**
```
src/main/java/com/inventario/
├── model/          # Entidades de datos
├── view/           # Interfaces gráficas
├── controller/     # Controladores
├── service/        # Lógica de negocio
├── util/           # Utilidades
└── main/           # Punto de entrada
```

## 🔍 Proceso de Review

### **Checklist para Pull Requests**
- [ ] Código compila sin errores
- [ ] Pruebas unitarias pasan
- [ ] Documentación actualizada
- [ ] No hay conflictos de merge
- [ ] Commits descriptivos
- [ ] Código sigue estándares del proyecto

### **Checklist para Reviewers**
- [ ] Código es legible y mantenible
- [ ] Lógica de negocio es correcta
- [ ] No hay código duplicado
- [ ] Manejo de errores apropiado
- [ ] Performance es aceptable

## 🧪 Testing

### **Pruebas Unitarias**
```java
@Test
public void testGuardarProducto() {
    // Arrange
    Producto producto = new Producto();
    producto.setNombre("Test Product");
    
    // Act
    boolean resultado = service.guardarProducto(producto);
    
    // Assert
    assertTrue(resultado);
}
```

### **Pruebas de Integración**
```java
@Test
public void testLoginExitoso() {
    // Test de integración entre componentes
    Usuario usuario = controller.autenticar("admin", "admin123");
    assertNotNull(usuario);
}
```

## 📝 Documentación

### **JavaDoc**
```java
/**
 * Servicio para la gestión de productos
 * Implementa la lógica de negocio para CRUD de productos
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 * @since 1.0
 */
public class ProductoService {
    // implementación
}
```

### **README Updates**
- Actualizar README.md cuando se agreguen nuevas funcionalidades
- Documentar cambios en la API
- Incluir ejemplos de uso

## 🚀 Release Process

### **Versionado Semántico**
- **MAJOR**: Cambios incompatibles
- **MINOR**: Nueva funcionalidad compatible
- **PATCH**: Correcciones de bugs

### **Changelog**
```markdown
## [1.1.0] - 2024-01-15
### Added
- Nueva funcionalidad de reportes
- Sistema de backup automático

### Changed
- Mejorada interfaz de usuario
- Optimizado rendimiento

### Fixed
- Corregido bug en cálculo de precios
- Solucionado problema de memoria
```

## 🐛 Reportar Bugs

### **Template de Bug Report**
```markdown
**Descripción del Bug**
Descripción clara del problema.

**Pasos para Reproducir**
1. Ir a '...'
2. Hacer clic en '...'
3. Ver error

**Comportamiento Esperado**
Lo que debería pasar.

**Comportamiento Actual**
Lo que está pasando.

**Screenshots**
Si aplica, agregar capturas de pantalla.

**Información Adicional**
- OS: Windows 10
- Java Version: 11
- Versión de la app: 1.0.0
```

## 💡 Sugerir Mejoras

### **Template de Feature Request**
```markdown
**Descripción de la Mejora**
Descripción clara de la funcionalidad deseada.

**Problema que Resuelve**
¿Qué problema resuelve esta mejora?

**Solución Propuesta**
Descripción de la solución propuesta.

**Alternativas Consideradas**
Otras soluciones que se consideraron.

**Contexto Adicional**
Cualquier otro contexto sobre la mejora.
```

## 📊 Métricas de Contribución

### **Commits**
- Mínimo 1 commit por día de trabajo
- Commits atómicos (un cambio por commit)
- Mensajes descriptivos

### **Pull Requests**
- Mínimo 2 revisiones por PR
- Resolver todos los comentarios
- Merge solo después de aprobación

### **Issues**
- Responder en 24 horas
- Cerrar issues resueltos
- Etiquetar apropiadamente

## 🎯 Roles y Responsabilidades

### **Contribuidor**
- Seguir estándares de código
- Escribir pruebas
- Documentar cambios
- Responder a feedback

### **Reviewer**
- Revisar código cuidadosamente
- Sugerir mejoras
- Aprobar/rechazar PRs
- Mantener calidad

### **Maintainer**
- Merge de PRs aprobados
- Gestionar releases
- Resolver conflictos
- Mantener documentación

## 🔧 Herramientas Recomendadas

### **IDE**
- IntelliJ IDEA (recomendado)
- Eclipse
- VS Code

### **Git**
- GitHub Desktop
- GitKraken
- SourceTree

### **Testing**
- JUnit 5
- Mockito
- AssertJ

## 📚 Recursos de Aprendizaje

- [Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- [Git Best Practices](https://git-scm.com/book)
- [JUnit Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)

## 🤝 Código de Conducta

### **Nuestros Compromisos**
- Ambiente inclusivo y acogedor
- Respeto mutuo
- Colaboración constructiva
- Aprendizaje continuo

### **Comportamiento Esperado**
- Usar lenguaje inclusivo
- Respetar diferentes puntos de vista
- Aceptar críticas constructivas
- Ayudar a otros contribuidores

### **Comportamiento Inaceptable**
- Lenguaje ofensivo
- Comportamiento inapropiado
- Trolling o comentarios insultantes
- Acoso público o privado

## 📞 Contacto

- **Email**: equipo@inventario.com
- **Slack**: #inventario-team
- **GitHub**: @equipo-inventario

## 🙏 Reconocimientos

Gracias a todos los contribuidores que hacen posible este proyecto:

- [Lista de contribuidores](CONTRIBUTORS.md)
- [Historial de releases](RELEASES.md)
- [Log de cambios](CHANGELOG.md)

