import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AlquilerI } from 'src/app/models/alquiler.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlquilerService {

  constructor(
    private http: HttpClient
  ) { }

  getAlquileres(): Observable<AlquilerI[]>{
    return this.http.get<AlquilerI[]>(environment.BASE_URL+'/alquiler')
  }
  
  confirmarAlquiler(data: AlquilerI){
    return this.http.post(environment.BASE_URL+'/alquiler', data)
  }

  alquilerEnCurso(dni: string, fechaHoraInicio: string){
    const alquiler = {dni, fechaHoraInicio};
    return this.http.put(environment.BASE_URL+'/alquiler/confirmar', alquiler);
  }
  
  alquilerCompletado(dni: string, fechaHoraInicio: string){
    const alquiler = {dni, fechaHoraInicio};
    return this.http.put(environment.BASE_URL+'/alquiler/devolver', alquiler);
  }
  
  alquilerCancelado(dni: string, fechaHoraInicio: string){
    const alquiler = {dni, fechaHoraInicio};
    return this.http.put(environment.BASE_URL+'/alquiler/cancelar', alquiler);
  }

}
