package com.example.services;

import java.util.List;

import com.example.entities.Empleado;

public interface EmpleadoService {

    public List<Empleado> dameTodosLosEmpleados();
    public Empleado dameUnEmpleado(int idEmpleado);
    public void eliminarEmpleado(int idEmpleado);
    public void persistirEmpleado(Empleado empleado);
    public void actualizarEmpleado(Empleado empleado);

}
