import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CoberturaI } from 'src/app/models/cobertura.model';
import { SeguroService } from '../../services/seguro.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit, OnDestroy {
  formulario: FormGroup;
  seguro: CoberturaI;
  routeSubscription: Subscription;
  seguroSubscription: Subscription;
  editarSubscription: Subscription;
  constructor(
    private route: ActivatedRoute,
    private seguroService: SeguroService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.seguro = {"idCob": 0, "descripcion": '', "precioDia": 0};
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.routeSubscription = this.route.params.subscribe(params => {
      this.seguroSubscription = this.seguroService.getSeguro(params.id)
      .subscribe(resp => {
        this.seguro = resp;
      })
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      descripcion: ['', Validators.required],
      precioDia: ['', Validators.compose([Validators.required, Validators.pattern('^[0-9]+(\.[0-9]+)?$')])]
    })
  }

  editarSeguro(){
    const idCob = this.seguro.idCob;
    const descripcion = this.formulario.value.descripcion;
    const precioDia = this.formulario.value.precioDia;
    this.seguro = {idCob, descripcion, precioDia};
    this.editarSubscription = this.seguroService.editarSeguro(this.seguro)
    .subscribe(resp => {
      this.openSnackBar('Seguro editado con exito', 'Cerrar');
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
    this.routeSubscription.unsubscribe();
    this.seguroSubscription.unsubscribe();
    if(this.editarSubscription){
      this.editarSubscription.unsubscribe();
    }
  }
}
