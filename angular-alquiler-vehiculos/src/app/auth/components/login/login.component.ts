import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserLoginI } from 'src/app/models/user.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  user: UserLoginI
  formularioLogin: FormGroup
  error: string
  loginSubscription: Subscription
  constructor(
    private authService: AuthService,
    private router: Router,
    private _snackBar: MatSnackBar,
    private fb: FormBuilder
    ) { }

  ngOnInit(): void {
    this.crearFormulario();
  }

  login(e: Event){
    e.preventDefault();
    this.user = {
      "email": this.formularioLogin.value.email,
      "password": this.formularioLogin.value.password
    }
    this.loginSubscription = this.authService.login(this.user)
    .subscribe(res => {
      this.router.navigateByUrl('')
    },
    err => {
      this.openSnackBar(err, 'Cerrar');
    }
    );
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'top'
    });
  }

  crearFormulario(){
    this.formularioLogin = this.fb.group({
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])]
    })
  }

  ngOnDestroy(){
    if(this.loginSubscription){
      this.loginSubscription.unsubscribe();
    }
  }
}
