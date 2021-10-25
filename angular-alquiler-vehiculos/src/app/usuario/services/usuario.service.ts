import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserLoginResponseI } from 'src/app/models/user.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(
    private http: HttpClient
  ) { }

  getUsuarios(): Observable<UserLoginResponseI[]>{
    return this.http.get<UserLoginResponseI[]>(environment.BASE_URL+'/persona')
    .pipe(
      map((resp: UserLoginResponseI[]) => {
        return resp.filter(p => p.estado)
      })
    )
  }
}
