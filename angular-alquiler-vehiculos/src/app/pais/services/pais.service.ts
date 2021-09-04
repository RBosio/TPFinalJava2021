import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaisI } from 'src/app/models/pais.model';

import { environment } from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class PaisService {

  constructor(
    private http: HttpClient
    ) { }

  getPaises(): Observable<PaisI[]>{
    return this.http.get<PaisI[]>(environment.BASE_URL+'/pais')
    .pipe(
      map((resp: PaisI[]) => resp.filter(p => p.estado))
    )
  }
  
  getPais(idPais: number): Observable<PaisI>{
    return this.http.get<PaisI>(environment.BASE_URL+`/pais/${idPais}`)
  }

  nuevoPais(pais: PaisI){
    return this.http.post(environment.BASE_URL+`/pais`, pais);
  }
  
  editarPais(pais: PaisI): Observable<PaisI>{
    return this.http.put<PaisI>(environment.BASE_URL+`/pais/${pais.idPais}`, pais)
  }
  
  eliminarPais(pais: PaisI): Observable<PaisI>{
    return this.http.delete<PaisI>(environment.BASE_URL+`/pais/${pais.idPais}`)
  }
}
