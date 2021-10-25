import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { VehiculoI } from 'src/app/models/vehiculo.model';
import { environment } from 'src/environments/environment';
import { VehiculoService } from '../../services/vehiculo.service';

@Component({
  selector: 'app-listado',
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.css']
})
export class ListadoComponent implements OnInit {
  vehiculos : VehiculoI[];
  page_size = 3;
  page_number = 1;
  pageSizeOptions = [5, 10, 20];
  BASE_URL = environment.BASE_URL;
  diferencia: number;
  constructor(
    private vehiculoService: VehiculoService
  ) {
    this.vehiculos = [];
  }

  ngOnInit(): void {
    this.vehiculoService.getAll()
    .subscribe(resp => {
      this.vehiculos = resp;
    })
  }

  handlePage(e: PageEvent){
    this.page_size = e.pageSize;
    this.page_number = e.pageIndex + 1;
  }
}
