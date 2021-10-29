import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CoberturaI } from 'src/app/models/cobertura.model';
import { SeguroService } from 'src/app/seguro/services/seguro.service';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit {
  formulario: FormGroup;
  seguro: CoberturaI;
  nuevoSubscription: Subscription;
  constructor(
    private seguroService: SeguroService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.seguro = {"descripcion": '', "precioDia": 0};
  }

  ngOnInit(): void {
    this.crearFormulario();
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      descripcion: ['', Validators.required],
      precioDia: ['', Validators.compose([Validators.required, Validators.pattern('^[0-9]+(\.[0-9]+)?$')])]
    })
  }

  nuevoSeguro(){
    const descripcion = this.formulario.value.descripcion;
    const precioDia = this.formulario.value.precioDia;
    this.seguro = {descripcion, precioDia};
    this.nuevoSubscription = this.seguroService.nuevoSeguro(this.seguro)
    .subscribe(resp => {
      this.openSnackBar('Seguro insertado con exito', 'Cerrar');
      this.router.navigateByUrl('seguros');
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
