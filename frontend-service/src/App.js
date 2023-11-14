import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainComponent from "./components/MainComponent";
import AgregarEstudianteComponent from "./components/AgregarEstudianteComponent";
import ListadoEstudianteComponent from "./components/ListadoEstudianteComponent";
import GenerarCuotasComponent from "./components/GenerarCuotasComponent";
import ListadoCuotasComponent from "./components/ListadoCuotasComponent";
import SubidaArchivoComponent from "./components/subidaArchivoComponent";
import InfoArchivoComponent from "./components/InfoArchivoComponent";
import ResumenEstadoPagoComponent from "./components/ResumenEstadoPagoComponent";

function App() {
    return (
        <div>
            <Router>
                <Routes>
                    <Route path="/" element={<MainComponent />} />
                    <Route path="/agregar_estudiante" element={<AgregarEstudianteComponent />} />
                    <Route path="/lista_estudiantes" element={<ListadoEstudianteComponent />} />
                    <Route path="/generar_cuotas" element={<GenerarCuotasComponent />} />
                    <Route path="/lista_cuotas" element={<ListadoCuotasComponent />} />
                    <Route path="/subir_archivo" element={< SubidaArchivoComponent/>} />
                    <Route path="/file-info" element={< InfoArchivoComponent/>} />
                    <Route path="/resumen" element={< ResumenEstadoPagoComponent/>} />
                </Routes>
            </Router>
        </div>
    );
}

export default App;

