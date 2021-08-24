import { PaisI } from "./pais.model";

export interface ProvinciaI{
    idProv: number;
    denominacion: string;
    pais: PaisI;
}

export interface ProvinciaIResponse{
    idProvincia: number;
    denominacion: string;
    idPais: number;
    estado: boolean;
}