import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VehiculoRoutingModule } from './vehiculo-routing.module';
import { ListadoVehiculosComponent } from './components/listado-vehiculos/listado-vehiculos.component';
import { SharedModule } from '../shared/shared.module';
import { ListadoComponent } from './components/listado/listado.component';
import { EliminarComponent } from './components/eliminar/eliminar.component';
import { AgregarComponent } from './components/agregar/agregar.component';


@NgModule({
  declarations: [
    ListadoVehiculosComponent,
    ListadoComponent,
    EliminarComponent,
    AgregarComponent
  ],
  imports: [
    CommonModule,
    VehiculoRoutingModule,
    SharedModule
  ]
})
export class VehiculoModule { }
