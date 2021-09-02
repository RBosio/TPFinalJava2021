import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '', redirectTo: 'alquiler', pathMatch: 'full',
  },
  {
    path: 'alquiler',
    loadChildren: () => import('./alquiler/alquiler.module').then(m => m.AlquilerModule)
  },
  {
    path: 'vehiculo',
    loadChildren: () => import('./vehiculo/vehiculo.module').then(m => m.VehiculoModule)
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: '**',
    redirectTo: 'alquiler'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
