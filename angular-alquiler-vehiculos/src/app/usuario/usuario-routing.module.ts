import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgregarComponent } from './components/agregar/agregar.component';
import { ListadoComponent } from './components/listado/listado.component';

const routes: Routes = [
  {path: 'listado', component: ListadoComponent},
  {path: 'agregar', component: AgregarComponent},
  {path: '**', redirectTo: 'listado'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
