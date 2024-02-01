package com.example.services;

import java.util.List;

import com.example.entities.Telefono;

public interface TelefonoService {

    public List<Telefono> telefonos(int idEmpleado);
    public void eliminarTelefonos(int idEmpleado);
    public void persistirTelefono(int idEmpleado, Telefono telefono);
    public void actualizarTelefono(int idEmpleado, Telefono telefono);
    

}
