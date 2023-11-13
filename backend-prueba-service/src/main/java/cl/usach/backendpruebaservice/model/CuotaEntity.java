package cl.usach.backendpruebaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CuotaEntity {
    private String rut;
    private int numeroCuota;
    private double monto;
    private double monto_variable;
    private String estado;
    private LocalDate fecha_cuota; //Tiene que partir de 4 en la primera cuota
    private LocalDate fecha_pago;
}
