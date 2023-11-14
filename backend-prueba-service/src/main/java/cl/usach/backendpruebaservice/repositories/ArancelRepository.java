
package cl.usach.backendpruebaservice.repositories;

        import cl.usach.backendpruebaservice.entities.ArancelEntity;
        import cl.usach.backendpruebaservice.entities.PruebaEntity;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;


@Repository
public interface ArancelRepository extends CrudRepository<ArancelEntity, Integer> {

        ArancelEntity findByRut(String rut);
}