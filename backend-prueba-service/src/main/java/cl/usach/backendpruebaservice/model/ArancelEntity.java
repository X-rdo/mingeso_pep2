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
    private EstudianteEntity estudiante;
    private List<Long> datosLong;
    private List<Integer> datosEnteros;
    private LocalDate ultimoPago;
}
