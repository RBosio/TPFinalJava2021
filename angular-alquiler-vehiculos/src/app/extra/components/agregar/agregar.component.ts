import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CoberturaI } from 'src/app/models/cobertura.model';
import { ExtraService } from 'src/app/extra/services/extra.service';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit {
  formulario: FormGroup;
  extra: CoberturaI;
  nuevoSubscription: Subscription;
  constructor(
    private extraService: ExtraService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.extra = {"descripcion": '', "precioDia": 0};
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

  nuevoExtra(){
    const descripcion = this.formulario.value.descripcion;
    const precioDia = this.formulario.value.precioDia;
    this.extra = {descripcion, precioDia};
    this.nuevoSubscription = this.extraService.nuevoExtra(this.extra)
    .subscribe(resp => {
      this.openSnackBar('Extra insertado con exito', 'Cerrar');
      this.router.navigateByUrl('extras');
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
