 package cl.usach.backendpruebaservice.services;

 import cl.usach.backendpruebaservice.entities.ArancelEntity;
 import cl.usach.backendpruebaservice.model.EstudianteEntity;
 import cl.usach.backendpruebaservice.repositories.ArancelRepository;
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

     @Autowired
     ArancelRepository arancelRepository;

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

     public ArancelEntity guardarArancel(String rut){
         ArancelEntity arancelMostrar = new ArancelEntity();
         arancelMostrar.setRut(findByRut(rut).getRut());
         arancelMostrar.setNombre(findByRut(rut).getNombres());
         arancelMostrar.setApellido(findByRut(rut).getApellidos());
         arancelMostrar.setTipoPago(findByRut(rut).getTipo_pago());

         arancelMostrar.setCantExamenesRendidos(datosEntero(rut).get(0));
         arancelMostrar.setPromedio(datosEntero(rut).get(1));
         arancelMostrar.setCantCuotas(datosEntero(rut).get(2));
         arancelMostrar.setCantCuotasPagadas(datosEntero(rut).get(3));
         arancelMostrar.setCantCuotasRetrasadas(datosEntero(rut).get(4));

         arancelMostrar.setMontoTotalArancel(datosLong(rut).get(0));
         arancelMostrar.setMontoPagado(datosLong(rut).get(1));
         arancelMostrar.setMontoPorPagar(datosLong(rut).get(2));

         arancelMostrar.setUltimoPago(ultimoPago(rut));

         arancelRepository.save(arancelMostrar);

         return arancelMostrar;
    }

     public ArancelEntity arancelActualizado(ArancelEntity arancelMostrar){

         arancelMostrar.setRut(findByRut(arancelMostrar.getRut()).getRut());
         arancelMostrar.setNombre(findByRut(arancelMostrar.getRut()).getNombres());
         arancelMostrar.setApellido(findByRut(arancelMostrar.getRut()).getApellidos());
         arancelMostrar.setTipoPago(findByRut(arancelMostrar.getRut()).getTipo_pago());

         arancelMostrar.setCantExamenesRendidos(datosEntero(arancelMostrar.getRut()).get(0));
         arancelMostrar.setPromedio(datosEntero(arancelMostrar.getRut()).get(1));
         arancelMostrar.setCantCuotas(datosEntero(arancelMostrar.getRut()).get(2));
         arancelMostrar.setCantCuotasPagadas(datosEntero(arancelMostrar.getRut()).get(3));
         arancelMostrar.setCantCuotasRetrasadas(datosEntero(arancelMostrar.getRut()).get(4));

         arancelMostrar.setMontoTotalArancel(datosLong(arancelMostrar.getRut()).get(0));
         arancelMostrar.setMontoPagado(datosLong(arancelMostrar.getRut()).get(1));
         arancelMostrar.setMontoPorPagar(datosLong(arancelMostrar.getRut()).get(2));

         arancelMostrar.setUltimoPago(ultimoPago(arancelMostrar.getRut()));
         arancelRepository.save(arancelMostrar);

         return arancelMostrar;
     }



}
