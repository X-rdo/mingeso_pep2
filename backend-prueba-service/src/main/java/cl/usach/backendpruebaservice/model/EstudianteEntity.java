package cl.usach.backendpruebaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstudianteEntity {
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