package cl.usach.backendestudianteservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "estudiantes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstudianteEntity {
    @Id
    @NotNull
    private String rut;
    private String nombres;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private int tipo_colegio;
    private String nombre_colegio;
    private int anio_egreso;
    private LocalDate anio_ingreso;
    private double arancel;
    private String tipo_pago;
}
