import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import * as moment from 'moment';
import { Subscription } from 'rxjs';
import { MarcaService } from 'src/app/marca/services/marca.service';
import { MarcaI } from 'src/app/models/marca.model';
import { VehiculoService } from 'src/app/vehiculo/services/vehiculo.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  formulario: FormGroup;
  fechaActual: string;
  fechaRetiro: string;
  fechaDevolucion: string;
  fechaRDefinitiva: string;
  fechaDDefinitiva: string;
  marcas: MarcaI[];
  marcasSubscription: Subscription;
  vehiculosSubscription: Subscription;
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
    this.marcasSubscription = this.marcaService.getMarcas()
    .subscribe(resp => {
      this.marcas = resp;
    });
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
    const momentRetiro = moment(this.formulario.value.fechaRetiro);
    const momentDevolucion = moment(this.formulario.value.fechaDevolucion);
    const diff = momentDevolucion.diff(momentRetiro, 'days');
    this.fechaRDefinitiva = moment(this.formulario.value.fechaRetiro).format('YYYY-MM-DD')+'T'+this.formulario.value.horaRetiro+':00';
    this.fechaDDefinitiva = moment(this.formulario.value.fechaDevolucion).format('YYYY-MM-DD')+'T'+this.formulario.value.horaDevolucion+':00';
    this.vehiculosSubscription = this.vehiculoService.getVehiculosDisponibles(this.fechaRDefinitiva, this.fechaDDefinitiva, this.formulario.value.marca, diff)
    .subscribe(resp => {
      this.router.navigateByUrl('alquiler/seleccion-vehiculo');
    })
  }

  ngOnDestroy(){
    this.marcasSubscription.unsubscribe();
    if(this.vehiculosSubscription){
      this.vehiculosSubscription.unsubscribe();
    }
  }
}
