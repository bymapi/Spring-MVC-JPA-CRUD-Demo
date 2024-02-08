package com.example.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final EmpleadoService empleadoService;
    private final DepartamentoService departamentoService;
    private final TelefonoService telefonoService;
    private final CorreoService correoService;

    private final Logger LOG = Logger.getLogger("MainController");
    
    // ANTIGUAMENTE
        // Anotación que está esperando una petición request
        // que va a llegar por el método Get
        // Relaciona la petición que va a llegar
    // @GetMapping("/all")
        /*     public ModelAndView dameEmpleados(){
                
                ModelAndView modelo = new ModelAndView("views/listadoEmpleados");

                List<Empleado> empleados = empleadoService.dameTodosLosEmpleados();


                modelo.addObject("empleados", empleados);


                return modelo;
            } */

    /*
    Actualmente
    */ 
    
    @GetMapping("/all")
    public String dameEmpleados (Model model){

        model.addAttribute("empleados",
        empleadoService.dameTodosLosEmpleados());

        return "views/listadoEmpleados";
    }

    /*
     * Cuando se recibe un parámetro conjuntamente con la petición,
     * se procede de esta manera.
     * Se utiliza también actualmente pero menos que
     * enviar una variable en la ruta.
     */

    // Método que reciba como parámetro el ID del Empleado
    // @GetMapping("/detalles")
    // public String detallesUnEmpleadoC (@RequestParam(name = "id") int idEmpleado, Model model) {
        
    //     LOG.info("ID Employé ok: " + idEmpleado);

    //     return "views/empleadoDetalles";
    // }

    @GetMapping("/detalles/{id}")
    public String detallesUnEmpleado (@PathVariable(name = "id") int idEmpleado, Model model) {
        
        LOG.info("ID Employé ok: " + idEmpleado);

        model.addAttribute("empleado", empleadoService.dameUnEmpleado(idEmpleado));

        return "views/empleadoDetalles";
    }

    @GetMapping("/frmAltaModif/")
    public String formularioAlta (Model model){

        // le paso al modelo un objeto empleado vacío totalmente
        Empleado empleado = new Empleado();

        model.addAttribute("empleado",empleado);

        // También los departamentos
        model.addAttribute("departamentos",
            departamentoService.dameDepartamentos());

        return "views/frmAltaModif";

    

    }



    @PostMapping("/persistir")
    @Transactional
    public String persistirEmpleado(@ModelAttribute(name = "empleado") Empleado empleado,
        @RequestParam(name="tlf", required = false) String telefonosRecibidos,
        @RequestParam(name="mails", required = false) String correosRecibidos,
        @RequestParam(name = "file", required = false) MultipartFile imagen){

        // Comprobamos si hemos recibido un archivo (el de la imagen)

        if (!imagen.isEmpty()) {

            // Vamos a trabajar todo el tiempo con NIO.2
            // una URI es parte de la url y lleva delante el tipo de recurso
            // al que vas a acceder
            // Creamos una ruta que directamente no comprobamos si la ruta existe
            // Recuperar la ruta relativa de la carpeta donde voy a almacenar el archivo
            // la ruta relativa siempre es respecto de donde estés trabajando
            // Una ruta es inmutable

           Path imageFolder = Path.of("src/main/resources/static/images");

           // Creamos la ruta absoluta, es todos los directorios que hay desde la raíz
           // la raíz en windows es C, en Linux es /
           // para construir esto en el paquete nio ya hay métodos
           // que a partir de la ruta relativa te saca la absoluta
           // TomCat es el que necesita la ruta completa

           Path rutaAbsoluta = imageFolder.toAbsolutePath();

           //además de la ruta absoluta necesitamos la ruta completa + nombre del archivo recibido

           Path rutaCompleta = Path.of(rutaAbsoluta + "/" + imagen.getOriginalFilename());
           
            // A partir de este punto se pueden generar excepciones
            // lo que me llegue lo voy a guardar en un array de bytes
            // y después a la ruta

            try {

                byte[] bytesImage = imagen.getBytes();
                Files.write(rutaCompleta, bytesImage);

                // Lo que resta es establecer la propiedad foto del empleado
                // al nombre original del archivo recibido

                empleado.setNombreFoto(imagen.getOriginalFilename());

                
            } catch (IOException e) {
                
            }
            
        }

        // Procesar los teléfonos
        
        if (telefonosRecibidos != null) {

            String[] arrayTelefonos = telefonosRecibidos.split(";");
            List<String> numerosTelefonos = Arrays.asList(arrayTelefonos);

            List<Telefono> telefonos = new ArrayList<>();
            
            numerosTelefonos.stream()
                    .forEach(numeroTelefono -> {
                        telefonos.add(Telefono.builder()
                        .numero(numeroTelefono)
                        .empleado(empleado)
                        .build());
                    });

            empleado.setTelefonos(telefonos);
                                            

            
        }

        // Procesar los correos
        if (correosRecibidos != null) {


            String[] arrayCorreos = correosRecibidos.split(";");
            List<String> direccionesCorreos = Arrays.asList(arrayCorreos);

            List<Correo> correos = new ArrayList<>();
            
            direccionesCorreos.stream()
                    .forEach(direccionCorreo-> {
                        correos.add(Correo.builder()
                        .correo(direccionCorreo)
                        .empleado(empleado)
                        .build());
                    });

            empleado.setCorreos(correos);
                                            
            
        }



        empleadoService.persistirEmpleado(empleado);
        return "redirect:/all";

    }

    // para modificar un empleado 

    @GetMapping("/update/{id}")
    @Transactional
    public String modificarEmpleado (@PathVariable(name = "id", required = true) int idEmpleado,
                                        Model model) {
    
    // Recupera el empleado cuyo id se recibe como parámetro
    
    Empleado empleado = empleadoService.dameUnEmpleado(idEmpleado);
    model.addAttribute("empleado", empleado);
    
    // Recupero los departamentos
    List<Departamento> departamentos = departamentoService.dameDepartamentos();
    model.addAttribute("departamentos", departamentos);

    // Construir los números de teléfono (separados por ;) a partir de
    // los teléfonos recibidos conjuntamente con el empleado
    // Y para los correos igual.

    if (empleado.getTelefonos()!= null) {  
    
    String numerosDeTelefono = empleado.getTelefonos().stream()
                                .map(Telefono::getNumero)
                                .collect(Collectors.joining(";"));

        model.addAttribute("numerosDeTelefono", numerosDeTelefono);
        }

    if (empleado.getCorreos()!= null) {  

        String direccionesDeCorreo = empleado.getCorreos().stream()
                                    .map(Correo::getCorreo)
                                    .collect(Collectors.joining(";"));
    
            model.addAttribute("direccionesDeCorreo", direccionesDeCorreo);
            }

    
    
        return "views/frmAltaModif";


    }
    


    //Para suprimir un empleado
    @GetMapping("/delete/{id}")
    @Transactional
    public String suprimirEmpleado (@PathVariable(name = "id", required = true) int idEmpleado
                                        ) {
    
    

        empleadoService.eliminarEmpleado(idEmpleado);

        return "redirect:/all";

}

}
