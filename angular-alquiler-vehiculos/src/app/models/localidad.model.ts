import { ProvinciaI } from "./provincia.model";

export interface LocalidadI{
    codPostal: string;
    nombre: string;
    provincia: ProvinciaI;
}