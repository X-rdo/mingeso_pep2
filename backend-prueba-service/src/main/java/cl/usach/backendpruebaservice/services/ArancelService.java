 package cl.usach.backendpruebaservice.services;

 import cl.usach.backendpruebaservice.model.EstudianteEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
 import org.springframework.web.client.RestTemplate;

 import java.time.LocalDate;
 import java.util.List;

 @Service
public class ArancelService {

     @Autowired
     RestTemplate restTemplate;

    public void verificarMontos(String rut){
        System.out.println("rut: "+rut);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8080/cuotas/actualizarMontos/"+rut,
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

     public List<Long> datosLong(String rut){
         System.out.println("rut: "+rut);
         ResponseEntity<List<Long>> response = restTemplate.exchange(
                 "http://localhost:8080/cuotas/datosLong/"+rut,
                 HttpMethod.GET,
                 null,
                 new ParameterizedTypeReference<List<Long>>() {}
         );
         return response.getBody();
     }

     public List<Integer> datosEntero(String rut){
         System.out.println("rut: "+rut);
         ResponseEntity<List<Integer>> response = restTemplate.exchange(
                 "http://localhost:8080/cuotas/datosEnteros/"+rut,
                 HttpMethod.GET,
                 null,
                 new ParameterizedTypeReference<List<Integer>>() {}
         );
         return response.getBody();
     }

     public EstudianteEntity findByRut(String rut){
         System.out.println("rut: "+rut);
         ResponseEntity<EstudianteEntity> response = restTemplate.exchange(
                 "http://localhost:8080/estudiante/"+rut,
                 HttpMethod.GET,
                 null,
                 new ParameterizedTypeReference<EstudianteEntity>() {}
         );
         return response.getBody();
     }

     public LocalDate ultimoPago(String rut){
         System.out.println("rut: "+rut);
         ResponseEntity<LocalDate> response = restTemplate.exchange(
                 "http://localhost:8080/cuotas/ultimoPago/"+rut,
                 HttpMethod.GET,
                 null,
                 new ParameterizedTypeReference<LocalDate>() {}
         );
         return response.getBody();
     }





}
