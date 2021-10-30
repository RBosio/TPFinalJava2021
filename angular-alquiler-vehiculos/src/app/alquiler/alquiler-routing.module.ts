import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VendedorGuard } from '../guards/vendedor.guard';

import { FacturaComponent } from './components/factura/factura.component';
import { HomeComponent } from './components/home/home.component';
import { ListadoComponent } from './components/listado/listado.component';
import { SegurosExtrasComponent } from './components/seguros-extras/seguros-extras.component';
import { SeleccionVehiculoComponent } from './components/seleccion-vehiculo/seleccion-vehiculo.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: HomeComponent},
  {path: 'seleccion-vehiculo', component: SeleccionVehiculoComponent},
  {path: 'seguros-extras', component: SegurosExtrasComponent},
  {path: 'factura', component: FacturaComponent},
  {path: 'listado', component: ListadoComponent, canActivate: [VendedorGuard]},
  {path: 'confirmar-alquiler', component: FacturaComponent, canActivate: [VendedorGuard]},
  {path: 'confirmar-devolucion', component: FacturaComponent, canActivate: [VendedorGuard]},
  {path: 'cancelar', component: FacturaComponent, canActivate: [VendedorGuard]},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AlquilerRoutingModule { }
