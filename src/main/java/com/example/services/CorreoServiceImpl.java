package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.CorreoDao;
import com.example.dao.EmpleadoDao;
import com.example.entities.Correo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorreoServiceImpl implements CorreoService{

    private CorreoDao correoDao;
    private EmpleadoDao empleadoDao;
    @Override
    public List<Correo> correos(int idEmpleado) {

        return correoDao.findByEmpleado(empleadoDao.findById(idEmpleado).get());
    }

    @Override
    public void eliminarCorreos(int idEmpleado) {
        correoDao.deleteByEmpleado(empleadoDao.findById(idEmpleado).get());

    }
    @Override
    public void persistirCorreo(int idEmpleado, Correo correo) {
        correo.setEmpleado(empleadoDao.findById(idEmpleado).get());
        correoDao.save(correo);
    }
    @Override
    public void actualizarCorreo(int idEmpleado, Correo correo) {
        correo.setEmpleado(empleadoDao.findById(idEmpleado).get());
        correoDao.save(correo);

    }

    

}
