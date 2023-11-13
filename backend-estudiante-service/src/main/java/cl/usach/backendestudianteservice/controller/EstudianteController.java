package cl.usach.backendestudianteservice.controller;

import cl.usach.backendestudianteservice.entity.EstudianteEntity;
import cl.usach.backendestudianteservice.repository.EstudianteRepository;
import cl.usach.backendestudianteservice.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {
    @Autowired
    EstudianteService estudianteService;

    @Autowired
    EstudianteRepository estudianteRepository;

    @PostMapping()
    public ResponseEntity<EstudianteEntity> newEstudiante(@RequestBody EstudianteEntity estudiante) {
        LocalDate anio_ingreso = LocalDate.now();
        estudiante.setAnio_ingreso(anio_ingreso);
        estudianteService.guardarEstudiante(estudiante);
        return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/")
    public ResponseEntity<List<EstudianteEntity>> listar() {
        List<EstudianteEntity> estudianteEntities = estudianteService.findAll();
        return ResponseEntity.ok(estudianteEntities);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<EstudianteEntity> findByRut(@PathVariable("rut") String rut) {
        EstudianteEntity estudianteEntity = estudianteService.findByRut(rut);
        System.out.println(estudianteEntity);
        return ResponseEntity.ok(estudianteEntity);
    }

    @GetMapping("/guardar")
    public ResponseEntity<EstudianteEntity> findByRut(EstudianteEntity estudiante) {
        EstudianteEntity estudianteEntity = estudianteRepository.save(estudiante);
        System.out.println(estudianteEntity);
        return ResponseEntity.ok(estudianteEntity);
    }
}
