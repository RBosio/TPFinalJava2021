import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ExtraI } from 'src/app/models/extra.model';
import { ExtraService } from '../../services/extra.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit, OnDestroy {
  formulario: FormGroup;
  extra: ExtraI;
  routeSubscription: Subscription;
  extraSubscription: Subscription;
  editarSubscription: Subscription;
  constructor(
    private route: ActivatedRoute,
    private extraService: ExtraService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.extra = {"idExtra": 0, "descripcion": '', "precioDia": 0};
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.routeSubscription = this.route.params.subscribe(params => {
      this.extraSubscription = this.extraService.getExtra(params.id)
      .subscribe(resp => {
        this.extra = resp;
      })
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      descripcion: ['', Validators.required],
      precioDia: ['', Validators.compose([Validators.required, Validators.pattern('^[0-9]+(\.[0-9]+)?$')])]
    })
  }

  editarextra(){
    const idExtra = this.extra.idExtra;
    const descripcion = this.formulario.value.descripcion;
    const precioDia = this.formulario.value.precioDia;
    this.extra = {idExtra, descripcion, precioDia};
    this.editarSubscription = this.extraService.editarExtra(this.extra)
    .subscribe(resp => {
      this.openSnackBar('Extra editado con exito', 'Cerrar');
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
    this.routeSubscription.unsubscribe();
    this.extraSubscription.unsubscribe();
    if(this.editarSubscription){
      this.editarSubscription.unsubscribe();
    }
  }
}
