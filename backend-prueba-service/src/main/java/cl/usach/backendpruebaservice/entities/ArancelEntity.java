package cl.usach.backendpruebaservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "arancel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArancelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para que se genere los valores de forma incremental
    @Column(unique = true, nullable = false) //valores unicos y que estos no sean falsos
    private int id;
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