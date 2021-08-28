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
}
