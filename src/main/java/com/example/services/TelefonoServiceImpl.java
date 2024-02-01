
package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.EmpleadoDao;
import com.example.dao.TelefonoDao;
import com.example.entities.Telefono;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelefonoServiceImpl implements TelefonoService {

    private final TelefonoDao telefonoDao;
    private final EmpleadoDao empleadoDao;

    @Override
    public List<Telefono> telefonos(int idEmpleado) {

        return telefonoDao.findByEmpleado(empleadoDao.findById(idEmpleado).get());
    }


    @Override
    public void persistirTelefono(int idEmpleado,Telefono telefono ) {
        telefono.setEmpleado(empleadoDao.findById(idEmpleado).get());
        telefonoDao.save(telefono);
    }

    @Override
    public void actualizarTelefono(int idEmpleado, Telefono telefono) {

        telefono.setEmpleado(empleadoDao.findById(idEmpleado).get());
        telefonoDao.save(telefono);
    }

    @Override
    public void eliminarTelefonos(int idEmpleado) {
        telefonoDao.deleteByEmpleado(empleadoDao.findById(idEmpleado).get());
    }

}
