package cl.usach.backendcuotaservice.controller;


import cl.usach.backendcuotaservice.entity.CuotasEntity;
import cl.usach.backendcuotaservice.model.EstudianteEntity;
import cl.usach.backendcuotaservice.repository.CuotasRepository;
import cl.usach.backendcuotaservice.service.CuotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cuotas")
public class CuotasController {
    @Autowired
    CuotasService cuotasService;

    @Autowired
    CuotasRepository cuotasRepository;

    @GetMapping("/{rut}/{cuotas}")
    public ResponseEntity<List<CuotasEntity>> cuotas(@PathVariable("rut") String rut, @PathVariable("cuotas") String cuotas){
        EstudianteEntity estudianteEntity = cuotasService.findByRut(rut);
        System.out.println(estudianteEntity);
        if(estudianteEntity != null){
            if(cuotasService.findCuotaByRut(estudianteEntity.getRut()).isEmpty()){
                cuotasService.generarCuotas(estudianteEntity, Integer.parseInt(cuotas));
                List<CuotasEntity> cuotasEntities = cuotasService.findCuotaByRut(estudianteEntity.getRut());
                cuotasService.verificarYActualizarMontos(estudianteEntity.getRut());
                System.out.println(cuotasEntities);
                return ResponseEntity.ok(cuotasEntities);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{rut}")
    public ResponseEntity<List<CuotasEntity>> listado(@PathVariable("rut") String rut){
        System.out.println("Listar");
        System.out.println("rut: "+rut+"\n");
        EstudianteEntity estudianteEntity = cuotasService.findByRut(rut);
        System.out.println(estudianteEntity);
        if(estudianteEntity != null){
            cuotasService.verificarYActualizarMontos(rut);
            List<CuotasEntity> cuotasEntities = cuotasService.findCuotaByRut(estudianteEntity.getRut());
            return ResponseEntity.ok(cuotasEntities);
        }
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id_cuota}")
    public ResponseEntity<String> actualizarEstadoPago(@PathVariable("id_cuota") int id_cuota){
        CuotasEntity cuota = cuotasService.findById(id_cuota);
        //cuotasService.cambiarEstadoPago(id_cuota,cuota.getRut());
        cuotasService.cambiarEstadoPago(cuota);
        //ArrayList<CuotasEntity> cuotas = cuotasService.obtenerCutoasByRut(cuotasService.separarRut(numeroYRut));
        return ResponseEntity.ok(null);
    }


    @PutMapping("/actualizarMontos/{rut}")
    public ResponseEntity<String> verificarYActualizarMontosCuotas(@PathVariable("rut") String rut){
        cuotasService.verificarYActualizarMontos(rut);
        return ResponseEntity.ok(null);
    }


    @GetMapping("/datosLong/{rut}")
    public ResponseEntity<List<Long>> datosLong(@PathVariable("rut") String rut){
        List<Long> datosLong = cuotasService.datosLong(rut);
        if(datosLong != null){
            return ResponseEntity.ok(datosLong);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/datosEnteros/{rut}")
    public ResponseEntity<List<Integer>> datosEnteros(@PathVariable("rut") String rut){
        List<Integer> datosEnteros= cuotasService.datosEnteros(rut);
        if(datosEnteros != null){
            return ResponseEntity.ok(datosEnteros);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/ultimoPago/{rut}")
    public ResponseEntity<LocalDate> ultimoPago(@PathVariable("rut") String rut) {
        LocalDate ultimoPagoRealizado = cuotasService.ultimoPago(rut);
        return ResponseEntity.ok(ultimoPagoRealizado);
    }


}
