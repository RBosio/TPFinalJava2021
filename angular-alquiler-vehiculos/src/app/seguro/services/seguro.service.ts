import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  getSeguros(){
    const diferencia = this.localService.getJsonValue('fechas').diferencia
    return this.http.get(environment.BASE_URL+'/cobertura')
    .pipe(
      map((resp: CoberturaI[]) => resp.filter(c => c.estado && (c.precioDia *= diferencia)))
    )
  }
}
