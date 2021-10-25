import { MarcaI } from "./marca.model";

export interface VehiculoI{
    idVeh?: number;
    denominacion: string;
    imagen?: string;
    cantPersonas: number;
    tipoCambio: string;
    aireAc: boolean;
    abs: boolean;
    precioDia: number;
    idMarca?: number;
    estado?: boolean;
    marca?: MarcaI;
}