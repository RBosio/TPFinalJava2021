import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { SegurosExtrasComponent } from './components/seguros-extras/seguros-extras.component';
import { SeleccionVehiculoComponent } from './components/seleccion-vehiculo/seleccion-vehiculo.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: HomeComponent},
  {path: 'seleccion-vehiculo', component: SeleccionVehiculoComponent},
  {path: 'seguros-extras', component: SegurosExtrasComponent},
  {path: '**', redirectTo: ''},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AlquilerRoutingModule { }
