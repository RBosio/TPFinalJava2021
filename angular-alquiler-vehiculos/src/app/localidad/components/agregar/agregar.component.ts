import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LocalidadI } from 'src/app/models/localidad.model';
import { PaisI } from 'src/app/models/pais.model';
import { ProvinciaI, ProvinciaIPost } from 'src/app/models/provincia.model';
import { PaisService } from 'src/app/pais/services/pais.service';
import { ProvinciaService } from 'src/app/provincia/services/provincia.service';
import { LocalidadService } from '../../services/localidad.service';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit {
  formulario: FormGroup;
  localidad: LocalidadI;
  paisSubscription: Subscription;
  provinciaSubscription: Subscription;
  nuevoSubscription: Subscription;
  
  paises: PaisI[];
  provincias: ProvinciaI[];
  constructor(
    private paisService: PaisService,
    private provinciaService: ProvinciaService,
    private localidadService: LocalidadService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.localidad = {"codPostal": "", "nombre": "", "idProv": 0};
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.paisSubscription = this.paisService.getPaises()
    .subscribe(resp => {
      this.paises = resp;
    })
    this.formulario.get('provincia').disable();
  }

  getProvinciasxPais(opcion: number){
    this.provinciaSubscription = this.provinciaService.getProvinciasxPais(opcion)
    .subscribe(resp => {
      this.provincias = resp;
      this.formulario.get('provincia').enable();
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      codPostal: ['', Validators.compose([Validators.required, Validators.pattern('[0-9]*')])],
      nombre: ['', Validators.required],
      pais: ['', Validators.required],
      provincia: ['', Validators.required]
    })
  }

  nuevaLocalidad(){
    const codPostal = this.formulario.value.codPostal;
    const nombre = this.formulario.value.nombre;
    const idProv = this.formulario.value.provincia;
    this.localidad = {codPostal, nombre, idProv};
    this.nuevoSubscription = this.localidadService.nuevaLocalidad(this.localidad)
    .subscribe(resp => {
      this.openSnackBar('Localidad insertada con exito', 'Cerrar');
      this.router.navigateByUrl('localidades');
    },
    err => {
      this.openSnackBar('Localidad existente', 'Cerrar');
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'top'
    });
  }

  ngOnDestroy(){
    this.paisSubscription.unsubscribe();
    if(this.nuevoSubscription){
      this.provinciaSubscription.unsubscribe();
      this.nuevoSubscription.unsubscribe();
    }
  }
}
