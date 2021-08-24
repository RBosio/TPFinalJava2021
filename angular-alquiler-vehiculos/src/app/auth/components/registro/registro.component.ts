import { validateHorizontalPosition } from '@angular/cdk/overlay';
import { error } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LocalidadI } from 'src/app/models/localidad.model';
import { PaisI } from 'src/app/models/pais.model';
import { ProvinciaI } from 'src/app/models/provincia.model';
import { UserLoginI } from 'src/app/models/user.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {
  user: UserLoginI;
  formularioLogin: FormGroup;
  error: string;

  paises: PaisI[];
  provincias: ProvinciaI[];
  localidades: LocalidadI[];
  constructor(
    private authService: AuthService,
    private router: Router,
    private _snackBar: MatSnackBar,
    private fb: FormBuilder
    ) { }

  ngOnInit(): void {
    this.crearFormulario();
  }

  crearFormulario(){
    this.formularioLogin = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.compose([Validators.required, Validators.minLength(6)])],
      dni: ['', Validators.required],
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])],
      telefono: ['', Validators.required],
      localidad: ['', Validators.required],
      provincia: ['', Validators.required],
      pais: ['', Validators.required]
    })
  }
}
