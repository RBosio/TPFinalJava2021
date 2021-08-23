import { Injectable } from '@angular/core';

import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalService } from '../shared/services/local.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor{
  token: string;
  constructor(private localStorageService: LocalService) {
    this.token = '';
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.token = this.localStorageService.getJsonValue('token') || '';

    const headers = new HttpHeaders({
      'token': this.token
    });
    
    const cloneReq = req.clone({
      headers
    });

    return next.handle(cloneReq)
  }
}
