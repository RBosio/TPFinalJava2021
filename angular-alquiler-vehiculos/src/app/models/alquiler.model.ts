import { ExtraI } from "./extra.model";

export interface AlquilerI{
    dni: number;
    fechaHoraInicio: string;
    fechaHoraFin: string;
    idVeh: number;
    idCob: number;
    estado?: string;
    extras: ExtraI[];
    estados?: boolean[];
}