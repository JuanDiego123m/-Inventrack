package com.inventario.dao;

import com.inventario.database.DatabaseManager;
import com.inventario.model.Usuario;
import com.inventario.model.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Data Access Object para la gestión de usuarios en la base de datos
 * Implementa operaciones CRUD para usuarios
 * 
 * @author Equipo de Desarrollo
 * @version 1.0
 */
public class UsuarioDAO {
    
    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());
    private final DatabaseManager dbManager;

    public UsuarioDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }

    /**
     * Obtiene todos los usuarios de la base de datos
     * 
     * @return Lista de usuarios
     */
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE activo = TRUE ORDER BY id";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearResultSet(rs));
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    /**
     * Busca un usuario por su ID
     * 
     * @param id ID del usuario
     * @return Usuario encontrado o null
     */
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND activo = TRUE";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener usuario por ID: " + e.getMessage());
        }

        return null;
    }

    /**
     * Busca un usuario por su nombre de usuario
     * 
     * @param username Nombre de usuario
     * @return Usuario encontrado o null
     */
    public Usuario obtenerPorUsername(String username) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND activo = TRUE";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener usuario por username: " + e.getMessage());
        }

        return null;
    }

    /**
     * Autentica un usuario con username y password
     * 
     * @param username Nombre de usuario
     * @param password Contraseña
     * @param esAdmin Si se espera que sea administrador
     * @return Usuario autenticado o null
     */
    public Usuario autenticar(String username, String password, boolean esAdmin) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ? AND activo = TRUE";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapearResultSet(rs);
                    
                    // Verificar que el rol coincida con la selección
                    if (esAdmin && (usuario.esAdministrador() || usuario.esSuperAdministrador())) {
                        return usuario;
                    } else if (!esAdmin && usuario.esVendedor()) {
                        return usuario;
                    }
                }
            }

        } catch (SQLException e) {
            logger.severe("Error al autenticar usuario: " + e.getMessage());
        }

        return null;
    }

    /**
     * Crea un nuevo usuario en la base de datos
     * 
     * @param usuario Usuario a crear
     * @return true si se creó exitosamente
     */
    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password, nombre, email, rol, activo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getRol().getCodigo());
            stmt.setBoolean(6, usuario.isActivo());

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Obtener el ID generado usando last_insert_rowid() (compatible con SQLite)
                try (PreparedStatement lastIdStmt = conn.prepareStatement("SELECT last_insert_rowid()");
                     ResultSet rs = lastIdStmt.executeQuery()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                    }
                }
                logger.info("Usuario creado exitosamente: " + usuario.getUsername() + " (ID: " + usuario.getId() + ")");
                return true;
            }

        } catch (SQLException e) {
            logger.severe("Error al crear usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Actualiza un usuario existente
     * 
     * @param usuario Usuario a actualizar
     * @return true si se actualizó exitosamente
     */
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET username = ?, password = ?, nombre = ?, email = ?, rol = ?, activo = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getRol().getCodigo());
            stmt.setBoolean(6, usuario.isActivo());
            stmt.setInt(7, usuario.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error al actualizar usuario: " + e.getMessage());
        }

        return false;
    }

    /**
     * Elimina un usuario (eliminación lógica)
     * 
     * @param id ID del usuario a eliminar
     * @return true si se eliminó exitosamente
     */
    public boolean eliminar(int id) {
        String sql = "UPDATE usuarios SET activo = FALSE WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.severe("Error al eliminar usuario: " + e.getMessage());
        }

        return false;
    }

    /**
     * Verifica si existe un usuario con el username dado
     * 
     * @param username Nombre de usuario
     * @return true si existe
     */
    public boolean existeUsername(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ? AND activo = TRUE";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            logger.severe("Error al verificar existencia de username: " + e.getMessage());
        }

        return false;
    }

    /**
     * Obtiene usuarios por rol
     * 
     * @param rol Rol a filtrar
     * @return Lista de usuarios con el rol especificado
     */
    public List<Usuario> obtenerPorRol(Rol rol) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol = ? AND activo = TRUE ORDER BY nombre";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rol.getCodigo());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearResultSet(rs));
                }
            }

        } catch (SQLException e) {
            logger.severe("Error al obtener usuarios por rol: " + e.getMessage());
        }

        return usuarios;
    }

    /**
     * Mapea un ResultSet a un objeto Usuario
     * 
     * @param rs ResultSet con los datos del usuario
     * @return Usuario mapeado
     * @throws SQLException Si hay error al mapear
     */
    private Usuario mapearResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        usuario.setRol(Rol.obtenerPorCodigo(rs.getString("rol")));
        usuario.setActivo(rs.getBoolean("activo"));
        return usuario;
    }
}
