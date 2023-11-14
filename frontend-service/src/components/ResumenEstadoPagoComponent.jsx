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

  const setearResumen = () => {
    PruebaService.resumenEstadoPago(rutParaBuscar).then((res) => {
      console.log("Response data Cuotas:", res.data);
      setPruebaEntity(res.data);
      // Supongamos que el servicio proporciona un estado de pago para cada cuota
    });
  };

  const changeRutHandler = (event) => {
    setRut(event.target.value);
  };

  const buscarResumen = () => {
    // Actualizar la variable para buscar cuando se presiona el botón
    setRutParaBuscar(rut);
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
        <Button className="boton" variant="primary" onClick={buscarResumen}>
          Buscar
        </Button>
        <table border="1" className="content-table">
          <thead>
            <tr>
              <th>Rut estudiant</th>
              <th>Número Cuota</th>
              <th>Monto</th>
              <th>Estado</th>
              <th>Fecha Vencimiento</th>
              <th>Realizar pago</th>
            </tr>
          </thead>
          <tbody>
            {Array.isArray(pruebaEntity) &&
              pruebaEntity.map((resumen) => (
                <tr key={resumen.rutCuota}>
                  <td>{resumen.rut}</td>
                  <td>{resumen.numeroCuota}</td>
                  <td>{resumen.monto_variable}</td>
                  <td>{resumen.estado}</td>
                  <td>{resumen.fecha_cuota}</td>
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ResumenEstadoPagoComponent;
