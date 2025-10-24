# 🤝 Guía de Colaboración en GitHub

## 📋 Configuración del Repositorio

### 1. **Estructura de Branches**
```
main                    # Código estable y funcional
├── develop           # Rama de desarrollo principal
├── feature/login     # Nueva funcionalidad de login
├── feature/productos # Gestión de productos
├── feature/ventas    # Sistema de ventas
├── feature/facturas  # Generación de facturas
└── hotfix/bug-123    # Correcciones urgentes
```

### 2. **Configuración Inicial**
```bash
# Clonar repositorio
git clone https://github.com/tu-usuario/sistema-inventario.git
cd sistema-inventario

# Configurar usuario
git config user.name "Tu Nombre"
git config user.email "tu-email@ejemplo.com"

# Crear rama develop
git checkout -b develop
git push -u origin develop
```

## 🔄 Flujo de Trabajo

### 1. **Para Nuevas Funcionalidades**
```bash
# 1. Actualizar develop
git checkout develop
git pull origin develop

# 2. Crear rama de feature
git checkout -b feature/nombre-funcionalidad

# 3. Desarrollar funcionalidad
# ... hacer commits ...

# 4. Push de la rama
git push -u origin feature/nombre-funcionalidad

# 5. Crear Pull Request en GitHub
```

### 2. **Para Correcciones**
```bash
# 1. Crear rama de hotfix
git checkout -b hotfix/descripcion-bug

# 2. Corregir bug
# ... hacer commits ...

# 3. Push y crear PR
git push -u origin hotfix/descripcion-bug
```

## 📝 Convenciones de Commits

### 1. **Formato de Commits**
```
tipo(scope): descripción breve

Descripción detallada del cambio (opcional)

- Lista de cambios específicos
- Referencias a issues (#123)
```

### 2. **Tipos de Commits**
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Documentación
- `style`: Formato de código
- `refactor`: Refactorización
- `test`: Pruebas
- `chore`: Tareas de mantenimiento

### 3. **Ejemplos de Commits**
```bash
# ✅ BUENO
git commit -m "feat(login): implementar autenticación de usuarios"
git commit -m "fix(productos): corregir validación de precios"
git commit -m "docs(readme): actualizar instrucciones de instalación"

# ❌ MALO
git commit -m "cambios"
git commit -m "fix"
git commit -m "actualizar"
```

## 🔍 Pull Requests

### 1. **Template de Pull Request**
```markdown
## 📋 Descripción
Breve descripción de los cambios realizados.

## 🎯 Tipo de Cambio
- [ ] Bug fix
- [ ] Nueva funcionalidad
- [ ] Breaking change
- [ ] Documentación

## ✅ Checklist
- [ ] Código compila sin errores
- [ ] Pruebas unitarias pasan
- [ ] Documentación actualizada
- [ ] No hay conflictos de merge

## 🧪 Pruebas
Descripción de las pruebas realizadas.

## 📸 Screenshots (si aplica)
Capturas de pantalla de los cambios visuales.

## 🔗 Issues Relacionados
Closes #123
```

### 2. **Proceso de Review**
1. **Auto-review**: Revisar tu propio código antes de solicitar review
2. **Asignar reviewers**: Mínimo 2 personas
3. **Address feedback**: Responder a comentarios y hacer cambios
4. **Merge**: Solo después de aprobación

## 👥 Roles y Responsabilidades

### 1. **Desarrollador**
- Crear ramas feature/hotfix
- Hacer commits descriptivos
- Crear Pull Requests
- Responder a feedback
- Realizar auto-review

### 2. **Reviewer**
- Revisar código de otros
- Aprobar/rechazar PRs
- Sugerir mejoras
- Verificar que cumple estándares

### 3. **Maintainer**
- Merge de PRs aprobados
- Gestionar releases
- Resolver conflictos
- Mantener documentación

## 🏷️ Etiquetado y Issues

### 1. **Labels para Issues**
- `bug`: Error en el código
- `enhancement`: Nueva funcionalidad
- `documentation`: Mejoras en documentación
- `help wanted`: Necesita ayuda
- `good first issue`: Bueno para principiantes
- `priority: high`: Alta prioridad
- `priority: medium`: Prioridad media
- `priority: low`: Baja prioridad

### 2. **Milestones**
- `Sprint 1`: Semana 1
- `Sprint 2`: Semana 2
- `Sprint 3`: Semana 3
- `Sprint 4`: Semana 4
- `Release v1.0`: Versión final

## 📊 Métricas de Colaboración

### 1. **Commits por Desarrollador**
```bash
# Ver commits por autor
git shortlog -sn

# Ver commits en un rango de fechas
git log --since="2024-01-01" --until="2024-01-31" --pretty=format:"%an %s"
```

### 2. **Contribuciones por Archivo**
```bash
# Ver quién modificó cada archivo
git log --pretty=format:"%an" --name-only | sort | uniq -c | sort -rn
```

### 3. **Actividad en GitHub**
- **Insights > Contributors**: Gráficos de contribuciones
- **Insights > Traffic**: Visitas y clones
- **Insights > Network**: Red de commits

## 🔧 Herramientas de Colaboración

### 1. **GitHub Desktop**
- Interfaz gráfica para Git
- Facilita el manejo de branches
- Visualización de cambios

### 2. **VS Code + Git**
- Integración nativa con Git
- Extensiones útiles:
  - GitLens
  - Git Graph
  - Git History

### 3. **GitHub CLI**
```bash
# Instalar GitHub CLI
# Crear PR desde terminal
gh pr create --title "Nueva funcionalidad" --body "Descripción"

# Ver PRs
gh pr list

# Merge PR
gh pr merge 123 --merge
```

## 📋 Checklist de Colaboración

### ✅ Antes de Empezar
- [ ] Repositorio clonado
- [ ] Rama develop actualizada
- [ ] Issue asignado
- [ ] Entorno de desarrollo configurado

### ✅ Durante el Desarrollo
- [ ] Commits frecuentes y descriptivos
- [ ] Código compila sin errores
- [ ] Pruebas unitarias pasan
- [ ] Documentación actualizada

### ✅ Antes de PR
- [ ] Código revisado personalmente
- [ ] Tests ejecutados
- [ ] Documentación actualizada
- [ ] No hay conflictos de merge

### ✅ Durante el Review
- [ ] Responder a comentarios
- [ ] Hacer cambios solicitados
- [ ] Actualizar PR si es necesario
- [ ] Agradecer feedback

## 🚀 Evidencias de Colaboración

### 1. **Commits Colaborativos**
```bash
# Ver commits con múltiples autores
git log --pretty=format:"%an: %s" | grep -E "(Co-authored|Reviewed-by)"
```

### 2. **Pull Requests**
- Historial de PRs en GitHub
- Comentarios y discusiones
- Aprobaciones y cambios

### 3. **Issues**
- Asignación de issues
- Comentarios y soluciones
- Cierre de issues

### 4. **Documentación**
- README actualizado
- Documentación técnica
- Guías de usuario

## 🎯 Mejores Prácticas

### 1. **Comunicación**
- Usar issues para discusiones
- Comentarios descriptivos en PRs
- Responder rápidamente a feedback
- Mantener conversaciones constructivas

### 2. **Código**
- Seguir estándares del proyecto
- Escribir código limpio y legible
- Documentar decisiones importantes
- Hacer commits atómicos

### 3. **Testing**
- Probar cambios localmente
- Escribir pruebas unitarias
- Verificar que no rompe funcionalidad existente
- Documentar casos de prueba

### 4. **Documentación**
- Actualizar README
- Documentar APIs
- Mantener changelog
- Escribir guías de usuario

## 📈 Métricas de Éxito

### 1. **Cantidad**
- Número de commits por desarrollador
- Líneas de código contribuidas
- Issues resueltos

### 2. **Calidad**
- Tiempo de resolución de PRs
- Número de revisiones por PR
- Bugs reportados vs resueltos

### 3. **Colaboración**
- Comentarios en PRs
- Discusiones en issues
- Documentación colaborativa

## 🔍 Troubleshooting

### 1. **Conflictos de Merge**
```bash
# Resolver conflictos
git status
# Editar archivos con conflictos
git add archivo-resuelto.java
git commit -m "resolve: resolver conflictos en archivo.java"
```

### 2. **Ramas Desactualizadas**
```bash
# Actualizar rama local
git checkout develop
git pull origin develop
git checkout feature/mi-rama
git rebase develop
```

### 3. **Commits Incorrectos**
```bash
# Modificar último commit
git commit --amend -m "nuevo mensaje"

# Revertir commits
git revert HEAD
```

## 📚 Recursos Adicionales

- [GitHub Flow](https://guides.github.com/introduction/flow/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Git Best Practices](https://git-scm.com/book/en/v2)
- [GitHub Collaboration](https://docs.github.com/en/get-started/quickstart/hello-world)

