import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { VehiculoI } from 'src/app/models/vehiculo.model';
import { LocalService } from 'src/app/shared/services/local.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-seleccion-vehiculo',
  templateUrl: './seleccion-vehiculo.component.html',
  styleUrls: ['./seleccion-vehiculo.component.css']
})
export class SeleccionVehiculoComponent implements OnInit {
  vehiculos : VehiculoI[];
  page_size = 3;
  page_number = 1;
  BASE_URL = environment.BASE_URL;
  diferencia: number;
  constructor(
    private localService: LocalService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.diferencia = this.localService.getJsonValue('fechas').diferencia;
    this.vehiculos = this.localService.getJsonValue('alquiler');
  }

  handlePage(e: PageEvent){
    this.page_size = e.pageSize;
    this.page_number = e.pageIndex + 1;
  }

  seleccion(idVeh: number){
    this.localService.setJsonValue('vehiculoSeleccionado', this.vehiculos.filter(v => v.idVeh == idVeh));
    this.router.navigateByUrl('alquileres/seguros-extras');
  }
}
