import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ExtraI } from 'src/app/models/extra.model';
import { LocalService } from 'src/app/shared/services/local.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ExtraService {

  constructor(
    private http: HttpClient,
    private localService: LocalService
  ) { }

  getExtras(): Observable<ExtraI[]>{
    return this.http.get<ExtraI[]>(environment.BASE_URL+'/extra')
    .pipe(
      map((resp: ExtraI[]) => resp.filter(p => p.estado))
    )
  }
  
  getExtrasFiltrados(){
    const diferencia = this.localService.getJsonValue('fechas').diferencia
    return this.http.get(environment.BASE_URL+'/extra')
    .pipe(
      map((resp: ExtraI[]) => resp.filter(e => e.estado && (e.precioDia *= diferencia)))
    )
  }

  getExtra(idExtra: number): Observable<ExtraI>{
    return this.http.get<ExtraI>(environment.BASE_URL+`/extra/${idExtra}`)
  }

  nuevoExtra(extra: ExtraI){
    return this.http.post(environment.BASE_URL+`/extra`, extra);
  }
  
  editarExtra(extra: ExtraI): Observable<ExtraI>{
    return this.http.put<ExtraI>(environment.BASE_URL+`/extra/${extra.idExtra}`, extra)
  }
  
  eliminarExtra(idExtra: number): Observable<ExtraI>{
    return this.http.delete<ExtraI>(environment.BASE_URL+`/extra/${idExtra}`)
  }

}
