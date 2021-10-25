import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { MarcaI } from 'src/app/models/marca.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {

  constructor(
    private http: HttpClient
  ) { }

  getMarcas(): Observable<MarcaI[]>{
    return this.http.get(environment.BASE_URL+'/marca')
    .pipe(
      map((resp: MarcaI[]) => resp.filter(m => m.estado))
    )
  }

  getMarca(idMarca: number): Observable<MarcaI>{
    return this.http.get<MarcaI>(environment.BASE_URL+`/marca/${idMarca}`)
  }

  nuevaMarca(marca: MarcaI){
    return this.http.post(environment.BASE_URL+`/marca`, marca);
  }
  
  editarMarca(marca: MarcaI): Observable<MarcaI>{
    return this.http.put<MarcaI>(environment.BASE_URL+`/marca/${marca.idMarca}`, marca)
  }
  
  eliminarmarca(idMarca: number): Observable<MarcaI>{
    return this.http.delete<MarcaI>(environment.BASE_URL+`/marca/${idMarca}`)
  }
}
