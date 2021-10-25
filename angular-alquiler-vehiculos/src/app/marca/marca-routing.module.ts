import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AgregarComponent } from './components/agregar/agregar.component';
import { EditarComponent } from './components/editar/editar.component';
import { EliminarComponent } from './components/eliminar/eliminar.component';
import { ListadoComponent } from './components/listado/listado.component';

const routes: Routes = [
  {path: 'listado', component: ListadoComponent},
  {path: 'agregar', component: AgregarComponent},
  {path: 'editar/:id', component: EditarComponent},
  {path: 'eliminar/:id', component: EliminarComponent},
  {path: '**', redirectTo: 'listado'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MarcaRoutingModule { }
