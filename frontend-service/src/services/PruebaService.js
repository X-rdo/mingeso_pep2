import axios from 'axios';

const CUOTAS_API_URL = "http://localhost:8080/arancel/";
const CUOTAS_API_URL2 = "http://localhost:8080/arancel/prueba/";

class PruebaService {

    cargarArchivoPost(file){
        return axios.post (CUOTAS_API_URL2 + "cargar-archivo",file);
    }

    getFileType1Info(){
        return axios.get(CUOTAS_API_URL2 + "get-info-archivo");
    }
    resumenEstadoPagoMostrar(rut){
        return axios.get(CUOTAS_API_URL + "resumen/" + rut);
    }

    resumenEstadoPago(rut){
        return axios.post (CUOTAS_API_URL + "resumen-estado-pago-datos/"+rut);
    }

}
    
export default new PruebaService()