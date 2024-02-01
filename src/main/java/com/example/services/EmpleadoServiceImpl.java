package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.EmpleadoDao;
import com.example.entities.Empleado;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoDao empleadoDao;

    @Override
    public List<Empleado> dameTodosLosEmpleados() {
        return empleadoDao.findAll();
   }

    @Override
    public Empleado dameUnEmpleado(int idEmpleado) {
        return empleadoDao.findById(idEmpleado).get();
    }

    @Override
    public void eliminarEmpleado(int idEmpleado) {

        empleadoDao.deleteById(idEmpleado);
    }

    @Override
    public void persistirEmpleado(Empleado empleado) {

        empleadoDao.save(empleado);
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) {

        empleadoDao.save(empleado);

    }

}
