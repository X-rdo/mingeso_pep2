package cl.usach.backendcuotaservice.repository;

import cl.usach.backendcuotaservice.entity.CuotasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotasRepository extends JpaRepository<CuotasEntity, Integer> {
    /*
    @Query("select e from CuotasEntity e where e.rut = :rut")
    List<CuotasEntity> findCuotaByRut(@Param("rut") String rut);
     */
    List<CuotasEntity> findCuotaByRut(String rut);
    CuotasEntity findById(int id_cuota);
}
