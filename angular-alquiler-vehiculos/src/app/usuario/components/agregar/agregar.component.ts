import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/services/auth.service';
import { LocalidadService } from 'src/app/localidad/services/localidad.service';
import { LocalidadIResponse } from 'src/app/models/localidad.model';
import { PaisI } from 'src/app/models/pais.model';
import { ProvinciaI } from 'src/app/models/provincia.model';
import { UserSignupI } from 'src/app/models/user.model';
import { PaisService } from 'src/app/pais/services/pais.service';
import { ProvinciaService } from 'src/app/provincia/services/provincia.service';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit, OnDestroy {
  user: UserSignupI;
  formularioRegistro: FormGroup;
  error: string;
  signupSubscription: Subscription;
  paisSubscription: Subscription;
  provinciaSubscription: Subscription;
  localidadSubscription: Subscription;
  checked: boolean;
  roles: any;

  paises: PaisI[];
  provincias: ProvinciaI[];
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
    this.checked = false;
    this.crearFormulario();
    this.paisSubscription = this.paisService.getPaises()
    .subscribe(resp => {
      this.paises = resp;
    })
    this.formularioRegistro.get('provincia').disable();
    this.formularioRegistro.get('localidad').disable();
  }

  crearFormulario(){
    this.formularioRegistro = this.fb.group({
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
    if(this.checked){
      this.roles = [{"idRol": 1}, {"idRol": 2}];
    }else{
      this.roles = [];
    }
    this.user = {
      "nombre": this.formularioRegistro.value.nombre,
      "apellido": this.formularioRegistro.value.apellido,
      "dni": this.formularioRegistro.value.dni,
      "email": this.formularioRegistro.value.email,
      "password": this.formularioRegistro.value.password,
      "telefono": this.formularioRegistro.value.telefono,
      "codPostal": this.formularioRegistro.value.localidad,
      "roles": this.roles
    }
    this.signupSubscription = this.authService.signup(this.user)
      .subscribe(res => {
        this.openSnackBar('Usuario registrado correctamente', 'Cerrar');
        this.router.navigateByUrl('auth/Registro');
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
    this.provinciaSubscription = this.provinciaService.getProvinciasxPais(opcion)
    .subscribe(resp => {
      this.provincias = resp;
      this.formularioRegistro.get('provincia').enable();
    })
  }
  
  getLocalidadesxProvincia(opcion: number){
    this.localidadSubscription = this.localidadService.getLocalidadesxProvincia(opcion)
    .subscribe(resp => {
      this.localidades = resp;
      this.formularioRegistro.get('localidad').enable();
    })
  }

  ngOnDestroy(){
    this.paisSubscription.unsubscribe();
    if(this.signupSubscription){
      this.provinciaSubscription.unsubscribe();
      this.localidadSubscription.unsubscribe();
      this.signupSubscription.unsubscribe();
    }
  }
}
