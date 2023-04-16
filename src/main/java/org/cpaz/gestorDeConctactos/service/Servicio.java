package org.cpaz.gestorDeConctactos.service;

import org.cpaz.gestorDeConctactos.modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Servicio {

    List<Usuario> listar() throws SQLException;
    Usuario porId(Long id) throws SQLException;
    Usuario guardar(Usuario usuario)throws SQLException;
    void eliminar(Long id) throws SQLException;
}
