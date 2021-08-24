import { PaisI } from "./pais.model";

export interface ProvinciaI{
    idProv: number;
    denominacion: string;
    pais: PaisI;
}