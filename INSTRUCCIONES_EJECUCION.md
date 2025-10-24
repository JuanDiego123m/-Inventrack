# 🚀 Instrucciones de Ejecución - Sistema de Inventario

## 📋 Requisitos del Sistema

### **Software Necesario**
- **Java JDK 8 o superior** (recomendado JDK 11+)
- **IDE de desarrollo** (IntelliJ IDEA, Eclipse, VS Code)
- **Git** para control de versiones
- **GitHub Desktop** (opcional, para interfaz gráfica)

### **Verificar Instalación de Java**
```bash
# Verificar versión de Java
java -version

# Verificar compilador Java
javac -version

# Verificar que JAVA_HOME esté configurado
echo $JAVA_HOME  # En Linux/Mac
echo %JAVA_HOME% # En Windows
```

## 🏗️ Configuración del Proyecto

### **1. Clonar el Repositorio**
```bash
# Clonar repositorio
git clone https://github.com/tu-usuario/sistema-inventario.git
cd sistema-inventario

# Verificar estructura
ls -la
```

### **2. Estructura del Proyecto**
```
sistema-inventario/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── inventario/
│                   ├── model/
│                   ├── view/
│                   ├── controller/
│                   ├── service/
│                   ├── util/
│                   └── main/
├── README.md
├── .gitignore
└── CONTRIBUTING.md
```

## 🚀 Ejecución del Sistema

### **Método 1: Desde IDE (Recomendado)**

#### **IntelliJ IDEA**
1. **Abrir proyecto:**
   - File → Open → Seleccionar carpeta del proyecto
   - Esperar a que se indexe el proyecto

2. **Configurar SDK:**
   - File → Project Structure → Project
   - Seleccionar Java SDK 8 o superior
   - Apply → OK

3. **Ejecutar aplicación:**
   - Navegar a `src/main/java/com/inventario/main/InventarioApp.java`
   - Clic derecho → Run 'InventarioApp.main()'
   - O usar el botón verde de "Run"

#### **Eclipse**
1. **Importar proyecto:**
   - File → Import → General → Existing Projects into Workspace
   - Seleccionar carpeta del proyecto
   - Finish

2. **Ejecutar aplicación:**
   - Clic derecho en `InventarioApp.java`
   - Run As → Java Application

#### **VS Code**
1. **Abrir proyecto:**
   - File → Open Folder → Seleccionar carpeta del proyecto
   - Instalar extensión "Extension Pack for Java"

2. **Ejecutar aplicación:**
   - Abrir `InventarioApp.java`
   - Clic en "Run" sobre el método main
   - O usar Ctrl+F5

### **Método 2: Desde Terminal**

#### **Compilar y Ejecutar**
```bash
# Navegar al directorio del proyecto
cd sistema-inventario

# Compilar todas las clases
javac -cp src/main/java src/main/java/com/inventario/main/InventarioApp.java
javac -cp src/main/java src/main/java/com/inventario/model/*.java
javac -cp src/main/java src/main/java/com/inventario/view/*.java
javac -cp src/main/java src/main/java/com/inventario/controller/*.java
javac -cp src/main/java src/main/java/com/inventario/service/*.java

# Ejecutar aplicación
java -cp src/main/java com.inventario.main.InventarioApp
```

#### **Script de Compilación (Windows)**
```batch
@echo off
echo Compilando Sistema de Inventario...

javac -cp src/main/java -d build src/main/java/com/inventario/main/InventarioApp.java
javac -cp src/main/java -d build src/main/java/com/inventario/model/*.java
javac -cp src/main/java -d build src/main/java/com/inventario/view/*.java
javac -cp src/main/java -d build src/main/java/com/inventario/controller/*.java
javac -cp src/main/java -d build src/main/java/com/inventario/service/*.java

echo Compilacion completada.
echo Ejecutando aplicacion...
java -cp build com.inventario.main.InventarioApp

pause
```

#### **Script de Compilación (Linux/Mac)**
```bash
#!/bin/bash
echo "Compilando Sistema de Inventario..."

# Crear directorio de compilación
mkdir -p build

# Compilar todas las clases
javac -cp src/main/java -d build src/main/java/com/inventario/main/InventarioApp.java
javac -cp src/main/java -d build src/main/java/com/inventario/model/*.java
javac -cp src/main/java -d build src/main/java/com/inventario/view/*.java
javac -cp src/main/java -d build src/main/java/com/inventario/controller/*.java
javac -cp src/main/java -d build src/main/java/com/inventario/service/*.java

echo "Compilación completada."
echo "Ejecutando aplicación..."
java -cp build com.inventario.main.InventarioApp
```

## 🔧 Configuración Avanzada

### **1. Variables de Entorno**
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-11.0.12
set PATH=%JAVA_HOME%\bin;%PATH%

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

### **2. Configuración de IDE**

#### **IntelliJ IDEA - Configuración de Proyecto**
```xml
<!-- .idea/misc.xml -->
<component name="ProjectRootManager" version="2" languageLevel="JDK_11" default="true" project-jdk-name="11" project-jdk-type="JavaSDK">
  <output url="file://$PROJECT_DIR$/out" />
</component>
```

#### **Eclipse - Configuración de Proyecto**
```xml
<!-- .project -->
<projectDescription>
  <name>sistema-inventario</name>
  <buildSpec>
    <buildCommand>
      <name>org.eclipse.jdt.core.javabuilder</name>
    </buildCommand>
  </buildSpec>
  <natures>
    <nature>org.eclipse.jdt.core.javanature</nature>
  </natures>
</projectDescription>
```

### **3. Configuración de Git**
```bash
# Configurar usuario
git config user.name "Tu Nombre"
git config user.email "tu-email@ejemplo.com"

# Configurar rama principal
git config init.defaultBranch main

# Verificar configuración
git config --list
```

## 🐛 Solución de Problemas

### **Error: "javac no se reconoce como comando"**
```bash
# Solución: Verificar que Java esté en el PATH
# Windows
set PATH=%JAVA_HOME%\bin;%PATH%

# Linux/Mac
export PATH=$JAVA_HOME/bin:$PATH
```

### **Error: "No se puede encontrar la clase principal"**
```bash
# Solución: Verificar que la compilación sea exitosa
# Recompilar todas las clases
javac -cp src/main/java src/main/java/com/inventario/**/*.java

# Verificar que las clases estén en el classpath
java -cp src/main/java com.inventario.main.InventarioApp
```

### **Error: "Look and Feel no disponible"**
```java
// Solución: Agregar manejo de excepciones
try {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
} catch (Exception e) {
    System.err.println("Error al configurar Look and Feel: " + e.getMessage());
    // Continuar con Look and Feel por defecto
}
```

### **Error: "Componentes no se muestran"**
```java
// Solución: Verificar que el frame sea visible
frame.setVisible(true);
frame.setLocationRelativeTo(null);
frame.pack(); // Ajustar tamaño automáticamente
```

## 📊 Verificación de Funcionamiento

### **1. Pruebas Básicas**
```java
// Verificar que la aplicación se inicie
public static void main(String[] args) {
    System.out.println("Iniciando Sistema de Inventario...");
    
    try {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
        System.out.println("Aplicación iniciada correctamente.");
    } catch (Exception e) {
        System.err.println("Error al iniciar aplicación: " + e.getMessage());
        e.printStackTrace();
    }
}
```

### **2. Pruebas de Componentes**
```java
// Verificar que todos los componentes se carguen
@Test
public void testComponentesCargados() {
    LoginFrame frame = new LoginFrame();
    assertNotNull(frame.getLblTitulo());
    assertNotNull(frame.getTxtUsuario());
    assertNotNull(frame.getBtnLogin());
    assertNotNull(frame.getMenuBar());
}
```

### **3. Pruebas de Funcionalidad**
```java
// Verificar login
@Test
public void testLogin() {
    LoginFrame frame = new LoginFrame();
    frame.setUsuario("admin");
    frame.setPassword("admin123");
    assertTrue(frame.validarLogin());
}
```

## 🚀 Despliegue

### **1. Crear JAR Ejecutable**
```bash
# Compilar con dependencias
javac -cp src/main/java -d build src/main/java/com/inventario/**/*.java

# Crear JAR
jar cfe sistema-inventario.jar com.inventario.main.InventarioApp -C build .

# Ejecutar JAR
java -jar sistema-inventario.jar
```

### **2. Script de Instalación**
```bash
#!/bin/bash
echo "Instalando Sistema de Inventario..."

# Verificar Java
if ! command -v java &> /dev/null; then
    echo "Java no está instalado. Por favor, instale Java 8 o superior."
    exit 1
fi

# Crear directorio de instalación
mkdir -p /opt/sistema-inventario
cp -r * /opt/sistema-inventario/

# Crear script de ejecución
cat > /usr/local/bin/inventario << EOF
#!/bin/bash
cd /opt/sistema-inventario
java -cp src/main/java com.inventario.main.InventarioApp
EOF

chmod +x /usr/local/bin/inventario

echo "Instalación completada."
echo "Ejecute 'inventario' para iniciar el sistema."
```

## 📱 Ejecución en Diferentes Sistemas

### **Windows**
```cmd
# Usar Command Prompt o PowerShell
cd C:\ruta\al\proyecto
javac -cp src/main/java src/main/java/com/inventario/**/*.java
java -cp src/main/java com.inventario.main.InventarioApp
```

### **Linux/Mac**
```bash
# Usar Terminal
cd /ruta/al/proyecto
javac -cp src/main/java src/main/java/com/inventario/**/*.java
java -cp src/main/java com.inventario.main.InventarioApp
```

### **Docker (Opcional)**
```dockerfile
# Dockerfile
FROM openjdk:11-jdk-slim

WORKDIR /app
COPY . .

RUN javac -cp src/main/java src/main/java/com/inventario/**/*.java

CMD ["java", "-cp", "src/main/java", "com.inventario.main.InventarioApp"]
```

```bash
# Construir y ejecutar
docker build -t sistema-inventario .
docker run -it sistema-inventario
```

## 📚 Recursos Adicionales

- [Java Installation Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/install/)
- [Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Git Setup](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup)
- [IDE Setup](https://www.jetbrains.com/help/idea/getting-started.html)

## 🆘 Soporte

Si encuentras problemas durante la ejecución:

1. **Verificar logs de error** en la consola
2. **Revisar configuración de Java** y PATH
3. **Consultar documentación** del proyecto
4. **Crear issue** en GitHub con detalles del error
5. **Contactar al equipo** de desarrollo

### **Información para Reportar Bugs**
- Sistema operativo y versión
- Versión de Java instalada
- IDE utilizado
- Mensaje de error completo
- Pasos para reproducir el problema
