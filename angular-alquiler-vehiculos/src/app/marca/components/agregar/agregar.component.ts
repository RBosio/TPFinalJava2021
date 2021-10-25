import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MarcaI } from 'src/app/models/marca.model';
import { MarcaService } from '../../services/marca.service';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit {
  formulario: FormGroup;
  marca: MarcaI;
  nuevaSubscription: Subscription;
  constructor(
    private marcaService: MarcaService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.marca = {"idMarca": 0, "denominacion": ""};
  }

  ngOnInit(): void {
    this.crearFormulario();
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      denominacion: ['', Validators.required]
    })
  }

  nuevaMarca(){
    const idMarca = this.marca.idMarca;
    const denominacion = this.formulario.value.denominacion;
    this.marca = {idMarca, denominacion};
    this.nuevaSubscription = this.marcaService.nuevaMarca(this.marca)
    .subscribe(resp => {
      this.openSnackBar('Marca insertada con exito', 'Cerrar');
      this.router.navigateByUrl('marcas');
    },
    err => {
      this.openSnackBar('Marca existente', 'Cerrar');
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
    if(this.nuevaSubscription){
      this.nuevaSubscription.unsubscribe();
    }
  }
}
