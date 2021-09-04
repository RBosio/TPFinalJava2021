import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ProvinciaI } from 'src/app/models/provincia.model';
import { ProvinciaService } from '../../services/provincia.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit, OnDestroy {
  formulario: FormGroup;
  provincia: ProvinciaI;
  pais: string;
  routeSubscription: Subscription;
  provinciaSubscription: Subscription;
  editarSubscription: Subscription;
  constructor(
    private route: ActivatedRoute,
    private provinciaService: ProvinciaService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.provincia = {idProvincia: 0, denominacion: '', idPais: 0};
    this.pais = '';
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.routeSubscription = this.route.params.subscribe(params => {
      this.provinciaSubscription = this.provinciaService.getProvincia(params.id)
      .subscribe(resp => {
        this.provincia = resp;
        this.pais = resp.pais.denominacion;
      })
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      denominacion: ['', Validators.required]
    })
  }

  editarProvincia(){
    const idProvincia = this.provincia.idProvincia;
    const denominacion = this.formulario.value.denominacion;
    const idPais = this.provincia.idPais;
    this.provincia = {idProvincia, denominacion, idPais};
    this.editarSubscription = this.provinciaService.editarProvincia(this.provincia)
    .subscribe(resp => {
      this.openSnackBar('Provincia editada con exito', 'Cerrar');
      this.router.navigateByUrl('provincias');
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
    this.provinciaSubscription.unsubscribe();
    if(this.editarSubscription){
      this.editarSubscription.unsubscribe();
    }
  }
}
