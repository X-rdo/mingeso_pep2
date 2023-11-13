package cl.usach.backendpruebaservice.controllers;

import cl.usach.backendpruebaservice.entities.PruebaEntity;
import cl.usach.backendpruebaservice.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/prueba")
@RestController
public class PruebaController {

    @Autowired
    PruebaService pruebaService;

    @GetMapping("/get-info-archivo")
    public ResponseEntity<List<PruebaEntity>> getFileType1Info() {
        List<PruebaEntity> pruebasEntities = pruebaService.getAllFiles();
        return ResponseEntity.ok(pruebasEntities);
    }

    @PostMapping("/cargar-archivo")
    public void cargarArchivoPost(
            @RequestParam("archivo") MultipartFile archivo) {
        pruebaService.guardarArchivo(archivo);
        pruebaService.leerCsv(archivo.getOriginalFilename());
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<PruebaEntity>> obtenerPruebasEstudiante(@PathVariable("rut") String rut) {
        List<PruebaEntity> pruebasEstudiante = pruebaService.obtenerPruebasEstudiante(rut);
        System.out.println(pruebasEstudiante);
        return ResponseEntity.ok(pruebasEstudiante);
    }
    @GetMapping("/promedio/{rut}")
    public ResponseEntity<Integer> obtenerPromedio(@PathVariable("rut") String rut) {
        List<PruebaEntity> pruebasEstudiante = pruebaService.obtenerPruebasEstudiante(rut);
        Integer promedio = pruebaService.obtenerPromedio(pruebasEstudiante);
        System.out.println(promedio);
        return ResponseEntity.ok(promedio);
    }

    @GetMapping("/cantExamenesRendidos/{rut}")
    public ResponseEntity<Integer> cantExamenesRendidos(@PathVariable("rut") String rut) {
        Integer cantidadExamenes = pruebaService.cantExamenesRendidos(rut);
        System.out.println(cantidadExamenes);
        return ResponseEntity.ok(cantidadExamenes);
    }


}
