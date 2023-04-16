package org.cpaz.gestorDeConctactos;

import org.cpaz.gestorDeConctactos.modelo.Usuario;
import org.cpaz.gestorDeConctactos.service.Servicio;
import org.cpaz.gestorDeConctactos.service.UsuarioServicio;

import java.sql.SQLException;
import java.sql.Date;

public class Main {
    public static void main(String[] args) throws SQLException {
        Servicio servicio = new UsuarioServicio();

        System.out.println("INSERTO NUEVO USUARIO");
        Usuario usuario = new Usuario();
        usuario.setNombre("Carolina");
        usuario.setApellido("Paz");
        Date fechaNacimiento = new Date(2000,8,16);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setDomicilio("Buenos Aires, Argentina");
        usuario.setDni("40123123");
        servicio.guardar(usuario);

        System.out.println("Usuario registrado exitosamente: " + usuario.getNombre() + " " + usuario.getApellido());

        System.out.println("---OBTENER POR ID ---");
        Usuario u;
        u=servicio.porId(2L);
        System.out.println(u);


        System.out.println("---MODIFICAR---");

        u.setNombre("Lionel");
        servicio.guardar(u);
        System.out.println(u);

        System.out.println("---LISTAR---");
        System.out.println(servicio.listar());


        /*System.out.println("---ELIMINAR---");
        servicio.eliminar(4L);
        */

        System.out.println("---LISTAR---");
        System.out.println(servicio.listar());
    }
}