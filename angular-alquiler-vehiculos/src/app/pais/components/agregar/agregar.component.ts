import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PaisI } from 'src/app/models/pais.model';
import { PaisService } from '../../services/pais.service';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit {
  formulario: FormGroup;
  pais: PaisI;
  nuevoSubscription: Subscription;
  constructor(
    private paisService: PaisService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.pais = {"idPais": 0, denominacion: ""};
  }

  ngOnInit(): void {
    this.crearFormulario();
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      denominacion: ['', Validators.required]
    })
  }

  nuevoPais(){
    const idPais = this.pais.idPais;
    const denominacion = this.formulario.value.denominacion;
    this.pais = {idPais, denominacion};
    this.nuevoSubscription = this.paisService.nuevoPais(this.pais)
    .subscribe(resp => {
      this.openSnackBar('Pais insertado con exito', 'Cerrar');
      this.router.navigateByUrl('paises');
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
    if(this.nuevoSubscription){
      this.nuevoSubscription.unsubscribe();
    }
  }
}
