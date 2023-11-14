package cl.usach.backendcuotaservice.service;

import cl.usach.backendcuotaservice.entity.CuotasEntity;
import cl.usach.backendcuotaservice.model.EstudianteEntity;
import cl.usach.backendcuotaservice.model.PruebaEntity;
import cl.usach.backendcuotaservice.repository.CuotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CuotasService {
    @Autowired
    CuotasRepository cuotasRepository;

    @Autowired
    RestTemplate restTemplate;

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
    public List<CuotasEntity> findCuotaByRut(String rut){
        return cuotasRepository.findCuotaByRut(rut);
    }

    public ArrayList<CuotasEntity> obtenerCuotas(){
        return (ArrayList<CuotasEntity>) cuotasRepository.findAll();
    }

    public ArrayList<CuotasEntity> obtenerCutoasByRut(String rut){
        ArrayList<CuotasEntity> cuotasTotales = (ArrayList<CuotasEntity>) cuotasRepository.findCuotaByRut(rut);
        ArrayList<CuotasEntity> cuotasAnhoActual = new ArrayList<CuotasEntity>();
        int diferencia;
        Calendar calendario = Calendar.getInstance();
        int anhoActual = calendario.get(Calendar.YEAR);

        for (CuotasEntity cuota: cuotasTotales) {
            LocalDate fecha = cuota.getFecha_cuota();
            diferencia = anhoActual - fecha.getYear();
            if(diferencia == 0){
                cuotasAnhoActual.add(cuota);
            }
        }
        return cuotasAnhoActual;

    }


    //metodo que ve si hay cuotas del año actual
    //Si encuentra cuotas del año actual true
    //Si no hay cuotas del año actual false
    public boolean hayCuotasActuales(String rut){
        ArrayList<CuotasEntity> cuotas = (ArrayList<CuotasEntity>) cuotasRepository.findCuotaByRut(rut);
        int diferencia;
        Calendar calendario = Calendar.getInstance();

        int anhoActual = calendario.get(Calendar.YEAR);

        for (CuotasEntity cuota: cuotas) {
            LocalDate fecha = cuota.getFecha_cuota();
            diferencia = anhoActual - fecha.getYear();
            if(diferencia == 0){
                return true;
            }
        }
        return false;

    }

    //capaz puede retornar un 1 o -1 para ver si corrió perfectamente
    public void generarCuotas(EstudianteEntity estudiante, int cantCuotas){
        CuotasEntity cuotaVariable;
        Long arancelEstudiante = estudiante.getArancel();
        Calendar calendario = Calendar.getInstance();
        int anhoActual = calendario.get(Calendar.YEAR);
        int mesPartidaCuotas = 4;
        long cantidadPorCuota;

        if(cantCuotas == -1){
            cantidadPorCuota = (long) (arancelEstudiante*0.5);
        }else{
            cantidadPorCuota= (long) (arancelEstudiante/cantCuotas);
        }
        for(int i = 1;i<cantCuotas + 1;++i){
            cuotaVariable = new CuotasEntity();
            cuotaVariable.setNumeroCuota(i);
            cuotaVariable.setMonto(cantidadPorCuota);
            cuotaVariable.setMonto_variable(cantidadPorCuota);
            cuotaVariable.setEstado("No pagada"); //Recordar cambiar el estado a string para la visualizacion en
            cuotaVariable.setRut(estudiante.getRut());
            cuotaVariable.setFecha_cuota(LocalDate.of(anhoActual,mesPartidaCuotas,5));
            mesPartidaCuotas += 1;
            if (mesPartidaCuotas > 12){
                mesPartidaCuotas = 1;
            }
            cuotasRepository.save(cuotaVariable);
        }

    }

    //Siendo el numero de cuota el primer dato del string[] y el segundo rut
    public String[] separaNumeroYrut(String numeroYRut) {
        String[] valores = numeroYRut.split(",");
        return valores;
    }

    public String separarRut(String numeroYRut) {
        String[] valores = separaNumeroYrut(numeroYRut);
        return valores[1];
    }

    public CuotasEntity findById(int id_cuota){return cuotasRepository.findById(id_cuota);}
    public void cambiarEstadoPago(CuotasEntity cuota){
        ArrayList<CuotasEntity> cuotas = (ArrayList<CuotasEntity>) cuotasRepository.findCuotaByRut(cuota.getRut());
        if (!cuotas.isEmpty()){
            cuota.setEstado("Pagado");
            Calendar calendario = Calendar.getInstance();
            int anho = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            cuota.setFecha_pago(LocalDate.of(anho,mes,dia));
            cuotasRepository.save(cuota);
        }
    }
    /*
    public void cambiarEstadoPago(int id_cuota, String rut){
        ArrayList<CuotasEntity> cuotas = (ArrayList<CuotasEntity>) cuotasRepository.findCuotaByRut(rut);
        if (!cuotas.isEmpty()){
            for(CuotasEntity cuota: cuotas){
                if(cuota.getNumeroCuota() == id_cuota){
                    cuota.setEstado("Pagado");
                    Calendar calendario = Calendar.getInstance();
                    int anho = calendario.get(Calendar.YEAR);
                    int mes = calendario.get(Calendar.MONTH);
                    int dia = calendario.get(Calendar.DAY_OF_MONTH);
                    cuota.setFecha_pago(LocalDate.of(anho,mes,dia));
                    cuotasRepository.save(cuota);
                    break;
                }
            }
        }
    }

     */

    public ArrayList<CuotasEntity> cuotasNoPagadas(ArrayList<CuotasEntity> cuotasTotales){
        ArrayList<CuotasEntity> cuotasNoPagadas = new ArrayList<CuotasEntity>();
        for (CuotasEntity cuota: cuotasTotales) {
            if(cuota.getEstado().equals("No pagada")){
                cuotasNoPagadas.add(cuota);
            }
        }
        return cuotasNoPagadas;
    }

    public Long aplicarInteres(int mesesAtraso, Long monto){
        if(mesesAtraso == 0 || mesesAtraso < 0){
            return monto;
        } else if (mesesAtraso == 1) {
            return (long) (monto * 1.03);
        } else if (mesesAtraso == 2) {
            return (long) (monto * 1.06);
        } else if (mesesAtraso == 3) {
            return (long) (monto * 1.09);
        }else{
            return (long) (monto * 1.15);
        }
    }

    public Long aplicarDescuento(int puntaje, Long monto){
        if(950 <= puntaje && puntaje<= 1000){
            return (long) (monto * 0.9);
        } else if (900 <= puntaje && puntaje<= 949) {
            return (long) (monto * 0.95);
        } else if (850 <= puntaje && puntaje<= 899) {
            return (long) (monto * 0.98);
        }else{
            return monto;
        }
    }



//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//APLICAR EN GENERAR CUOTA
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------

    public List<PruebaEntity> obtenerPruebasEstudiante(String rut) {
        ResponseEntity<List<PruebaEntity>> response = restTemplate.exchange(
                "http://localhost:8080/arancel/prueba/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PruebaEntity>>() {}
        );
        return response.getBody();
    }

    public Integer obtenerPromedio(String rut){
        System.out.println("rut: "+rut);
        ResponseEntity<Integer> response = restTemplate.exchange(
                "http://localhost:8080/arancel/prueba/promedio/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Integer>() {}
        );
        return response.getBody();
    }

    public void verificarYActualizarMontos(String rut){
        ArrayList<CuotasEntity> cuotasEstudiante = cuotasNoPagadas(obtenerCutoasByRut(rut));

        //Intereses atraso
        int mesesAtraso;
        Calendar calendario = Calendar.getInstance();
        int mesActual = calendario.get(Calendar.MONTH) + 1;

        for (CuotasEntity cuota: cuotasEstudiante) {
            mesesAtraso = mesActual- cuota.getFecha_cuota().getMonthValue();
            cuota.setMonto_variable(aplicarInteres(mesesAtraso,cuota.getMonto()));
        }
        int promedio = obtenerPromedio(rut);

        for (CuotasEntity cuota: cuotasEstudiante) {
            cuota.setMonto_variable(aplicarDescuento(promedio,cuota.getMonto_variable()));
            cuotasRepository.save(cuota);
        }
    }

    public void saveEstudiante(EstudianteEntity estudiante) {
        String url = "http://localhost:8080/estudiante/guardar";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EstudianteEntity> requestEntity = new HttpEntity<>(estudiante, headers);

        ResponseEntity<EstudianteEntity> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                EstudianteEntity.class
        );
        responseEntity.getBody();
    }

    public void cambiarTipoPago(String rut, int tipoPagoInt) {
        String tipoPago;
        if (tipoPagoInt == 1) {
            tipoPago = "Contado";
        } else {
            tipoPago = "Cuotas";
        }
        EstudianteEntity estudiante = findByRut(rut);

        estudiante.setTipo_pago(tipoPago);
        saveEstudiante(estudiante);

    }
    public Long montoTotalArancel(String rut){
        Long montoTotal = 0L;
        ArrayList<CuotasEntity> cuotasEstudiante = obtenerCutoasByRut(rut);
        for (CuotasEntity cuota: cuotasEstudiante) {
            montoTotal += cuota.getMonto();
        }
        return montoTotal;
    }

    public int cantidadCuotas(String rut){
        ArrayList<CuotasEntity> cuotas = obtenerCutoasByRut(rut);
        return cuotas.size();
    }

    public int cantidadCuotasPagadas(String rut){
        ArrayList<CuotasEntity> cuotas = obtenerCutoasByRut(rut);
        int cuotasPagadas = 0;
        for (CuotasEntity cuota: cuotas) {
            if (cuota.getEstado().equals("Pagado")) {
                cuotasPagadas += 1;
            }
        }
        return cuotasPagadas;
    }
    public Long montoPagado(String rut){
        ArrayList<CuotasEntity> cuotas = obtenerCutoasByRut(rut);
        Long montoTotal = 0L;
        for (CuotasEntity cuota: cuotas) {
            if(cuota.getEstado().equals("Pagado")){
                montoTotal += cuota.getMonto_variable();
            }
        }
        return montoTotal;
    }
    public Long montoPorPagar(String rut){
        ArrayList<CuotasEntity> cuotas = obtenerCutoasByRut(rut);
        Long montoTotal = 0L;
        for (CuotasEntity cuota: cuotas) {
            if(cuota.getEstado().equals("No pagada")){
                montoTotal += cuota.getMonto_variable();
            }
        }
        return montoTotal;
    }

    //Recordar que si no hay un pago se retorna el 2000-01-01
    public LocalDate ultimoPago(String rut){
        ArrayList<CuotasEntity> cuotas = obtenerCutoasByRut(rut);
        LocalDate ultimoPago = LocalDate.of(2000,1,1);

        for (CuotasEntity cuota: cuotas) {
            if(cuota.getFecha_pago() != null){
                if (ultimoPago.isBefore(cuota.getFecha_pago())){
                    ultimoPago = cuota.getFecha_pago();
                }
            }
        }
        return ultimoPago;
    }
    public int cuotasRetrasadas(String rut) {
        ArrayList<CuotasEntity> cuotasEstudiante = cuotasNoPagadas(obtenerCutoasByRut(rut));

        int cantCuotasAtrasadas = 0;

        int mesesAtraso;
        Long montoVariable;
        Calendar calendario = Calendar.getInstance();
        int mesActual = calendario.get(Calendar.MONTH) + 1;
        for (CuotasEntity cuota : cuotasEstudiante) {
            mesesAtraso = mesActual - cuota.getFecha_cuota().getMonthValue();
            montoVariable = cuota.getMonto() - aplicarInteres(mesesAtraso, cuota.getMonto());
            if(montoVariable<0){
                cantCuotasAtrasadas += 1;
            }
        }
        return cantCuotasAtrasadas;
    }

    public Integer cantidadExamenesRendidos(String rut){
        System.out.println("rut: "+rut);
        ResponseEntity<Integer> response = restTemplate.exchange(
                "http://localhost:8080/arancel/prueba/cantExamenesRendidos/"+rut,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Integer>() {}
        );
        return response.getBody();
    }

    public List<Integer> datosEnteros(String rut){
        List<Integer> datos = new ArrayList<Integer>();
        datos.add(cantidadExamenesRendidos(rut));
        datos.add(obtenerPromedio(rut));
        datos.add(cantidadCuotas(rut));
        datos.add(cantidadCuotasPagadas(rut));
        datos.add(cuotasRetrasadas(rut));
        return datos;
    }
    public List<Long> datosLong(String rut){
        List<Long> datos = new ArrayList<Long>();
        datos.add(montoTotalArancel(rut));
        datos.add(montoPagado(rut));
        datos.add(montoPorPagar(rut));
        return datos;
    }

}
