package com.inventario.view;

import com.inventario.model.Producto;
import com.inventario.controller.ProductoController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz para la gestión de productos
 * Implementa todos los componentes gráficos requeridos incluyendo Tables
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class ProductoFrame extends JFrame {
    
    // Componentes gráficos requeridos
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblDescripcion;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblCategoria;
    
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    
    private JRadioButton rdbElectronica;
    private JRadioButton rdbRopa;
    private JRadioButton rdbHogar;
    private JRadioButton rdbOtros;
    private ButtonGroup btnGroupCategoria;
    
    private JTable tablaProductos;
    private DefaultTableModel modeloTable;
    private JScrollPane scrollPane;
    
    private JMenuBar menuBar;
    private JMenu menuArchivo;
    private JMenu menuEditar;
    private JMenuItem menuItemNuevo;
    private JMenuItem menuItemGuardar;
    private JMenuItem menuItemEliminar;
    private JMenuItem menuItemSalir;
    
    private ProductoController controller;
    private Producto productoSeleccionado;

    public ProductoFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupMenuBar();
        configureFrame();
        cargarDatos();
    }

    /**
     * Inicializa todos los componentes gráficos
     */
    private void initializeComponents() {
        // Labels
        lblTitulo = new JLabel("Gestión de Productos", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204));
        
        lblNombre = new JLabel("Nombre:");
        lblDescripcion = new JLabel("Descripción:");
        lblPrecio = new JLabel("Precio:");
        lblCantidad = new JLabel("Cantidad:");
        lblCategoria = new JLabel("Categoría:");
        
        // Text Fields
        txtNombre = new JTextField(20);
        txtDescripcion = new JTextField(20);
        txtPrecio = new JTextField(10);
        txtCantidad = new JTextField(10);
        
        // Configurar tooltips
        txtPrecio.setToolTipText("Ingrese el precio en pesos colombianos");
        txtCantidad.setToolTipText("Ingrese la cantidad disponible");
        
        // Buttons
        btnGuardar = crearBoton("Guardar", new Color(76, 175, 80));
        btnActualizar = crearBoton("Actualizar", new Color(33, 150, 243));
        btnEliminar = crearBoton("Eliminar", new Color(244, 67, 54));
        btnLimpiar = crearBoton("Limpiar", new Color(158, 158, 158));
        btnBuscar = crearBoton("Buscar", new Color(255, 152, 0));
        
        // Radio Buttons para categorías
        rdbElectronica = new JRadioButton("Electrónica");
        rdbRopa = new JRadioButton("Ropa");
        rdbHogar = new JRadioButton("Hogar");
        rdbOtros = new JRadioButton("Otros");
        
        btnGroupCategoria = new ButtonGroup();
        btnGroupCategoria.add(rdbElectronica);
        btnGroupCategoria.add(rdbRopa);
        btnGroupCategoria.add(rdbHogar);
        btnGroupCategoria.add(rdbOtros);
        rdbElectronica.setSelected(true);
        
        // Table
        setupTable();
        
        // Menu Bar
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
        menuEditar = new JMenu("Editar");
        menuItemNuevo = new JMenuItem("Nuevo Producto");
        menuItemGuardar = new JMenuItem("Guardar");
        menuItemEliminar = new JMenuItem("Eliminar");
        menuItemSalir = new JMenuItem("Salir");
    }

    /**
     * Configura la tabla de productos
     */
    private void setupTable() {
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Cantidad", "Categoría"};
        modeloTable = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla de solo lectura
            }
        };
        
        tablaProductos = new JTable(modeloTable);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setRowHeight(25);
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        
        // Configurar ancho de columnas
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(200); // Descripción
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(100); // Precio
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(80);  // Cantidad
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(100); // Categoría
        
        scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setPreferredSize(new Dimension(600, 300));
    }

    /**
     * Crea un botón con estilo personalizado
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }

    /**
     * Configura el layout de la interfaz
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior con formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        panelFormulario.setBackground(new Color(248, 248, 248));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(lblNombre, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);
        
        // Fila 2: Descripción
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(lblDescripcion, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtDescripcion, gbc);
        
        // Fila 3: Precio y Cantidad
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(lblPrecio, gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtPrecio, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2;
        panelFormulario.add(lblCantidad, gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtCantidad, gbc);
        
        // Fila 4: Categorías
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(lblCategoria, gbc);
        
        JPanel panelCategorias = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCategorias.setBackground(new Color(248, 248, 248));
        panelCategorias.add(rdbElectronica);
        panelCategorias.add(rdbRopa);
        panelCategorias.add(rdbHogar);
        panelCategorias.add(rdbOtros);
        
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 3;
        panelFormulario.add(panelCategorias, gbc);
        
        // Fila 5: Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(248, 248, 248));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnBuscar);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(panelBotones, gbc);
        
        // Panel central con tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
        // Agregar paneles al frame
        add(panelFormulario, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
    }

    /**
     * Configura los manejadores de eventos
     */
    private void setupEventHandlers() {
        controller = new ProductoController(this);
        
        // Eventos de botones
        btnGuardar.addActionListener(e -> controller.guardarProducto());
        btnActualizar.addActionListener(e -> controller.actualizarProducto());
        btnEliminar.addActionListener(e -> controller.eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> controller.buscarProductos());
        
        // Evento de selección en tabla
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarProducto();
            }
        });
        
        // Eventos del menú
        menuItemNuevo.addActionListener(e -> limpiarFormulario());
        menuItemGuardar.addActionListener(e -> controller.guardarProducto());
        menuItemEliminar.addActionListener(e -> controller.eliminarProducto());
        menuItemSalir.addActionListener(e -> dispose());
    }

    /**
     * Configura la barra de menú
     */
    private void setupMenuBar() {
        menuArchivo.add(menuItemNuevo);
        menuArchivo.add(menuItemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(menuItemSalir);
        
        menuEditar.add(menuItemEliminar);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuEditar);
        
        setJMenuBar(menuBar);
    }

    /**
     * Configura las propiedades del frame
     */
    private void configureFrame() {
        setTitle("Gestión de Productos - Sistema de Inventario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    /**
     * Carga los datos en la tabla
     */
    private void cargarDatos() {
        controller.cargarProductos();
    }

    /**
     * Selecciona un producto de la tabla
     */
    private void seleccionarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (Integer) modeloTable.getValueAt(filaSeleccionada, 0);
            productoSeleccionado = controller.obtenerProductoPorId(id);
            if (productoSeleccionado != null) {
                llenarFormulario(productoSeleccionado);
            }
        }
    }

    /**
     * Llena el formulario con los datos del producto
     */
    private void llenarFormulario(Producto producto) {
        txtNombre.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
        txtPrecio.setText(producto.getPrecio().toString());
        txtCantidad.setText(String.valueOf(producto.getCantidad()));
        
        // Seleccionar categoría
        String categoria = producto.getCategoria();
        switch (categoria) {
            case "Electrónica":
                rdbElectronica.setSelected(true);
                break;
            case "Ropa":
                rdbRopa.setSelected(true);
                break;
            case "Hogar":
                rdbHogar.setSelected(true);
                break;
            default:
                rdbOtros.setSelected(true);
                break;
        }
    }

    /**
     * Limpia el formulario
     */
    public void limpiarFormulario() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        rdbElectronica.setSelected(true);
        productoSeleccionado = null;
        tablaProductos.clearSelection();
    }

    /**
     * Actualiza la tabla con los productos
     */
    public void actualizarTabla(List<Producto> productos) {
        modeloTable.setRowCount(0);
        for (Producto producto : productos) {
            Object[] fila = {
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                "$" + producto.getPrecio(),
                producto.getCantidad(),
                producto.getCategoria()
            };
            modeloTable.addRow(fila);
        }
    }

    // Getters para el controlador
    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public String getDescripcion() {
        return txtDescripcion.getText().trim();
    }

    public String getPrecio() {
        return txtPrecio.getText().trim();
    }

    public String getCantidad() {
        return txtCantidad.getText().trim();
    }

    public String getCategoria() {
        if (rdbElectronica.isSelected()) return "Electrónica";
        if (rdbRopa.isSelected()) return "Ropa";
        if (rdbHogar.isSelected()) return "Hogar";
        return "Otros";
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}

