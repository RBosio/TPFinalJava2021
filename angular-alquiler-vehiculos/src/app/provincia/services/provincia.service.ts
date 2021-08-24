import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProvinciaIResponse } from 'src/app/models/provincia.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProvinciaService {

  constructor(
    private http: HttpClient
    ) { }

  getProvinciasxPais(idPais: number): Observable<ProvinciaIResponse[]>{
    return this.http.get<ProvinciaIResponse[]>(environment.BASE_URL+'/provincia')
    .pipe(
      map((resp: ProvinciaIResponse[]) => {
        return resp.filter(p => p.estado && p.idPais == idPais)
      })
    )
  }
}
