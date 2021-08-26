import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  getVehiculos(){
    return this.http.get(environment.BASE_URL+'/vehiculo')
    .pipe(
      map((resp: VehiculoI[]) => resp.filter(v => v.estado))
    )
  }

  getVehiculosDisponibles(fechaHoraInicio: string, fechaHoraFin: string, marca: string){
    return this.http.get(environment.BASE_URL+`/vehiculo/${fechaHoraInicio}/${fechaHoraFin}`)
    .pipe(
      map((resp: VehiculoI[]) => {
        resp = resp.filter(v => v.estado && v.marca.denominacion == marca);
        this.localService.setJsonValue('alquiler', resp);
        return resp;
        })
    )
  }
}
