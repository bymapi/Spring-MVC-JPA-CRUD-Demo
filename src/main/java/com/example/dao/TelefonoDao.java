package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Empleado;
import com.example.entities.Telefono;
import java.util.List;


@Repository
public interface TelefonoDao extends JpaRepository<Telefono, Integer> {

    List<Telefono> findByEmpleado(Empleado empleado);
    void deleteByEmpleado(Empleado empleado);

}
