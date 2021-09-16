import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalidadI, LocalidadIResponse } from 'src/app/models/localidad.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LocalidadService {

  constructor(
    private http: HttpClient
  ) { }

  getLocalidades(): Observable<LocalidadI[]>{
    return this.http.get<LocalidadI[]>(environment.BASE_URL+'/localidad')
    .pipe(
      map((resp: LocalidadI[]) => {
        return resp.filter(l => l.estado)
      })
    )
  }
  
  getLocalidad(codPostal: number): Observable<LocalidadI>{
    return this.http.get<LocalidadI>(environment.BASE_URL+`/localidad/${codPostal}`)
  }

  getLocalidadesxProvincia(idProv: number): Observable<LocalidadIResponse[]>{
    return this.http.get<LocalidadIResponse[]>(environment.BASE_URL+'/localidad')
    .pipe(
      map((resp: LocalidadIResponse[]) => {
        return resp.filter(l => l.estado && l.idProv == idProv)
      })
    )
  }

  nuevaLocalidad(localidad: LocalidadI): Observable<LocalidadI>{
    return this.http.post<LocalidadI>(environment.BASE_URL+'/localidad', localidad);
  }

  editarLocalidad(loc: LocalidadI): Observable<LocalidadI>{
    return this.http.put<LocalidadI>(environment.BASE_URL+`/localidad/${loc.codPostal}`, loc);
  }

  eliminarLocalidad(codPostal: string): Observable<LocalidadI>{
    return this.http.delete<LocalidadI>(environment.BASE_URL+`/localidad/${codPostal}`)
  }
}
