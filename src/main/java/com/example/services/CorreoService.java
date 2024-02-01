package com.example.services;

import java.util.List;

import com.example.entities.Correo;



public interface CorreoService {
    public List<Correo> correos(int idEmpleado);
    public void eliminarCorreos(int idEmpleado);
    public void persistirCorreo(int idEmpleado, Correo correo);
    public void actualizarCorreo(int idEmpleado, Correo correo);

}
