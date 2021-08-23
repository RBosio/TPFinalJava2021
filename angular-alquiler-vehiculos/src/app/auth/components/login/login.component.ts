import { error } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserLoginI, UserLoginResponse } from 'src/app/models/user.model';
import { LocalService } from 'src/app/shared/services/local.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: UserLoginI
  error: string
  constructor(private authService: AuthService, private router: Router, private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  login(e: Event, email: HTMLInputElement, pass: HTMLInputElement){
    e.preventDefault();
    this.user = {
      "email": email.value,
      "password": pass.value
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
}
