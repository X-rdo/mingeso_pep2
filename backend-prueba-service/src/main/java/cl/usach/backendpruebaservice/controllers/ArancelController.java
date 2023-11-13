package cl.usach.backendpruebaservice.controllers;

import cl.usach.backendpruebaservice.model.ArancelEntity;
import cl.usach.backendpruebaservice.model.EstudianteEntity;
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

    @PutMapping("/actualizarMontos/{rut}")
    public ResponseEntity<String> verificarYActualizarMontosGeneral(@PathVariable("rut") String rut){
        arancelService.verificarMontos(rut);
        return ResponseEntity.ok(null);
    }
    @PostMapping("/resumen-estado-pago-datos/{rut}")
    public ResponseEntity<ArancelEntity> resumenEstadoPago(@PathVariable("rut") String rut){
        ArancelEntity arancelMostrar = new ArancelEntity();
        arancelMostrar.setEstudiante(arancelService.findByRut(rut));
        arancelMostrar.setDatosEnteros(arancelService.datosEntero(rut));
        arancelMostrar.setDatosLong(arancelService.datosLong(rut));
        arancelMostrar.setUltimoPago(arancelService.ultimoPago(rut));
        return ResponseEntity.ok(arancelMostrar);
    }

}
