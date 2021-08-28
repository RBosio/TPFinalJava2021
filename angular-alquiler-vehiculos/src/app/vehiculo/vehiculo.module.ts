import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VehiculoRoutingModule } from './vehiculo-routing.module';
import { ListadoVehiculosComponent } from './components/listado-vehiculos/listado-vehiculos.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    ListadoVehiculosComponent
  ],
  imports: [
    CommonModule,
    VehiculoRoutingModule,
    SharedModule
  ]
})
export class VehiculoModule { }
