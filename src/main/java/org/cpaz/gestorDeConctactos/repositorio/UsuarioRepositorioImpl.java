package org.cpaz.gestorDeConctactos.repositorio;

import org.cpaz.gestorDeConctactos.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class UsuarioRepositorioImpl implements Repositorio<Usuario> {
    private Connection conn;



    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement smt = conn.createStatement();
             ResultSet rs = smt.executeQuery("SELECT * FROM usuarios")){
            while (rs.next()){
                usuarios.add(crearUsuario(rs));
            }
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios as u WHERE u.id = ?"))
        {
            stmt.setLong(1,id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    usuario = crearUsuario(rs);
                }
            }
        }
        return usuario;
    }

    @Override
    public Usuario guardar(Usuario u) throws SQLException {
        String sql = null;

        if (u.getId() != null && u.getId()>0){
            sql = "UPDATE usuarios SET nombre=?, apellido=?, domicilio=?, fechaNacimiento=?, dni=? WHERE id=?";
        } else {
            sql = "INSERT INTO usuarios (nombre, apellido, domicilio, fechaNacimiento, dni) VALUES (?,?,?,?,?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellido());
            stmt.setString(3,u.getDomicilio());
            stmt.setDate(4,(Date) u.getFechaNacimiento());
            stmt.setString(5,u.getDni());

            if (u.getId() != null && u.getId()>0){
                stmt.setLong(6,u.getId());
            }
            stmt.executeUpdate();

            if (u.getId() ==null){
                try (ResultSet rs = stmt.getGeneratedKeys()){
                    if(rs.next()){
                        u.setId(rs.getLong(1));
                    }
                }
            }
        }
        return u;
    }


    @Override
    public void eliminar(Long id) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM usuarios WHERE id=?")){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }
    private static Usuario crearUsuario(ResultSet rs) throws SQLException{
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setDomicilio(rs.getString("domicilio"));
        u.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        u.setDni(rs.getString("dni"));
        return u;
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
