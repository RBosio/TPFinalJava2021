import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import * as moment from 'moment';
import { MarcaService } from 'src/app/marca/services/marca.service';
import { MarcaI } from 'src/app/models/marca.model';
import { VehiculoI } from 'src/app/models/vehiculo.model';
import { VehiculoService } from 'src/app/vehiculo/services/vehiculo.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  formulario: FormGroup;
  fechaActual: string;
  fechaRetiro: string;
  fechaDevolucion: string;
  fechaRDefinitiva: string;
  fechaDDefinitiva: string;
  marcas: MarcaI[];
  vehiculos: VehiculoI[];
  constructor(
    private fb: FormBuilder,
    private marcaService: MarcaService,
    private vehiculoService: VehiculoService,
    private router: Router
    ) {
    moment.locale('es');
    this.fechaActual = moment(Date.now()).add(1, 'd').format("YYYY-MM-DD")
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.marcaService.getMarcas()
    .subscribe(resp => {
      this.marcas = resp;
    });
    this.vehiculoService.getVehiculos()
    .subscribe(resp => {
      this.vehiculos = resp;
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      "fechaRetiro": ['', Validators.required],
      "fechaDevolucion": ['', Validators.required],
      "horaRetiro": ['', Validators.required],
      "horaDevolucion": ['', Validators.required],
      "marca": ['', Validators.required]
    })
  }

  setFechaRetiro(){
    this.fechaRetiro = moment(this.formulario.value.fechaRetiro, 'YYYY-MM-DD').add(2, 'd').format('YYYY-MM-DD');
  }
  
  setFechaDevolucion(){
    this.fechaDevolucion = moment(this.formulario.value.fechaDevolucion).format('YYYY-MM-DD');
  }

  buscar(){
    this.fechaRDefinitiva = moment(this.formulario.value.fechaRetiro).format('YYYY-MM-DD')+'T'+this.formulario.value.horaRetiro+':00';
    this.fechaDDefinitiva = moment(this.formulario.value.fechaDevolucion).format('YYYY-MM-DD')+'T'+this.formulario.value.horaDevolucion+':00';
    this.vehiculoService.getVehiculosDisponibles(this.fechaRDefinitiva, this.fechaDDefinitiva, this.formulario.value.marca)
    .subscribe(resp => {
      this.router.navigateByUrl('alquiler/seleccion-vehiculo');
    })
  }
}
