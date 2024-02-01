package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.DepartamentoDao;
import com.example.entities.Departamento;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {

    //Inyección de Dependencia (DI) por constructor
    /*
     * Antiguamente para inyectar una dependencia por constructor
     * 
     * Primero: Declarabas la variable del tipo del objeto.
     * 
     * Segundo: Creabas el constructor y le pasabas el objeto.
     * 
     * Pero, actualmente con LOMBOK lo anterior no es necesario, se facilita,
     * solamente declarando la variable del objeto y especificando el modificador
     * final y utilizando la anotación de lombok @RequiredArgsConstructor
     */

     //Actualmente la inyección de dependencias por constructor se realiza
     // de la forma siguiente y con la anotación de lombok correspondiente

     private final DepartamentoDao departamentoDao;

    @Override
    public List<Departamento> dameDepartamentos() {
        return departamentoDao.findAll();
    }

    @Override
    public Departamento dameUnDepartamento(int idDepartamento) {
    return departamentoDao.findById(idDepartamento).get();
    }

    @Override
    public void persistirDpto(Departamento departamento) {
    departamentoDao.save(departamento);
    }

}
