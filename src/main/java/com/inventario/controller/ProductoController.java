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
                view.mostrarError("Error al guardar el producto.");
            }

        } catch (Exception e) {
            view.mostrarError("Error al guardar producto: " + e.getMessage());
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
                view.mostrarError("Error al actualizar el producto.");
            }

        } catch (Exception e) {
            view.mostrarError("Error al actualizar producto: " + e.getMessage());
        }
    }

    /**
     * Elimina un producto del sistema
     */
    public void eliminarProducto() {
        try {
            Producto productoSeleccionado = view.getProductoSeleccionado();
            if (productoSeleccionado == null) {
                view.mostrarError("Seleccione un producto para eliminar.");
                return;
            }

            // Confirmar eliminación
            int opcion = javax.swing.JOptionPane.showConfirmDialog(
                view,
                "¿Está seguro que desea eliminar el producto '" + 
                productoSeleccionado.getNombre() + "'?",
                "Confirmar Eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION
            );

            if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                if (service.eliminarProducto(productoSeleccionado.getId())) {
                    view.mostrarMensaje("Producto eliminado exitosamente.");
                    view.limpiarFormulario();
                    cargarProductos();
                } else {
                    view.mostrarError("Error al eliminar el producto.");
                }
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
            String categoria = view.getCategoria();
            
            productos = service.buscarProductos(nombre, categoria);
            view.actualizarTabla(productos);
            
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
            view.actualizarTabla(productos);
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
        producto.setNombre(view.getNombre());
        producto.setDescripcion(view.getDescripcion());
        producto.setPrecio(new BigDecimal(view.getPrecio()));
        producto.setCantidad(Integer.parseInt(view.getCantidad()));
        producto.setCategoria(view.getCategoria());
        return producto;
    }

    /**
     * Actualiza un producto con los datos del formulario
     */
    private void actualizarProductoDesdeFormulario(Producto producto) {
        producto.setNombre(view.getNombre());
        producto.setDescripcion(view.getDescripcion());
        producto.setPrecio(new BigDecimal(view.getPrecio()));
        producto.setCantidad(Integer.parseInt(view.getCantidad()));
        producto.setCategoria(view.getCategoria());
    }
}

