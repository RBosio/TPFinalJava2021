import { LocalidadI } from "./localidad.model";
import { Rol } from "./rol.model";

export interface UserLoginI{
    email: string;
    password: string;
}

export interface UserLoginResponse{
    apellido: string;
    codPostal: string;
    dni: string;
    email: string;
    estado: boolean;
    loc: LocalidadI;
    nombre: string;
    roles: Rol[];
    telefono: string
    token: string;
}