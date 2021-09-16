import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LocalidadI } from 'src/app/models/localidad.model';
import { LocalidadService } from '../../services/localidad.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit, OnDestroy {
  formulario: FormGroup;
  localidad: LocalidadI;
  provincia: string;
  pais: string;
  routeSubscription: Subscription;
  localidadSubscription: Subscription;
  editarSubscription: Subscription;
  constructor(
    private route: ActivatedRoute,
    private localidadService: LocalidadService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.localidad = {codPostal: '', nombre: '', idProv: 0};
    this.provincia = '';
    this.pais = '';
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.routeSubscription = this.route.params.subscribe(params => {
      this.localidadSubscription = this.localidadService.getLocalidad(params.codPostal)
      .subscribe(resp => {
        this.localidad = resp;
        this.provincia = resp.provincia.denominacion;
        this.pais = resp.provincia.pais.denominacion;
      })
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      nombre: ['', Validators.required]
    })
  }

  editarLocalidad(){
    const codPostal = this.localidad.codPostal;
    const nombre = this.formulario.value.nombre;
    const idProv = this.localidad.idProv;
    this.localidad = {codPostal, nombre, idProv};
    this.editarSubscription = this.localidadService.editarLocalidad(this.localidad)
    .subscribe(resp => {
      this.openSnackBar('Localidad editada con exito', 'Cerrar');
      this.router.navigateByUrl('localidades');
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
    this.localidadSubscription.unsubscribe();
    if(this.editarSubscription){
      this.editarSubscription.unsubscribe();
    }
  }
}
