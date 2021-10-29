import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CoberturaI } from 'src/app/models/cobertura.model';
import { LocalService } from 'src/app/shared/services/local.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SeguroService {

  constructor(
    private http: HttpClient,
    private localService: LocalService
  ) { }

  getSeguros(): Observable<CoberturaI[]>{
    return this.http.get<CoberturaI[]>(environment.BASE_URL+'/cobertura')
    .pipe(
      map((resp: CoberturaI[]) => resp.filter(p => p.estado))
    )
  }
  
  getSegurosFiltrados(){
    const diferencia = this.localService.getJsonValue('fechas').diferencia
    return this.http.get(environment.BASE_URL+'/cobertura')
    .pipe(
      map((resp: CoberturaI[]) => resp.filter(c => c.estado && (c.precioDia *= diferencia)))
    )
  }

  getSeguro(idCob: number): Observable<CoberturaI>{
    return this.http.get<CoberturaI>(environment.BASE_URL+`/cobertura/${idCob}`)
  }

  nuevoSeguro(cobertura: CoberturaI){
    return this.http.post(environment.BASE_URL+`/cobertura`, cobertura);
  }
  
  editarSeguro(cobertura: CoberturaI): Observable<CoberturaI>{
    return this.http.put<CoberturaI>(environment.BASE_URL+`/cobertura/${cobertura.idCob}`, cobertura)
  }
  
  eliminarSeguro(idCob: number): Observable<CoberturaI>{
    return this.http.delete<CoberturaI>(environment.BASE_URL+`/cobertura/${idCob}`)
  }
}
