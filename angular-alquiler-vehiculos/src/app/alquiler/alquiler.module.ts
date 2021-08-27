import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AlquilerRoutingModule } from './alquiler-routing.module';
import { SharedModule } from '../shared/shared.module';

import { FacturaComponent } from './components/factura/factura.component';
import { HomeComponent } from './components/home/home.component';
import { PaginatorPipe } from './pipes/paginator.pipe';
import { SegurosExtrasComponent } from './components/seguros-extras/seguros-extras.component';
import { SeleccionVehiculoComponent } from './components/seleccion-vehiculo/seleccion-vehiculo.component';


@NgModule({
  declarations: [
    HomeComponent,
    SeleccionVehiculoComponent,
    PaginatorPipe,
    SegurosExtrasComponent,
    FacturaComponent
  ],
  imports: [
    CommonModule,
    AlquilerRoutingModule,
    SharedModule
  ]
})
export class AlquilerModule { }
