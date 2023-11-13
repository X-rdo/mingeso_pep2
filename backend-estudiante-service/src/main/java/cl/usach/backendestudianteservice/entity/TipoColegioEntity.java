package cl.usach.backendestudianteservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tipo_colegio")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TipoColegioEntity {

    @Id
    @NotNull
    private int id;
    private String tipo;
}
