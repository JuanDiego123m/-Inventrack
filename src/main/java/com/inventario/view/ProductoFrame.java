package com.inventario.view;

import com.inventario.model.Producto;
import com.inventario.controller.ProductoController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz para la gestión de productos - Diseño Profesional v2.0
 * 
 * @author Equipo de Desarrollo
 * @version 2.0
 */
public class ProductoFrame extends JFrame {
    
    // Paleta de colores profesional (consistente)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color CARD_COLOR = Color.WHITE;
    
    // Tipografía consistente
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    
    private MainFrame mainFrame;
    private ProductoController controller;
    private Producto productoSeleccionado;
    
    // Componentes UI
    private JTextField txtCodigo, txtNombre, txtDescripcion, txtPrecio, txtCantidad;
    private JRadioButton rdbElectronica, rdbRopa, rdbHogar, rdbOtros;
    private ButtonGroup btnGroupCategoria;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnRegresar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public ProductoFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = new ProductoController(this);
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        configureFrame();
        cargarDatos();
    }

    private void initializeComponents() {
        // Campos de texto
        txtCodigo = crearTextField();
        txtNombre = crearTextField();
        txtDescripcion = crearTextField();
        txtPrecio = crearTextField();
        txtCantidad = crearTextField();
        
        // Radio buttons para categoría
        rdbElectronica = crearRadioButton("Electrónica");
        rdbRopa = crearRadioButton("Ropa");
        rdbHogar = crearRadioButton("Hogar");
        rdbOtros = crearRadioButton("Otros");
        
        btnGroupCategoria = new ButtonGroup();
        btnGroupCategoria.add(rdbElectronica);
        btnGroupCategoria.add(rdbRopa);
        btnGroupCategoria.add(rdbHogar);
        btnGroupCategoria.add(rdbOtros);
        rdbElectronica.setSelected(true);
        
        // Botones con colores profesionales
        btnGuardar = crearBotonModerno("💾 Guardar", SUCCESS_COLOR);
        btnActualizar = crearBotonModerno("✏️ Actualizar", PRIMARY_COLOR);
        btnEliminar = crearBotonModerno("🗑️ Eliminar", DANGER_COLOR);
        btnLimpiar = crearBotonModerno("🔄 Limpiar", WARNING_COLOR);
        btnRegresar = crearBotonModerno("⬅️ Regresar al Menú", SECONDARY_COLOR);
        
        // Tabla
        String[] columnas = {"Código", "Nombre", "Descripción", "Precio", "Cantidad", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFont(INPUT_FONT);
        tablaProductos.setRowHeight(30);
        tablaProductos.setGridColor(new Color(224, 224, 224));
        tablaProductos.setSelectionBackground(PRIMARY_COLOR.brighter());
        tablaProductos.setSelectionForeground(Color.WHITE);
        
        // Configurar encabezado de la tabla con renderizador personalizado
        javax.swing.table.JTableHeader header = tablaProductos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(0, 45));
        
        // Renderizador personalizado para asegurar que los colores se apliquen
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setBackground(PRIMARY_COLOR);
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 1, PRIMARY_COLOR.darker()),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
                label.setHorizontalAlignment(JLabel.CENTER);
                return label;
            }
        });
        
        // Implementar zebra striping (filas alternadas) y hover
        tablaProductos.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                
                java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(PRIMARY_COLOR.brighter());
                    c.setForeground(Color.WHITE);
                } else {
                    // Zebra striping: filas pares blancas, impares gris claro
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                    c.setForeground(SECONDARY_COLOR);
                }
                
                // Centrar contenido numérico
                if (value instanceof Number || column == 3 || column == 4) {
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.JLabel.CENTER);
                } else {
                    ((javax.swing.JLabel) c).setHorizontalAlignment(javax.swing.JLabel.LEFT);
                }
                
                return c;
            }
        });
        
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private JTextField crearTextField() {
        JTextField textField = new JTextField();
        textField.setFont(INPUT_FONT);
        textField.setPreferredSize(new Dimension(200, 32));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private JRadioButton crearRadioButton(String texto) {
        JRadioButton radioButton = new JRadioButton(texto);
        radioButton.setFont(INPUT_FONT);
        radioButton.setBackground(CARD_COLOR);
        radioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return radioButton;
    }

    private JButton crearBotonModerno(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(BUTTON_FONT);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Panel superior con título y botón regresar
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(CARD_COLOR);
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, PRIMARY_COLOR),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTitulo = new JLabel("📦 Gestión de Productos");
        lblTitulo.setFont(TITLE_FONT);
        lblTitulo.setForeground(SECONDARY_COLOR);
        
        panelSuperior.add(lblTitulo, BorderLayout.WEST);
        panelSuperior.add(btnRegresar, BorderLayout.EAST);
        
        // Panel de formulario
        JPanel panelFormulario = crearPanelFormulario();
        
        // Panel de tabla
        JPanel panelTabla = crearPanelTabla();
        
        // Agregar todo
        add(panelSuperior, BorderLayout.NORTH);
        add(panelFormulario, BorderLayout.WEST);
        add(panelTabla, BorderLayout.CENTER);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setPreferredSize(new Dimension(350, 0));
        
        // Título del panel
        JLabel lblFormTitulo = new JLabel("Datos del Producto");
        lblFormTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFormTitulo.setForeground(PRIMARY_COLOR);
        lblFormTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblFormTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Campos
        panel.add(crearCampo("Código:", txtCodigo));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(crearCampo("Nombre:", txtNombre));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(crearCampo("Descripción:", txtDescripcion));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(crearCampo("Precio:", txtPrecio));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(crearCampo("Cantidad:", txtCantidad));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Categoría
        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setFont(LABEL_FONT);
        lblCategoria.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblCategoria);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        
        JPanel panelRadios = new JPanel(new GridLayout(2, 2, 5, 5));
        panelRadios.setBackground(CARD_COLOR);
        panelRadios.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelRadios.setMaximumSize(new Dimension(300, 60));
        panelRadios.add(rdbElectronica);
        panelRadios.add(rdbRopa);
        panelRadios.add(rdbHogar);
        panelRadios.add(rdbOtros);
        panel.add(panelRadios);
        
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Sección de botones con título
        JLabel lblAcciones = new JLabel("Acciones:");
        lblAcciones.setFont(LABEL_FONT);
        lblAcciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblAcciones);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Botones de acción con mejor espaciado
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(CARD_COLOR);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(300, 220));
        
        // Agregar botones con espaciado
        btnGuardar.setMaximumSize(new Dimension(300, 45));
        btnActualizar.setMaximumSize(new Dimension(300, 45));
        btnEliminar.setMaximumSize(new Dimension(300, 45));
        btnLimpiar.setMaximumSize(new Dimension(300, 45));
        
        panelBotones.add(btnGuardar);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 8)));
        panelBotones.add(btnActualizar);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 8)));
        panelBotones.add(btnEliminar);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 8)));
        panelBotones.add(btnLimpiar);
        
        panel.add(panelBotones);
        
        return panel;
    }

    private JPanel crearCampo(String label, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(300, 60));
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        textField.setMaximumSize(new Dimension(300, 32));
        
        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(textField);
        
        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel lblTabla = new JLabel("Lista de Productos");
        lblTabla.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTabla.setForeground(PRIMARY_COLOR);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        
        panel.add(lblTabla, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void setupEventHandlers() {
        btnGuardar.addActionListener(e -> controller.guardarProducto());
        btnActualizar.addActionListener(e -> controller.actualizarProducto());
        btnEliminar.addActionListener(e -> controller.eliminarProducto());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRegresar.addActionListener(e -> regresarAlMenu());
        
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tablaProductos.getSelectedRow();
                if (selectedRow >= 0) {
                    controller.seleccionarProducto(selectedRow);
                }
            }
        });
    }

    private void configureFrame() {
        setTitle("Gestión de Productos - Sistema de Inventario");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                regresarAlMenu();
            }
        });
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abrir maximizada
        setResizable(true);
    }

    private void cargarDatos() {
        controller.cargarProductos();
    }

    private void regresarAlMenu() {
        this.dispose();
        mainFrame.mostrar();
    }

    // Getters públicos para el controlador
    public String getCodigo() { return txtCodigo.getText().trim(); }
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getDescripcion() { return txtDescripcion.getText().trim(); }
    public String getPrecio() { return txtPrecio.getText().trim(); }
    public String getCantidad() { return txtCantidad.getText().trim(); }
    
    public String getCategoriaSeleccionada() {
        if (rdbElectronica.isSelected()) return "Electrónica";
        if (rdbRopa.isSelected()) return "Ropa";
        if (rdbHogar.isSelected()) return "Hogar";
        return "Otros";
    }

    public void setCodigo(String codigo) { txtCodigo.setText(codigo); }
    public void setNombre(String nombre) { txtNombre.setText(nombre); }
    public void setDescripcion(String descripcion) { txtDescripcion.setText(descripcion); }
    public void setPrecio(String precio) { txtPrecio.setText(precio); }
    public void setCantidad(String cantidad) { txtCantidad.setText(cantidad); }
    
    public void setCategoria(String categoria) {
        switch (categoria) {
            case "Electrónica": rdbElectronica.setSelected(true); break;
            case "Ropa": rdbRopa.setSelected(true); break;
            case "Hogar": rdbHogar.setSelected(true); break;
            default: rdbOtros.setSelected(true);
        }
    }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }
    public JTable getTablaProductos() { return tablaProductos; }
    
    public Producto getProductoSeleccionado() { return productoSeleccionado; }
    public void setProductoSeleccionado(Producto producto) { this.productoSeleccionado = producto; }

    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        rdbElectronica.setSelected(true);
        productoSeleccionado = null;
        tablaProductos.clearSelection();
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean confirmar(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje, "Confirmar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
