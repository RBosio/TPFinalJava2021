import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  getMarcas(){
    return this.http.get(environment.BASE_URL+'/marca')
    .pipe(
      map((resp: MarcaI[]) => resp.filter(m => m.estado))
    )
  }
}
