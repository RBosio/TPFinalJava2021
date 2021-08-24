import { ProvinciaI } from "./provincia.model";

export interface LocalidadI{
    codPostal: string;
    nombre: string;
    provincia: ProvinciaI;
}

export interface LocalidadIResponse{
    codPostal: number;
    nombre: string;
    idProv: number;
    estado: boolean;
}