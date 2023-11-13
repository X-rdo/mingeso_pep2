package cl.usach.backendpruebaservice.repositories;

import cl.usach.backendpruebaservice.entities.PruebaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PruebaRepository extends CrudRepository<PruebaEntity, Long> {

    List<PruebaEntity> findByRut(String rut);
}
