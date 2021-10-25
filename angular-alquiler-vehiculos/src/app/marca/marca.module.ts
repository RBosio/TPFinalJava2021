import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MarcaRoutingModule } from './marca-routing.module';
import { ListadoComponent } from './components/listado/listado.component';
import { AgregarComponent } from './components/agregar/agregar.component';
import { EditarComponent } from './components/editar/editar.component';
import { EliminarComponent } from './components/eliminar/eliminar.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    ListadoComponent,
    AgregarComponent,
    EditarComponent,
    EliminarComponent
  ],
  imports: [
    CommonModule,
    MarcaRoutingModule,
    SharedModule
  ]
})
export class MarcaModule { }
