package com.inventario.controller;

import com.inventario.model.Producto;
import com.inventario.service.ProductoService;
import com.inventario.view.ProductoFrame;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador para la gestión de productos
 * Implementa la lógica de negocio y coordinación entre vista y modelo
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class ProductoController {
    
    private ProductoFrame view;
    private ProductoService service;
    private List<Producto> productos;

    public ProductoController(ProductoFrame view) {
        this.view = view;
        this.service = new ProductoService();
    }

    /**
     * Guarda un nuevo producto en el sistema
     */
    public void guardarProducto() {
        try {
            // Validar campos obligatorios
            if (!validarCampos()) {
                return;
            }

            // Crear producto
            Producto producto = crearProductoDesdeFormulario();
            
            // Validar producto
            if (!service.validarProducto(producto)) {
                view.mostrarError("El producto no cumple con los criterios de validación.");
                return;
            }

            // Guardar producto
            if (service.guardarProducto(producto)) {
                view.mostrarMensaje("Producto guardado exitosamente.");
                view.limpiarFormulario();
                cargarProductos();
            } else {
                view.mostrarError("Error al guardar el producto.\nVerifique que el código no esté duplicado.");
            }

        } catch (NumberFormatException e) {
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("Formato de precio inválido")) {
                view.mostrarError("Formato de precio inválido.\n\n" +
                                "Formatos válidos:\n" +
                                "• 1000\n" +
                                "• 1000.50\n" +
                                "• 1,000.50\n" +
                                "• 1.000,50\n\n" +
                                "No use letras ni símbolos especiales.");
            } else {
                view.mostrarError("Error en el formato de los datos:\n" +
                                "• Precio: solo números y punto/coma decimal\n" +
                                "• Cantidad: solo números enteros\n\n" +
                                "Detalle: " + mensaje);
            }
        } catch (Exception e) {
            String mensaje = e.getMessage();
            if (mensaje != null && (mensaje.contains("UNIQUE constraint failed") || mensaje.contains("already exists"))) {
                view.mostrarError("El código del producto ya existe.\nPor favor, ingrese un código único.");
            } else {
                view.mostrarError("Error al guardar producto: " + mensaje);
            }
        }
    }

    /**
     * Actualiza un producto existente
     */
    public void actualizarProducto() {
        try {
            Producto productoSeleccionado = view.getProductoSeleccionado();
            if (productoSeleccionado == null) {
                view.mostrarError("Seleccione un producto para actualizar.");
                return;
            }

            // Validar campos
            if (!validarCampos()) {
                return;
            }

            // Actualizar datos del producto
            actualizarProductoDesdeFormulario(productoSeleccionado);

            // Validar producto actualizado
            if (!service.validarProducto(productoSeleccionado)) {
                view.mostrarError("El producto no cumple con los criterios de validación.");
                return;
            }

            // Actualizar en el servicio
            if (service.actualizarProducto(productoSeleccionado)) {
                view.mostrarMensaje("Producto actualizado exitosamente.");
                view.limpiarFormulario();
                cargarProductos();
            } else {
                view.mostrarError("Error al actualizar el producto.\nVerifique que el código no esté duplicado.");
            }

        } catch (NumberFormatException e) {
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("Formato de precio inválido")) {
                view.mostrarError("Formato de precio inválido.\n\n" +
                                "Formatos válidos:\n" +
                                "• 1000\n" +
                                "• 1000.50\n" +
                                "• 1,000.50\n" +
                                "• 1.000,50\n\n" +
                                "No use letras ni símbolos especiales.");
            } else {
                view.mostrarError("Error en el formato de los datos:\n" +
                                "• Precio: solo números y punto/coma decimal\n" +
                                "• Cantidad: solo números enteros\n\n" +
                                "Detalle: " + mensaje);
            }
        } catch (Exception e) {
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("UNIQUE constraint failed")) {
                view.mostrarError("El código del producto ya existe.\nPor favor, ingrese un código único.");
            } else {
                view.mostrarError("Error al actualizar producto: " + mensaje);
            }
        }
    }

    /**
     * Elimina un producto del sistema con doble confirmación
     */
    public void eliminarProducto() {
        try {
            Producto productoSeleccionado = view.getProductoSeleccionado();
            if (productoSeleccionado == null) {
                view.mostrarError("Seleccione un producto para eliminar.");
                return;
            }

            // PRIMERA CONFIRMACIÓN
            int primeraConfirmacion = javax.swing.JOptionPane.showConfirmDialog(
                view,
                "¿Está seguro que desea eliminar el producto?\n\n" + 
                "Código: " + productoSeleccionado.getCodigo() + "\n" +
                "Nombre: " + productoSeleccionado.getNombre() + "\n" +
                "Precio: $" + productoSeleccionado.getPrecio() + "\n\n" +
                "Esta acción no se puede deshacer.",
                "⚠️ Confirmar Eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
            );

            if (primeraConfirmacion != javax.swing.JOptionPane.YES_OPTION) {
                return; // Usuario canceló
            }

            // SEGUNDA CONFIRMACIÓN
            int segundaConfirmacion = javax.swing.JOptionPane.showConfirmDialog(
                view,
                "⚠️ ÚLTIMA ADVERTENCIA ⚠️\n\n" +
                "¿Realmente desea eliminar el producto '" + 
                productoSeleccionado.getNombre() + "'?\n\n" +
                "Esta acción es IRREVERSIBLE y eliminará:\n" +
                "• El producto del inventario\n" +
                "• Todas sus referencias en el sistema\n\n" +
                "¿Está COMPLETAMENTE seguro?",
                "🚨 CONFIRMACIÓN FINAL",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.ERROR_MESSAGE
            );

            if (segundaConfirmacion == javax.swing.JOptionPane.YES_OPTION) {
                if (service.eliminarProducto(productoSeleccionado.getId())) {
                    view.mostrarMensaje("✅ Producto eliminado exitosamente.\n\n" +
                                      "Código: " + productoSeleccionado.getCodigo() + "\n" +
                                      "Nombre: " + productoSeleccionado.getNombre());
                    view.limpiarFormulario();
                    cargarProductos();
                } else {
                    view.mostrarError("Error al eliminar el producto.\nPor favor, intente nuevamente.");
                }
            } else {
                view.mostrarMensaje("Eliminación cancelada.\nEl producto no ha sido eliminado.");
            }

        } catch (Exception e) {
            view.mostrarError("Error al eliminar producto: " + e.getMessage());
        }
    }

    /**
     * Busca productos según criterios
     */
    public void buscarProductos() {
        try {
            String nombre = view.getNombre();
            String categoria = view.getCategoriaSeleccionada();
            
            productos = service.buscarProductos(nombre, categoria);
            actualizarTablaProductos(productos);
            
            if (productos.isEmpty()) {
                view.mostrarMensaje("No se encontraron productos con los criterios especificados.");
            }

        } catch (Exception e) {
            view.mostrarError("Error al buscar productos: " + e.getMessage());
        }
    }

    /**
     * Carga todos los productos en la tabla
     */
    public void cargarProductos() {
        try {
            productos = service.obtenerTodosProductos();
            actualizarTablaProductos(productos);
        } catch (Exception e) {
            view.mostrarError("Error al cargar productos: " + e.getMessage());
        }
    }

    /**
     * Obtiene un producto por su ID
     */
    public Producto obtenerProductoPorId(int id) {
        return service.obtenerProductoPorId(id);
    }

    /**
     * Valida que todos los campos obligatorios estén completos
     */
    private boolean validarCampos() {
        String nombre = view.getNombre();
        String precio = view.getPrecio();
        String cantidad = view.getCantidad();

        if (nombre.isEmpty()) {
            view.mostrarError("El nombre del producto es obligatorio.");
            return false;
        }

        if (precio.isEmpty()) {
            view.mostrarError("El precio del producto es obligatorio.");
            return false;
        }

        if (cantidad.isEmpty()) {
            view.mostrarError("La cantidad del producto es obligatoria.");
            return false;
        }

        // Validar formato de precio
        try {
            BigDecimal precioDecimal = new BigDecimal(precio);
            if (precioDecimal.compareTo(BigDecimal.ZERO) <= 0) {
                view.mostrarError("El precio debe ser mayor a cero.");
                return false;
            }
        } catch (NumberFormatException e) {
            view.mostrarError("El precio debe ser un número válido.");
            return false;
        }

        // Validar formato de cantidad
        try {
            int cantidadInt = Integer.parseInt(cantidad);
            if (cantidadInt < 0) {
                view.mostrarError("La cantidad no puede ser negativa.");
                return false;
            }
        } catch (NumberFormatException e) {
            view.mostrarError("La cantidad debe ser un número entero válido.");
            return false;
        }

        return true;
    }

    /**
     * Crea un producto desde los datos del formulario
     */
    private Producto crearProductoDesdeFormulario() {
        Producto producto = new Producto();
        producto.setCodigo(view.getCodigo());
        producto.setNombre(view.getNombre());
        producto.setDescripcion(view.getDescripcion());
        
        // Limpiar formato del precio antes de convertir
        String precioLimpio = limpiarFormatoPrecio(view.getPrecio());
        producto.setPrecio(new BigDecimal(precioLimpio));
        
        producto.setCantidad(Integer.parseInt(view.getCantidad()));
        producto.setCategoria(view.getCategoriaSeleccionada());
        return producto;
    }

    /**
     * Actualiza un producto con los datos del formulario
     */
    private void actualizarProductoDesdeFormulario(Producto producto) {
        producto.setCodigo(view.getCodigo());
        producto.setNombre(view.getNombre());
        producto.setDescripcion(view.getDescripcion());
        
        // Limpiar formato del precio antes de convertir
        String precioLimpio = limpiarFormatoPrecio(view.getPrecio());
        producto.setPrecio(new BigDecimal(precioLimpio));
        
        producto.setCantidad(Integer.parseInt(view.getCantidad()));
        producto.setCategoria(view.getCategoriaSeleccionada());
    }
    
    /**
     * Limpia el formato del precio para convertirlo a BigDecimal
     * Elimina símbolos de moneda, comas y espacios
     * 
     * @param precio Precio con formato (ej: "$1,000.00" o "1.000,00")
     * @return Precio limpio (ej: "1000.00")
     */
    private String limpiarFormatoPrecio(String precio) {
        if (precio == null || precio.trim().isEmpty()) {
            return "0";
        }
        
        // Eliminar espacios, símbolos de moneda y otros caracteres no numéricos
        String limpio = precio.trim()
            .replace("$", "")
            .replace("€", "")
            .replace("£", "")
            .replace("¥", "")
            .replace(" ", "")
            .replace("\u00A0", ""); // Non-breaking space
        
        // Detectar si usa coma como separador decimal (formato europeo)
        // Ej: "1.000,50" -> tiene punto antes de la coma
        if (limpio.contains(",") && limpio.contains(".")) {
            // Si el punto está antes de la coma, es formato europeo
            if (limpio.indexOf(".") < limpio.indexOf(",")) {
                // Formato: 1.000,50 -> 1000.50
                limpio = limpio.replace(".", "").replace(",", ".");
            } else {
                // Formato: 1,000.50 -> 1000.50
                limpio = limpio.replace(",", "");
            }
        } else if (limpio.contains(",")) {
            // Solo tiene coma, puede ser decimal o separador de miles
            // Si hay más de una coma o está lejos del final, es separador de miles
            long comaPos = limpio.indexOf(",");
            if (limpio.length() - comaPos <= 3) {
                // Probablemente es decimal: 1000,50 -> 1000.50
                limpio = limpio.replace(",", ".");
            } else {
                // Probablemente es separador de miles: 1,000 -> 1000
                limpio = limpio.replace(",", "");
            }
        }
        
        // Validar que solo contenga dígitos y punto decimal
        if (!limpio.matches("^\\d+(\\.\\d+)?$")) {
            throw new NumberFormatException("Formato de precio inválido: " + precio);
        }
        
        return limpio;
    }
    
    /**
     * Actualiza la tabla de productos en la vista
     */
    private void actualizarTablaProductos(List<Producto> productos) {
        view.getModeloTabla().setRowCount(0);
        for (Producto producto : productos) {
            Object[] fila = {
                producto.getCodigo(),
                producto.getNombre(),
                producto.getDescripcion(),
                "$" + producto.getPrecio(),
                producto.getCantidad(),
                producto.getCategoria()
            };
            view.getModeloTabla().addRow(fila);
        }
    }
    
    /**
     * Selecciona un producto de la tabla para editar
     */
    public void seleccionarProducto(int filaSeleccionada) {
        if (filaSeleccionada >= 0 && filaSeleccionada < productos.size()) {
            Producto producto = productos.get(filaSeleccionada);
            view.setProductoSeleccionado(producto);
            
            // Llenar formulario
            view.setCodigo(producto.getCodigo());
            view.setNombre(producto.getNombre());
            view.setDescripcion(producto.getDescripcion());
            view.setPrecio(producto.getPrecio().toString());
            view.setCantidad(String.valueOf(producto.getCantidad()));
            view.setCategoria(producto.getCategoria());
        }
    }
}

