import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { VehiculoI } from '../models/vehiculo.model';
import { LocalService } from '../shared/services/local.service';

@Injectable({
  providedIn: 'root'
})
export class VendedorGuard implements CanActivate {
  constructor(private localStorageService: LocalService){}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const vendedor = this.localStorageService.getJsonValue('user');
      const rolVendedor = vendedor.roles.filter(r => r.nombre == 'Vendedor');   
      if(rolVendedor.length > 0){
        console.log(rolVendedor);
        return true;
      };
      return false;
  }

}
