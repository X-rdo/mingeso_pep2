package cl.usach.backendpruebaservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArancelEntity {
    private String rut;
    private String nombre;
    private String apellido;
    private String tipoPago;

    private int cantExamenesRendidos;
    private int promedio;
    private int cantCuotas;
    private int cantCuotasPagadas;
    private int cantCuotasRetrasadas;

    private long montoTotalArancel;
    private long montoPagado;
    private long montoPorPagar;

    private LocalDate ultimoPago;
}
