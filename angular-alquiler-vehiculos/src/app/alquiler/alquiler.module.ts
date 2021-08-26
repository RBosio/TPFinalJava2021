import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AlquilerRoutingModule } from './alquiler-routing.module';
import { SharedModule } from '../shared/shared.module';

import { HomeComponent } from './components/home/home.component';
import { SeleccionVehiculoComponent } from './components/seleccion-vehiculo/seleccion-vehiculo.component';


@NgModule({
  declarations: [
    HomeComponent,
    SeleccionVehiculoComponent
  ],
  imports: [
    CommonModule,
    AlquilerRoutingModule,
    SharedModule
  ]
})
export class AlquilerModule { }
