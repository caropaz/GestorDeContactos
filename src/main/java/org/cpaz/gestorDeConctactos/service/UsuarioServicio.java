package org.cpaz.gestorDeConctactos.service;

import org.cpaz.gestorDeConctactos.modelo.Usuario;
import org.cpaz.gestorDeConctactos.repositorio.Repositorio;
import org.cpaz.gestorDeConctactos.repositorio.UsuarioRepositorioImpl;
import org.cpaz.gestorDeConctactos.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioServicio implements Servicio{
    private Repositorio<Usuario> usuarioRepositorio;

    public UsuarioServicio() {
        this.usuarioRepositorio = new UsuarioRepositorioImpl();
    }

    @Override
    public List<Usuario> listar() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            usuarioRepositorio.setConn(conn);
            return usuarioRepositorio.listar();
        }
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()){
            usuarioRepositorio.setConn(conn);
            return usuarioRepositorio.porId(id);
        }
    }

    @Override
    public Usuario guardar(Usuario usuario) throws SQLException {

        try (Connection conn = ConexionBaseDatos.getConnection()){
            usuarioRepositorio.setConn(conn);
            if (conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            Usuario nuevoUsuario = null;
            try {
                nuevoUsuario = usuarioRepositorio.guardar(usuario);
                conn.commit();
            }catch (SQLException e){
                conn.rollback();
                e.printStackTrace();
            }
            return nuevoUsuario;
        }

    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            usuarioRepositorio.setConn(conn);
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                usuarioRepositorio.eliminar(id);
                conn.commit();
            }catch (SQLException e){
                conn.rollback();
                e.printStackTrace();
            }
        }
    }

}
