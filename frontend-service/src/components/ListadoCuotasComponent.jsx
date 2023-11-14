import React, { useState, useEffect } from "react";
import EstudianteService from "../services/EstudianteService";
import HeaderComponent from "./Headers/HeaderComponent";
import CuotasService from "../services/CuotasService";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

function ListadoCuotasComponent() {
  const [cuotasEntity, setCuotasEntity] = useState([]);
  const [rut, setRut] = useState("");
  const [rutParaBuscar, setRutParaBuscar] = useState("");
  const [estadoPagos, setEstadoPagos] = useState({});

  useEffect(() => {
    if (rutParaBuscar.trim() !== "") {
      getCuotasByRut();
    }
  }, [rutParaBuscar]);

  const getCuotasByRut = () => {
    CuotasService.getCuotas(rutParaBuscar).then((res) => {
      console.log("Response data Cuotas:", res.data);
      setCuotasEntity(res.data);

      // Supongamos que el servicio proporciona un estado de pago para cada cuota
      setEstadoPagos(
        res.data.reduce((acc, cuota) => {
          acc[cuota.numeroCuota] = cuota.pagada;
          return acc;
        }, {})
      );
    });
  };

  const changeRutHandler = (event) => {
    setRut(event.target.value);
  };

  const buscarCuotas = () => {
    // Actualizar la variable para buscar cuando se presiona el botón

    setRutParaBuscar(rut);
  };

  const pagarCuota = (numeroCuota, id_cuota) => {
    // Aquí deberías llamar al servicio para realizar el pago de la cuota
    // Puedes pasar el número de cuota como parámetro al servicio
    console.log(`Pagar cuota ${numeroCuota}`);
    CuotasService.actualizarEstadoPago(id_cuota);
    getCuotasByRut();
    //actualizarEstadoPago(id_cuota);
    // Actualizar el estado de pagos si es necesario
    setEstadoPagos({ ...estadoPagos, [numeroCuota]: "Pagada" });
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
        variant="primary" onClick={buscarCuotas}>
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
            {cuotasEntity.map((cuota) => (
              <tr key={cuota.rutCuota}>
                <td>{cuota.rut}</td>
                <td>{cuota.numeroCuota}</td>
                <td>{cuota.monto_variable}</td>
                <td>{cuota.estado}</td>
                <td>{cuota.fecha_cuota}</td>
                <Button
                  className="boton"
                  variant="success"
                  onClick={() => pagarCuota(cuota.numeroCuota, cuota.id)}
                  disabled={estadoPagos[cuota.numeroCuota] === "Pagada"}
                >
                  Pagar
                </Button>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ListadoCuotasComponent;
