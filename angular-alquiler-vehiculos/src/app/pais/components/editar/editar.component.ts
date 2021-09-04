import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PaisI } from 'src/app/models/pais.model';
import { PaisService } from '../../services/pais.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit, OnDestroy {
  formulario: FormGroup;
  pais: PaisI;
  routeSubscription: Subscription;
  paisSubscription: Subscription;
  editarSubscription: Subscription;
  constructor(
    private route: ActivatedRoute,
    private paisService: PaisService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.pais = {"idPais": 0, "denominacion": ''};
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.routeSubscription = this.route.params.subscribe(params => {
      this.paisSubscription = this.paisService.getPais(params.id)
      .subscribe(resp => {
        this.pais = resp;
      })
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      denominacion: ['', Validators.required]
    })
  }

  editarPais(){
    const idPais = this.pais.idPais;
    const denominacion = this.formulario.value.denominacion;
    this.pais = {idPais, denominacion};
    this.editarSubscription = this.paisService.editarPais(this.pais)
    .subscribe(resp => {
      this.openSnackBar('Pais editado con exito', 'Cerrar');
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
    this.routeSubscription.unsubscribe();
    this.paisSubscription.unsubscribe();
    if(this.editarSubscription){
      this.editarSubscription.unsubscribe();
    }
  }
}
