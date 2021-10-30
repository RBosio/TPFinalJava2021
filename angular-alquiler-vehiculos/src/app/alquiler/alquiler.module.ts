import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AlquilerRoutingModule } from './alquiler-routing.module';
import { SharedModule } from '../shared/shared.module';

import { FacturaComponent } from './components/factura/factura.component';
import { HomeComponent } from './components/home/home.component';
import { SegurosExtrasComponent } from './components/seguros-extras/seguros-extras.component';
import { SeleccionVehiculoComponent } from './components/seleccion-vehiculo/seleccion-vehiculo.component';
import { ListadoComponent } from './components/listado/listado.component';


@NgModule({
  declarations: [
    HomeComponent,
    SeleccionVehiculoComponent,
    SegurosExtrasComponent,
    FacturaComponent,
    ListadoComponent
  ],
  imports: [
    CommonModule,
    AlquilerRoutingModule,
    SharedModule
  ]
})
export class AlquilerModule { }
