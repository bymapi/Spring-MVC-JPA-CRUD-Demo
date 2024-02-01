package com.example.services;

import java.util.List;

import com.example.entities.Departamento;

public interface DepartamentoService {

    public List<Departamento> dameDepartamentos();
    public Departamento dameUnDepartamento(int idDepartamento);
    public void persistirDpto(Departamento departamento);

}
