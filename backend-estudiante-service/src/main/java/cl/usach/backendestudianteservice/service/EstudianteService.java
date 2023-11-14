package cl.usach.backendestudianteservice.service;

import cl.usach.backendestudianteservice.entity.EstudianteEntity;
import cl.usach.backendestudianteservice.entity.TipoColegioEntity;
import cl.usach.backendestudianteservice.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;

    @Autowired
    TipoColegioService tipoColegioService;

    public int findIdByTipo(String tipo){
        TipoColegioEntity tipoColegioEntity = tipoColegioService.findByTipo(tipo);
        return tipoColegioEntity.getId();
    }

    public void save(EstudianteEntity estudianteEntity){
        estudianteRepository.save(estudianteEntity);
    }

    public List<EstudianteEntity> findAll(){
        return estudianteRepository.findAll();
    }

    public EstudianteEntity findByRut(String rut){
        return estudianteRepository.findByRut(rut);
    }

    public ArrayList<EstudianteEntity> obtenerEstudiantes(){
        return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
    }

    public EstudianteEntity guardarEstudiante(EstudianteEntity estudiante){
        estudiante.setArancel(generarArancel(estudiante));
        estudianteRepository.save(estudiante);
        return estudiante;
    }

    public Optional<EstudianteEntity> obtenerPorId(String id){return estudianteRepository.findById(id);}

    public double generarArancel(EstudianteEntity estudiante) {
        double arancel = 1500000;
        //Aplica descuento por tipo de colegio
        if (estudiante.getTipo_colegio() == 1){
            arancel = arancel * 0.8;
        } else if (estudiante.getTipo_colegio() == 2){
            arancel = arancel * 0.9;
        }
        //Aplica descuento por años desde que egresó
        LocalDate current_date = LocalDate.now();
        int current_Year = current_date.getYear();
        int egreso = estudiante.getAnio_egreso();
        if(current_Year - egreso == 0){
            arancel = arancel * 0.85;
        } else if (current_Year - egreso < 3) {
            arancel = arancel * 0.92;
        } else if (current_Year - egreso < 5) {
            arancel = arancel*0.96;
        }
        return arancel;
    }

    public int verificarEstablecimiento(String rutEstudiante){

        Optional<EstudianteEntity> estudiante = estudianteRepository.findById(rutEstudiante);
        if(estudiante.isPresent()){
            return estudiante.get().getTipo_colegio();
        }
        return -1;
    }

    public boolean verificarEstudiante(String rut){
        Optional<EstudianteEntity> estudiante = estudianteRepository.findById(rut);
        if (estudiante.isPresent()){
            return true;
        }else{
            return false;
        }
    }

}