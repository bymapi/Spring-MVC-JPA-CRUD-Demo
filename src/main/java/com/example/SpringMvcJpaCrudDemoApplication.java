package com.example;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Genero;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

import lombok.RequiredArgsConstructor;


@SpringBootApplication
@RequiredArgsConstructor
public class SpringMvcJpaCrudDemoApplication implements CommandLineRunner{

	private final EmpleadoService empleadoService;
	private final DepartamentoService departamentoService;
	private final TelefonoService telefonoService;
	private final CorreoService correoService;
	

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcJpaCrudDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Primero: creamos departamento

		Departamento dpt1 = Departamento.builder()
							.nombre("RRHH")
							.build();

		Departamento dpt2 = Departamento.builder()
							.nombre("INFORMATICA")
							.build();
		
		Departamento dpt3 = Departamento.builder()
							.nombre("CONTABILIDAD")
							.build();

		
		// Persistir los departamentos

		departamentoService.persistirDpto(dpt1);
		departamentoService.persistirDpto(dpt2);
		departamentoService.persistirDpto(dpt3);

				// Creamos los empleados

				Empleado emp1 = Empleado.builder()
				.nombre("Emp1")
				.primerApellido("Emp1Ap1")
				.segundoApellido("Emp1Ap1")
				.fechaAlta(LocalDate.of(2000, Month.JANUARY, 12))
				.salario(3000)
				.genero(Genero.MUJER)
				.departamento(departamentoService.dameUnDepartamento(1))
				.build();


				Empleado emp2 = Empleado.builder()
				.nombre("Emp2")
				.primerApellido("Emp2Ap1")
				.segundoApellido("Emp2Ap2")
				.fechaAlta(LocalDate.of(2010, Month.APRIL, 20))
				.salario(700)
				.genero(Genero.HOMBRE)
				.departamento(departamentoService.dameUnDepartamento(2))
				.build();



		// Ahora vamos a crear correos y teléfonos

		List<Telefono> telefonosEmp1 = new ArrayList<>();

			Telefono tlf1Emp1 = Telefono.builder()
				.numero("4329324")
				.build();

			Telefono tlf2Emp1 = Telefono.builder()
				.numero("347317")
				.build();
			
		telefonosEmp1.add(tlf1Emp1);
		telefonosEmp1.add(tlf2Emp1);
		
		List<Telefono> telefonosEmp2 = new ArrayList<>();

			Telefono tlf1Emp2 = Telefono.builder()
				.numero("0428338592")
				.build();
	
			Telefono tlf2Emp2 = Telefono.builder()
				.numero("3204328742")
				.build();


		telefonosEmp2.add(tlf1Emp2);
		telefonosEmp2.add(tlf2Emp2);

		// Creamos los correos
		List<Correo> correosEmp1 = new ArrayList<>();

			Correo correo1emp1 = Correo.builder()
				.correo("emp1@mail1.com")
				.build();
	
			Correo correo2emp1 = Correo.builder()
			.correo("emp1@mail2.com")
			.build();


		correosEmp1.add(correo1emp1);
		correosEmp1.add(correo2emp1);

		List<Correo> correosEmp2 = new ArrayList<>();

		Correo correo1emp2 = Correo.builder()
			.correo("emp2@mail1.com")
			.build();

		Correo correo2emp2 = Correo.builder()
		.correo("emp2@mail2.com")
		.build();


		correosEmp2.add(correo1emp2);
		correosEmp2.add(correo2emp2);



		


	}

}
