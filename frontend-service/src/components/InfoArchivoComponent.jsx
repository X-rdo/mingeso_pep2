import React, { useState, useEffect } from "react";
import PruebaService from '../services/PruebaService'
import HeaderComponent from './Headers/HeaderComponent'

function ListaPrueba() {

    const [pruebaEntity, setPruebaEntity] = useState([]);
    //const [input, setInput] = useState(initialState);

    useEffect(() => {
        PruebaService.getFileType1Info().then((res) => {
            console.log("Response data Pruebas:", res.data);
            setPruebaEntity(res.data);
            //setInput({ ...input, estudianteEntity: res.data });
        });
    }, []);

    return(
        <div className="general">
            <HeaderComponent/>
            <div align="center" className="container-2">
                <h1><b> Listado de estudiante</b></h1>
                <table border="1" className="content-table">
                    <thead>
                        <tr>
                            <th>RUT</th>
                            <th>Fecha</th>
                            <th>Puntaje</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            pruebaEntity.map((prueba) => (
                                <tr key= {prueba.rut}>
                                    <td> {prueba.rut} </td>
                                    <td> {prueba.fecha_examen} </td>
                                    <td> {prueba.puntaje} </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ListaPrueba