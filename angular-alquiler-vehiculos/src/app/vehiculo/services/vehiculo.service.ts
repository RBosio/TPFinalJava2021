import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { fstatSync } from 'fs';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { VehiculoI } from 'src/app/models/vehiculo.model';
import { LocalService } from 'src/app/shared/services/local.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VehiculoService {

  constructor(
    private http: HttpClient,
    private localService: LocalService
    ) { }

  getVehiculo(idVeh: number): Observable<VehiculoI>{
    return this.http.get<VehiculoI>(environment.BASE_URL+`/vehiculo/${idVeh}`)
  }

  getAll(): Observable<VehiculoI[]>{
    return this.http.get(environment.BASE_URL+'/vehiculo')
    .pipe(
      map((resp: VehiculoI[]) => resp.filter(v => v.estado && v.marca.estado))
    )
  }

  getVehiculosDisponibles(fechaHoraInicio: string, fechaHoraFin: string, marca: string, diferencia: number): Observable<VehiculoI[]>{
    return this.http.get(environment.BASE_URL+`/vehiculo/${fechaHoraInicio}/${fechaHoraFin}`)
    .pipe(
      map((resp: VehiculoI[]) => {
        const fechas = {"fechaHoraInicio": fechaHoraInicio, "fechaHoraFin": fechaHoraFin, diferencia};
        resp = resp.filter(v => v.estado && v.marca.denominacion == marca && (v.precioDia *= diferencia));
        this.localService.setJsonValue('fechas', fechas);
        this.localService.setJsonValue('alquiler', resp);
        return resp;
        })
    )
  }

  nuevoVehiculo(v: VehiculoI): Observable<VehiculoI>{
    return this.http.post<VehiculoI>(environment.BASE_URL+`/vehiculo`, v)
  }

  cargarImagen(idVeh: number, file: File){
    const fd = new FormData();
    fd.append('file', file);
    return this.http.put(environment.BASE_URL+`/vehiculo/upload/${idVeh}`, fd)
  }

  eliminarVehiculo(idVeh: number): Observable<VehiculoI>{
    return this.http.delete<VehiculoI>(environment.BASE_URL+`/vehiculo/${idVeh}`)
  }
}