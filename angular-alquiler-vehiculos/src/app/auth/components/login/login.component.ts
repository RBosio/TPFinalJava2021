import { validateHorizontalPosition } from '@angular/cdk/overlay';
import { error } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserLoginI } from 'src/app/models/user.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: UserLoginI
  formularioLogin: FormGroup
  error: string
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
    this.authService.login(this.user)
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
}
