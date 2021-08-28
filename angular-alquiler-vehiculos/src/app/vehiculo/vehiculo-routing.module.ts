import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListadoVehiculosComponent } from './components/listado-vehiculos/listado-vehiculos.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: ListadoVehiculosComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VehiculoRoutingModule { }
