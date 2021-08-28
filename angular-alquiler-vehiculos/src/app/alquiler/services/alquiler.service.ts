import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AlquilerI } from 'src/app/models/alquiler.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlquilerService {

  constructor(
    private http: HttpClient
  ) { }

  confirmarAlquiler(data: AlquilerI){
    return this.http.post(environment.BASE_URL+'/alquiler', data)
  }
}
