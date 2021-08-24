import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LocalidadService } from 'src/app/localidad/services/localidad.service';
import { LocalidadIResponse } from 'src/app/models/localidad.model';
import { PaisI } from 'src/app/models/pais.model';
import { ProvinciaIResponse } from 'src/app/models/provincia.model';
import { UserSignupI } from 'src/app/models/user.model';
import { PaisService } from 'src/app/paises/services/pais.service';
import { ProvinciaService } from 'src/app/provincia/services/provincia.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {
  user: UserSignupI;
  formularioLogin: FormGroup;
  error: string;

  paises: PaisI[];
  provincias: ProvinciaIResponse[];
  localidades: LocalidadIResponse[];
  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private paisService: PaisService,
    private provinciaService: ProvinciaService,
    private localidadService: LocalidadService,
    private _snackBar: MatSnackBar
    ) {}

  ngOnInit(): void {
    this.crearFormulario();
    this.paisService.getPaises()
    .subscribe(resp => {
      this.paises = resp;
    })
    this.formularioLogin.get('provincia').disable();
    this.formularioLogin.get('localidad').disable();
  }

  crearFormulario(){
    this.formularioLogin = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      dni: ['', Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(10), Validators.pattern('[0-9]*')])],
      email: ['', Validators.compose([Validators.required, Validators.email])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])],
      telefono: ['', Validators.compose([Validators.required, Validators.pattern('[0-9]*')])],
      pais: ['', Validators.required],
      provincia: ['', Validators.required],
      localidad: ['', Validators.required]
    })
  }
  
  signup(e: Event){
    e.preventDefault();
    this.user = {
      "nombre": this.formularioLogin.value.nombre,
      "apellido": this.formularioLogin.value.apellido,
      "dni": this.formularioLogin.value.dni,
      "email": this.formularioLogin.value.email,
      "password": this.formularioLogin.value.password,
      "telefono": this.formularioLogin.value.telefono,
      "codPostal": this.formularioLogin.value.localidad,
      "roles": []
    }
    this.authService.signup(this.user)
      .subscribe(res => {
        this.router.navigateByUrl('auth/login')
      },
      err => {
        this.openSnackBar('DNI o email existente', 'Cerrar');
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
  
  getProvinciasxPais(opcion: number){
    this.provinciaService.getProvinciasxPais(opcion)
    .subscribe(resp => {
      this.provincias = resp;
      this.formularioLogin.get('provincia').enable();
    })
  }
  
  getLocalidadesxProvincia(opcion: number){
    this.localidadService.getLocalidadesxProvincia(opcion)
    .subscribe(resp => {
      this.localidades = resp;
      this.formularioLogin.get('localidad').enable();
    })
  }
}
