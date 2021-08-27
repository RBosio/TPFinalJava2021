import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  getExtras(){
    const diferencia = this.localService.getJsonValue('fechas').diferencia
    return this.http.get(environment.BASE_URL+'/extra')
    .pipe(
      map((resp: ExtraI[]) => resp.filter(e => e.estado && (e.precioDia *= diferencia)))
    )
  }

}
