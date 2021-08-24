import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LocalidadIResponse } from 'src/app/models/localidad.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LocalidadService {

  constructor(
    private http: HttpClient
  ) { }

  getLocalidadesxProvincia(idProv: number): Observable<LocalidadIResponse[]>{
    return this.http.get<LocalidadIResponse[]>(environment.BASE_URL+'/localidad')
    .pipe(
      map((resp: LocalidadIResponse[]) => {
        return resp.filter(l => l.estado && l.idProv == idProv)
      })
    )
  }
}
