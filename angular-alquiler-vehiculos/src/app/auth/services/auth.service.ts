import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { UserLoginI, UserLoginResponseI, UserSignupI } from 'src/app/models/user.model';
import { environment } from "../../../environments/environment";

import { catchError, map } from "rxjs/operators";
import { throwError } from 'rxjs';
import { LocalService } from 'src/app/shared/services/local.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user: UserLoginResponseI
  userEvent = new EventEmitter<UserLoginResponseI>();
  userExpired = new EventEmitter<void>();

  constructor(
    private http: HttpClient,
    private localStorageService: LocalService
    ) { }

  login(user: UserLoginI){
    return this.http.post<UserLoginResponseI>(environment.BASE_URL+'/persona/login', user)
    .pipe(
      map((res: UserLoginResponseI) => {
        this.localStorageService.setJsonValue('token', res.token);
        this.localStorageService.setJsonValue('user', res);
        this.userEvent.emit(res);
        return res
      }),
      catchError(err => {
        return throwError(err.error.message)
      })
    )
  }
  
  signup(user: UserSignupI){
    return this.http.post<UserLoginResponseI>(environment.BASE_URL+'/persona/signup', user)
    .pipe(
      map((res: UserLoginResponseI) => {
        return res
      }),
      catchError(err => {
        return throwError(err.error.message)
      })
    )
  }

  logout(){
    this.localStorageService.clearToken();
  }

  tokenExpired(){
    this.userExpired.emit();
  }
}
