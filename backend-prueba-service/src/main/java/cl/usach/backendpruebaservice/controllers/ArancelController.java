package cl.usach.backendpruebaservice.controllers;

import cl.usach.backendpruebaservice.entities.ArancelEntity;
import cl.usach.backendpruebaservice.entities.PruebaEntity;
import cl.usach.backendpruebaservice.model.EstudianteEntity;
import cl.usach.backendpruebaservice.repositories.ArancelRepository;
import cl.usach.backendpruebaservice.services.ArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/arancel")
@RestController
public class ArancelController {

    @Autowired
    ArancelService arancelService;

    @Autowired
    ArancelRepository arancelRepository;

    @PutMapping("/actualizarMontos/{rut}")
    public ResponseEntity<String> verificarYActualizarMontosGeneral(@PathVariable("rut") String rut){
        arancelService.verificarMontos(rut);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/resumen-estado-pago-datos/{rut}")
    public ResponseEntity<ArancelEntity> resumenEstadoPago(@PathVariable("rut") String rut){
        // Verificar si ya existe una entidad con el mismo rut
        ArancelEntity arancelExistente = arancelRepository.findByRut(rut);

        if (arancelExistente != null) {
            // Si ya existe, devolver esa entidad sin crear una nueva
            // faltaria mandar a un servicio que la actualizara nomas
            arancelService.arancelActualizado(arancelExistente);
            return ResponseEntity.ok(arancelExistente);
        } else {
            // Si no existe, crear una nueva entidad y guardarla
            ArancelEntity arancelMostrar = arancelService.guardarArancel(rut);
            return ResponseEntity.ok(arancelMostrar);
        }
    }


    @GetMapping("/resumen/{rut}")
    public ResponseEntity<ArancelEntity> resumenEstadoPagoMostrar(@PathVariable("rut") String rut) {
        ArancelEntity arancelMostrar = new ArancelEntity();
        arancelMostrar = arancelRepository.findByRut(rut);

        return ResponseEntity.ok(arancelMostrar);
    }

}
