import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdministradorGuard } from './guards/administrador.guard';
import { LoginGuard } from './guards/login.guard';

const routes: Routes = [
  {
    path: '', redirectTo: 'auth', pathMatch: 'full',
  },
  {
    path: 'alquileres',
    loadChildren: () => import('./alquiler/alquiler.module').then(m => m.AlquilerModule),
    canLoad: [LoginGuard]
  },
  {
    path: 'vehiculos',
    loadChildren: () => import('./vehiculo/vehiculo.module').then(m => m.VehiculoModule),
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'paises',
    loadChildren: () => import('./pais/pais.module').then(m => m.PaisModule),
    canLoad: [LoginGuard, AdministradorGuard]
  },
  {
    path: 'provincias',
    loadChildren: () => import('./provincia/provincia.module').then(m => m.ProvinciaModule),
    canLoad: [LoginGuard, AdministradorGuard]
  },
  {
    path: 'localidades',
    loadChildren: () => import('./localidad/localidad.module').then(m => m.LocalidadModule),
    canLoad: [LoginGuard, AdministradorGuard]
  },
  {
    path: 'usuarios',
    loadChildren: () => import('./usuario/usuario.module').then(m => m.UsuarioModule),
    canLoad: [LoginGuard, AdministradorGuard]
  },
  {
    path: 'marcas',
    loadChildren: () => import('./marca/marca.module').then(m => m.MarcaModule),
    canLoad: [LoginGuard, AdministradorGuard]
  },
  {
    path: 'seguros',
    loadChildren: () => import('./seguro/seguro.module').then(m => m.SeguroModule),
    canLoad: [LoginGuard, AdministradorGuard]
  },
  {
    path: 'extras',
    loadChildren: () => import('./extra/extra.module').then(m => m.ExtraModule),
    canLoad: [LoginGuard, AdministradorGuard]
  },
  {
    path: '**',
    redirectTo: 'auth'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
