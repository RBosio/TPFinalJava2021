import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProvinciaI, ProvinciaIPost } from 'src/app/models/provincia.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProvinciaService {

  constructor(
    private http: HttpClient
    ) { }

  getProvincias(): Observable<ProvinciaI[]>{
    return this.http.get<ProvinciaI[]>(environment.BASE_URL+'/provincia')
    .pipe(
      map((resp: ProvinciaI[]) => {
        return resp.filter(p => p.estado)
      })
    )
  }

  getProvinciasxPais(idPais: number): Observable<ProvinciaI[]>{
    return this.http.get<ProvinciaI[]>(environment.BASE_URL+'/provincia')
    .pipe(
      map((resp: ProvinciaI[]) => {
        return resp.filter(p => p.estado && p.idPais == idPais)
      })
    )
  }
  
  getProvincia(idProvincia: number): Observable<ProvinciaI>{
    return this.http.get<ProvinciaI>(environment.BASE_URL+`/provincia/${idProvincia}`)
  }

  nuevaProvincia(provincia: ProvinciaIPost): Observable<ProvinciaI>{
    return this.http.post<ProvinciaI>(environment.BASE_URL+`/provincia`, provincia);
  }

  editarProvincia(provincia: ProvinciaI): Observable<ProvinciaI>{
    return this.http.put<ProvinciaI>(environment.BASE_URL+`/provincia/${provincia.idProvincia}`, provincia)
  }

  eliminarProvincia(provincia: ProvinciaI): Observable<ProvinciaI>{
    return this.http.delete<ProvinciaI>(environment.BASE_URL+`/provincia/${provincia.idProvincia}`)
  }
}
