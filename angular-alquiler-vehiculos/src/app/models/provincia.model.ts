import { PaisI } from "./pais.model";

export interface ProvinciaI{
    idProvincia: number;
    denominacion: string;
    idPais: number;
    estado?: boolean;
    pais?: PaisI;
}

export interface ProvinciaIPost{
    denominacion: string;
    idPais: number;
}