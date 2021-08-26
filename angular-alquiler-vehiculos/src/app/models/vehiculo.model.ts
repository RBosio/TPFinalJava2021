import { MarcaI } from "./marca.model";

export interface VehiculoI{
    idVeh: number;
    denominacion: string;
    imagen: string;
    cantPersonas: number;
    tipoCambio: boolean;
    aireAc: boolean;
    abs: boolean;
    precioDia: number;
    estado: boolean;
    marca: MarcaI;
}