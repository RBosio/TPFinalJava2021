import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AlquilerRoutingModule } from './alquiler-routing.module';
import { SharedModule } from '../shared/shared.module';

import { HomeComponent } from './components/home/home.component';
import { SeleccionVehiculoComponent } from './components/seleccion-vehiculo/seleccion-vehiculo.component';
import { PaginatorPipe } from './pipes/paginator.pipe';
import { SegurosExtrasComponent } from './components/seguros-extras/seguros-extras.component';


@NgModule({
  declarations: [
    HomeComponent,
    SeleccionVehiculoComponent,
    PaginatorPipe,
    SegurosExtrasComponent
  ],
  imports: [
    CommonModule,
    AlquilerRoutingModule,
    SharedModule
  ]
})
export class AlquilerModule { }
