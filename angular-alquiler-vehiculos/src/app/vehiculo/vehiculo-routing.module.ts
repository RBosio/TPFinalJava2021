import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgregarComponent } from './components/agregar/agregar.component';
import { EliminarComponent } from './components/eliminar/eliminar.component';
import { ListadoVehiculosComponent } from './components/listado-vehiculos/listado-vehiculos.component';
import { ListadoComponent } from './components/listado/listado.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: ListadoVehiculosComponent},
  {path: 'listado', component: ListadoComponent},
  {path: 'agregar', component: AgregarComponent},
  {path: 'eliminar/:idVeh', component: EliminarComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VehiculoRoutingModule { }
