import { EventEmitter, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, RouterStateSnapshot, UrlSegment, UrlTree } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/services/auth.service';
import { LocalService } from '../shared/services/local.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanLoad {

  constructor(
    private localStorageService: LocalService,
    private authService: AuthService){}

  canLoad(
    route: Route,
    segments: UrlSegment[]): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const helper = new JwtHelperService();
      const token = this.localStorageService.getJsonValue('token');
      
      const isExpired = helper.isTokenExpired(token);
      
      if(!isExpired){
        return true;
      };
      
      this.localStorageService.clearToken();
      this.authService.tokenExpired();
      return false;
  }
}
