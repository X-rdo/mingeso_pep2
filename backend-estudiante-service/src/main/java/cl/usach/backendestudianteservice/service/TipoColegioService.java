package cl.usach.backendestudianteservice.service;

import cl.usach.backendestudianteservice.entity.TipoColegioEntity;
import cl.usach.backendestudianteservice.repository.TipoColegioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoColegioService {
    @Autowired
    TipoColegioRepository tipoColegioRepository;

    public TipoColegioEntity findByTipo(String tipo){
        TipoColegioEntity tipoColegioEntity = tipoColegioRepository.findByTipo(tipo);
        return tipoColegioEntity;
    }
}
