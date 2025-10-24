# 🎨 Ejemplos de Componentes Gráficos Java Swing

## 📋 Componentes Implementados

### **1. Labels (JLabel)**
```java
// Label básico
JLabel lblTitulo = new JLabel("Sistema de Inventario", JLabel.CENTER);
lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
lblTitulo.setForeground(new Color(0, 102, 204));

// Label con icono
JLabel lblIcono = new JLabel("Usuario", new ImageIcon("user.png"), JLabel.LEFT);

// Label con tooltip
JLabel lblPrecio = new JLabel("Precio:");
lblPrecio.setToolTipText("Ingrese el precio en pesos colombianos");
```

### **2. Text Fields (JTextField)**
```java
// Text field básico
JTextField txtNombre = new JTextField(20);
txtNombre.setToolTipText("Ingrese el nombre del producto");

// Text field con formato
JFormattedTextField txtPrecio = new JFormattedTextField(
    new DecimalFormat("#,##0.00"));
txtPrecio.setHorizontalAlignment(JTextField.RIGHT);

// Password field
JPasswordField txtPassword = new JPasswordField(15);
txtPassword.setEchoChar('*');
```

### **3. Buttons (JButton)**
```java
// Button básico
JButton btnGuardar = new JButton("Guardar");
btnGuardar.setBackground(new Color(76, 175, 80));
btnGuardar.setForeground(Color.WHITE);
btnGuardar.setFocusPainted(false);

// Button con icono
JButton btnEliminar = new JButton("Eliminar", new ImageIcon("delete.png"));
btnEliminar.setMnemonic('E'); // Alt+E

// Button con estilo personalizado
JButton btnBuscar = new JButton("Buscar");
btnBuscar.setBorder(BorderFactory.createRaisedBorder());
btnBuscar.setPreferredSize(new Dimension(100, 30));
```

### **4. Radio Buttons (JRadioButton)**
```java
// Radio buttons individuales
JRadioButton rdbElectronica = new JRadioButton("Electrónica");
JRadioButton rdbRopa = new JRadioButton("Ropa");
JRadioButton rdbHogar = new JRadioButton("Hogar");

// Button Group para agrupar
ButtonGroup btnGroupCategoria = new ButtonGroup();
btnGroupCategoria.add(rdbElectronica);
btnGroupCategoria.add(rdbRopa);
btnGroupCategoria.add(rdbHogar);

// Seleccionar por defecto
rdbElectronica.setSelected(true);
```

### **5. Tables (JTable)**
```java
// Modelo de tabla personalizado
public class ProductoTableModel extends AbstractTableModel {
    private String[] columnas = {"ID", "Nombre", "Precio", "Cantidad", "Categoría"};
    private List<Producto> productos;
    
    @Override
    public Object getValueAt(int row, int col) {
        Producto producto = productos.get(row);
        switch (col) {
            case 0: return producto.getId();
            case 1: return producto.getNombre();
            case 2: return "$" + producto.getPrecio();
            case 3: return producto.getCantidad();
            case 4: return producto.getCategoria();
            default: return "";
        }
    }
}

// Configuración de tabla
JTable tablaProductos = new JTable(new ProductoTableModel());
tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
tablaProductos.setRowHeight(25);
tablaProductos.getTableHeader().setReorderingAllowed(false);

// Scroll pane para tabla
JScrollPane scrollPane = new JScrollPane(tablaProductos);
scrollPane.setPreferredSize(new Dimension(600, 300));
```

### **6. Menu Bar (JMenuBar)**
```java
// Crear barra de menú
JMenuBar menuBar = new JMenuBar();

// Menú Archivo
JMenu menuArchivo = new JMenu("Archivo");
JMenuItem menuItemNuevo = new JMenuItem("Nuevo Producto");
JMenuItem menuItemGuardar = new JMenuItem("Guardar");
JMenuItem menuItemSalir = new JMenuItem("Salir");

menuArchivo.add(menuItemNuevo);
menuArchivo.add(menuItemGuardar);
menuArchivo.addSeparator();
menuArchivo.add(menuItemSalir);

// Menú Editar
JMenu menuEditar = new JMenu("Editar");
JMenuItem menuItemEliminar = new JMenuItem("Eliminar");
menuEditar.add(menuItemEliminar);

// Agregar menús a la barra
menuBar.add(menuArchivo);
menuBar.add(menuEditar);

// Asignar barra de menú al frame
setJMenuBar(menuBar);
```

## 🎨 Ejemplo Completo: LoginFrame

```java
public class LoginFrame extends JFrame {
    // Componentes
    private JLabel lblTitulo, lblUsuario, lblPassword;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancelar;
    private JRadioButton rdbAdmin, rdbVendedor;
    private ButtonGroup btnGroupRol;
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuAyuda;
    private JMenuItem menuItemSalir, menuItemAcerca;

    public LoginFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupMenuBar();
        configureFrame();
    }

    private void initializeComponents() {
        // Labels
        lblTitulo = new JLabel("Sistema de Inventario", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204));
        
        lblUsuario = new JLabel("Usuario:");
        lblPassword = new JLabel("Contraseña:");
        
        // Text Fields
        txtUsuario = new JTextField(15);
        txtPassword = new JPasswordField(15);
        
        // Buttons
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(204, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        
        // Radio Buttons
        rdbAdmin = new JRadioButton("Administrador");
        rdbVendedor = new JRadioButton("Vendedor");
        
        btnGroupRol = new ButtonGroup();
        btnGroupRol.add(rdbAdmin);
        btnGroupRol.add(rdbVendedor);
        rdbVendedor.setSelected(true);
        
        // Menu Bar
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
        menuAyuda = new JMenu("Ayuda");
        menuItemSalir = new JMenuItem("Salir");
        menuItemAcerca = new JMenuItem("Acerca de");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(248, 248, 248));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Usuario
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblUsuario, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtUsuario, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(lblPassword, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(txtPassword, gbc);
        
        // Radio buttons para rol
        JPanel panelRol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRol.setBackground(new Color(248, 248, 248));
        panelRol.add(new JLabel("Tipo de usuario:"));
        panelRol.add(rdbAdmin);
        panelRol.add(rdbVendedor);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelRol, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 248, 248));
        panelBotones.add(btnLogin);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelBotones, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        btnLogin.addActionListener(e -> {
            // Lógica de login
            String usuario = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());
            boolean esAdmin = rdbAdmin.isSelected();
            
            // Validar credenciales
            if (validarLogin(usuario, password, esAdmin)) {
                JOptionPane.showMessageDialog(this, "Login exitoso!");
                // Abrir ventana principal
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas!");
            }
        });
        
        btnCancelar.addActionListener(e -> System.exit(0));
        
        // Enter en password field
        txtPassword.addActionListener(e -> {
            btnLogin.doClick();
        });
    }

    private void setupMenuBar() {
        menuArchivo.add(menuItemSalir);
        menuAyuda.add(menuItemAcerca);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
        
        // Eventos del menú
        menuItemSalir.addActionListener(e -> System.exit(0));
        menuItemAcerca.addActionListener(e -> mostrarAcercaDe());
    }

    private void configureFrame() {
        setTitle("Sistema de Inventario - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private boolean validarLogin(String usuario, String password, boolean esAdmin) {
        // Lógica de validación
        return "admin".equals(usuario) && "admin123".equals(password);
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(this,
            "Sistema de Inventario v1.0\n" +
            "Desarrollado para Construcción de Software I\n" +
            "Ingeniería de Software - Segundo Semestre",
            "Acerca de",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
```

## 🎯 Mejores Prácticas para Componentes

### **1. Consistencia Visual**
```java
// Paleta de colores consistente
public class Colores {
    public static final Color PRIMARY = new Color(0, 102, 204);
    public static final Color SUCCESS = new Color(76, 175, 80);
    public static final Color WARNING = new Color(255, 152, 0);
    public static final Color ERROR = new Color(244, 67, 54);
    public static final Color BACKGROUND = new Color(248, 248, 248);
}

// Fuentes consistentes
public class Fuentes {
    public static final Font TITULO = new Font("Arial", Font.BOLD, 18);
    public static final Font SUBTITULO = new Font("Arial", Font.BOLD, 14);
    public static final Font NORMAL = new Font("Arial", Font.PLAIN, 12);
}
```

### **2. Accesibilidad**
```java
// Tooltips informativos
txtPrecio.setToolTipText("Ingrese el precio en pesos colombianos");

// Mnemónicos para teclado
btnGuardar.setMnemonic('G'); // Alt+G

// Atajos de teclado
menuItemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
```

### **3. Validación de Entrada**
```java
// Validación en tiempo real
txtPrecio.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) && c != '.') {
            e.consume();
        }
    }
});

// Validación al perder foco
txtCantidad.addFocusListener(new FocusAdapter() {
    @Override
    public void focusLost(FocusEvent e) {
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad < 0) {
                txtCantidad.setBackground(Color.RED);
                mostrarError("La cantidad no puede ser negativa");
            } else {
                txtCantidad.setBackground(Color.WHITE);
            }
        } catch (NumberFormatException ex) {
            txtCantidad.setBackground(Color.RED);
            mostrarError("La cantidad debe ser un número válido");
        }
    }
});
```

### **4. Responsive Design**
```java
// Layouts flexibles
GridBagConstraints gbc = new GridBagConstraints();
gbc.weightx = 1.0; // Expandir horizontalmente
gbc.fill = GridBagConstraints.HORIZONTAL;

// Tamaños mínimos
setMinimumSize(new Dimension(800, 600));
setPreferredSize(new Dimension(1000, 700));
```

### **5. Manejo de Eventos**
```java
// Eventos con lambda expressions
btnGuardar.addActionListener(e -> guardarProducto());

// Eventos con métodos de referencia
btnEliminar.addActionListener(this::eliminarProducto);

// Eventos con ActionListener tradicional
btnBuscar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        buscarProductos();
    }
});
```

## 🚀 Componentes Avanzados

### **1. JComboBox**
```java
// ComboBox con opciones
String[] categorias = {"Electrónica", "Ropa", "Hogar", "Otros"};
JComboBox<String> cmbCategoria = new JComboBox<>(categorias);
cmbCategoria.setSelectedIndex(0);

// ComboBox con modelo personalizado
DefaultComboBoxModel<Producto> modelo = new DefaultComboBoxModel<>();
JComboBox<Producto> cmbProductos = new JComboBox<>(modelo);
```

### **2. JCheckBox**
```java
// Checkbox individual
JCheckBox chkActivo = new JCheckBox("Producto activo");
chkActivo.setSelected(true);

// Checkbox con evento
chkActivo.addActionListener(e -> {
    boolean activo = chkActivo.isSelected();
    // Lógica según estado
});
```

### **3. JSlider**
```java
// Slider para valores numéricos
JSlider sliderCantidad = new JSlider(0, 100, 10);
sliderCantidad.setMajorTickSpacing(20);
sliderCantidad.setMinorTickSpacing(5);
sliderCantidad.setPaintTicks(true);
sliderCantidad.setPaintLabels(true);
```

### **4. JProgressBar**
```java
// Barra de progreso
JProgressBar progressBar = new JProgressBar(0, 100);
progressBar.setStringPainted(true);
progressBar.setString("Cargando...");

// Simular progreso
Timer timer = new Timer(100, e -> {
    int value = progressBar.getValue();
    if (value < 100) {
        progressBar.setValue(value + 10);
    } else {
        ((Timer) e.getSource()).stop();
    }
});
```

## 📊 Ejemplo de Tabla Completa

```java
public class ProductoTableModel extends AbstractTableModel {
    private String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Cantidad", "Categoría", "Activo"};
    private List<Producto> productos;
    
    public ProductoTableModel() {
        this.productos = new ArrayList<>();
    }
    
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return productos.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnas.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        Producto producto = productos.get(row);
        switch (col) {
            case 0: return producto.getId();
            case 1: return producto.getNombre();
            case 2: return producto.getDescripcion();
            case 3: return "$" + producto.getPrecio();
            case 4: return producto.getCantidad();
            case 5: return producto.getCategoria();
            case 6: return producto.isActivo() ? "Sí" : "No";
            default: return "";
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class;
            case 4: return Integer.class;
            case 6: return Boolean.class;
            default: return String.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 6; // Solo la columna "Activo" es editable
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 6) {
            Producto producto = productos.get(row);
            producto.setActivo((Boolean) value);
            fireTableCellUpdated(row, col);
        }
    }
}
```

## 🎨 Temas y Estilos

### **1. Look and Feel**
```java
// Configurar Look and Feel del sistema
try {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
} catch (Exception e) {
    e.printStackTrace();
}

// Look and Feel personalizado
UIManager.put("Button.background", new Color(0, 102, 204));
UIManager.put("Button.foreground", Color.WHITE);
UIManager.put("TextField.background", Color.WHITE);
UIManager.put("TextField.foreground", Color.BLACK);
```

### **2. Colores Personalizados**
```java
// Tema oscuro
public class TemaOscuro {
    public static final Color FONDO = new Color(45, 45, 45);
    public static final Color TEXTO = new Color(255, 255, 255);
    public static final Color BOTON = new Color(70, 130, 180);
    public static final Color BORDE = new Color(100, 100, 100);
}
```

## 📱 Responsive Design

### **1. Layouts Adaptativos**
```java
// BorderLayout para ventanas principales
setLayout(new BorderLayout());
add(panelNorte, BorderLayout.NORTH);
add(panelCentro, BorderLayout.CENTER);
add(panelSur, BorderLayout.SOUTH);

// GridBagLayout para formularios
GridBagConstraints gbc = new GridBagConstraints();
gbc.weightx = 1.0;
gbc.fill = GridBagConstraints.HORIZONTAL;
```

### **2. Tamaños Responsivos**
```java
// Configurar tamaños
setMinimumSize(new Dimension(800, 600));
setPreferredSize(new Dimension(1000, 700));
setMaximumSize(new Dimension(1200, 800));

// Centrar ventana
setLocationRelativeTo(null);
```

## 🔍 Testing de Componentes

### **1. Pruebas Unitarias**
```java
@Test
public void testLoginFrame() {
    LoginFrame frame = new LoginFrame();
    assertNotNull(frame);
    assertTrue(frame.isVisible());
}

@Test
public void testValidarLogin() {
    LoginFrame frame = new LoginFrame();
    assertTrue(frame.validarLogin("admin", "admin123", true));
    assertFalse(frame.validarLogin("admin", "wrong", true));
}
```

### **2. Pruebas de Integración**
```java
@Test
public void testProductoFrame() {
    ProductoFrame frame = new ProductoFrame();
    frame.txtNombre.setText("Test Product");
    frame.txtPrecio.setText("100.00");
    frame.txtCantidad.setText("10");
    
    // Simular clic en guardar
    frame.btnGuardar.doClick();
    
    // Verificar que se guardó
    assertTrue(frame.modeloTable.getRowCount() > 0);
}
```

## 📚 Recursos Adicionales

- [Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Swing Components](https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html)
- [Layout Managers](https://docs.oracle.com/javase/tutorial/uiswing/layout/index.html)
- [Event Handling](https://docs.oracle.com/javase/tutorial/uiswing/events/index.html)

