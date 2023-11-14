import React, { useState, useEffect } from "react";

import HeaderComponent from "./Headers/HeaderComponent";
import PruebaService from "../services/PruebaService";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import EstudianteService from "../services/EstudianteService";

function ResumenEstadoPagoComponent() {
  const [pruebaEntity, setPruebaEntity] = useState([]);
  const [rut, setRut] = useState("");
  const [rutParaBuscar, setRutParaBuscar] = useState("");

  useEffect(() => {
    if (rutParaBuscar.trim() !== "") {
        setearResumen();
    }
  }, [rutParaBuscar]);

  const setearResumen = async () => {
    try {
      await PruebaService.resumenEstadoPago(rutParaBuscar);
  
      const res = await PruebaService.resumenEstadoPagoMostrar(rutParaBuscar);
      console.log("Response data Cuotas:", res.data);
      setPruebaEntity(res.data);
    } catch (error) {
      console.error("Error al obtener datos:", error);
    }
  };
  
  const refrescar = () => {
    setearResumen();
  };
  
  const buscarResumen = () => {
    // Actualizar la variable para buscar cuando se presiona el botón
    setRutParaBuscar(rut);
    refrescar();
  };
  
  const changeRutHandler = (event) => {
    setRut(event.target.value);
  };

  

  return (
    <div className="general">
      <HeaderComponent />
      <div align="center" className="container-2">
        <h1>
          <b>Listado de Cuotas</b>
        </h1>
        <Form.Group className="mb-3" controlId="rut">
          <Form.Label>Rut:</Form.Label>
          <Form.Control
            type="text"
            name="rut"
            className="agregar"
            value={rut}
            onChange={changeRutHandler}
          />
        </Form.Group>
        {/* Agregar el botón de búsqueda */}
        <Button 
        className="boton"
        variant="primary" onClick={buscarResumen}>
          Buscar
        </Button>
        <table border="1" className="content-table">
          <thead>
            <tr>
              <th>Rut</th>
              <th>Nombres</th>
              <th>Apellidos</th>
              <th>Nro. exámenes rendidos</th>
              <th>Promedio</th>
              <th>Monto total a Pagar</th>
              <th>Tipo Pago</th>
              <th>Total de cuotas</th>
              <th>Cantidad cuotas pagadas</th>
              <th>Monto pagado</th>
              <th>Fecha último pago</th>
              <th>Saldo por pagar</th>
              <th>Nro. Cuotas con retraso</th>
              
            </tr>
          </thead>
          <tbody>
            
              <tr key={pruebaEntity.rutCuota}>
                <td>{pruebaEntity.rut}</td>
                <td>{pruebaEntity.nombre}</td>
                <td>{pruebaEntity.apellido}</td>
                <td>{pruebaEntity.cantExamenesRendidos}</td>
                <td>{pruebaEntity.promedio}</td>
                <td>{pruebaEntity.montoTotalArancel}</td>
                <td>{pruebaEntity.tipoPago}</td>
                <td>{pruebaEntity.cantCuotas}</td>
                <td>{pruebaEntity.cantCuotasPagadas}</td>
                <td>{pruebaEntity.montoPagado}</td>
                <td>{pruebaEntity.ultimoPago}</td>
                <td>{pruebaEntity.montoPorPagar}</td>
                <td>{pruebaEntity.cantCuotasRetrasadas}</td>
            
                
              </tr>
            
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ResumenEstadoPagoComponent;