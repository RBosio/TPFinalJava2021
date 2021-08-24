import { LocalidadI } from "./localidad.model";
import { Rol } from "./rol.model";

export interface UserLoginI{
    email: string;
    password: string;
}

export interface UserLoginResponseI{
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

export interface UserSignupI{
    nombre: string;
    apellido: string;
    dni: string;
    email: string;
    password: string;
    telefono: string
    codPostal: number;
    roles: Rol[];
}