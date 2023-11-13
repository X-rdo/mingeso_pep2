package cl.usach.backendestudianteservice.repository;

import cl.usach.backendestudianteservice.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, String> {
    @Query("select e from EstudianteEntity e where e.rut = :rut")
    EstudianteEntity findByRut(@Param("rut") String rut);
}
