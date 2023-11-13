package cl.usach.backendcuotaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "cuotas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuotasEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rut;
    private int numeroCuota;
    private Long monto;
    private Long monto_variable;
    private String estado;
    private LocalDate fecha_cuota; //Tiene que partir de 4 en la primera cuota
    private LocalDate fecha_pago;
}
