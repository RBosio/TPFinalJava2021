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
    path: 'vehiculos',
    loadChildren: () => import('./vehiculo/vehiculo.module').then(m => m.VehiculoModule)
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'paises',
    loadChildren: () => import('./pais/pais.module').then(m => m.PaisModule)
  },
  {
    path: 'provincias',
    loadChildren: () => import('./provincia/provincia.module').then(m => m.ProvinciaModule)
  },
  {
    path: 'localidades',
    loadChildren: () => import('./localidad/localidad.module').then(m => m.LocalidadModule)
  },
  {
    path: 'usuarios',
    loadChildren: () => import('./usuario/usuario.module').then(m => m.UsuarioModule)
  },
  {
    path: 'marcas',
    loadChildren: () => import('./marca/marca.module').then(m => m.MarcaModule)
  },
  {
    path: 'seguros',
    loadChildren: () => import('./seguro/seguro.module').then(m => m.SeguroModule)
  },
  {
    path: 'extras',
    loadChildren: () => import('./extra/extra.module').then(m => m.ExtraModule)
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
