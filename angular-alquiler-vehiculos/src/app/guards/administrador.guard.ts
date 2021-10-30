import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, RouterStateSnapshot, UrlSegment, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalService } from '../shared/services/local.service';

@Injectable({
  providedIn: 'root'
})
export class AdministradorGuard implements CanActivate, CanLoad {
  constructor(private localStorageService: LocalService){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const administrador = this.localStorageService.getJsonValue('user');
      const rolAdministrador = administrador.roles.filter(r => r.nombre == 'Administrador');   
      if(rolAdministrador.length > 0){
        console.log(rolAdministrador);
        return true;
      };
      return false;
  }

  canLoad(
    route: Route,
    segments: UrlSegment[]): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const administrador = this.localStorageService.getJsonValue('user');
      const rolAdministrador = administrador.roles.filter(r => r.nombre == 'Administrador');   
      if(rolAdministrador.length > 0){
        console.log(rolAdministrador);
        return true;
      };
      return false;
  }
}
