import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PaisI } from 'src/app/models/pais.model';
import { ProvinciaIPost } from 'src/app/models/provincia.model';
import { PaisService } from 'src/app/pais/services/pais.service';
import { ProvinciaService } from 'src/app/provincia/services/provincia.service';

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit {
  formulario: FormGroup;
  provincia: ProvinciaIPost;
  paises: PaisI[];
  nuevoSubscription: Subscription;
  constructor(
    private provinciaService: ProvinciaService,
    private paisService: PaisService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.provincia = {"denominacion": "", "idPais": 0};
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.paisService.getPaises()
    .subscribe(resp => {
      this.paises = resp;
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      denominacion: ['', Validators.required],
      pais: ['', Validators.required]
    })
  }

  nuevaProvincia(){
    const denominacion = this.formulario.value.denominacion;
    const idPais = this.formulario.value.pais;
    this.provincia = {denominacion, idPais};
    this.nuevoSubscription = this.provinciaService.nuevaProvincia(this.provincia)
    .subscribe(resp => {
      this.openSnackBar('Provincia insertada con exito', 'Cerrar');
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
    if(this.nuevoSubscription){
      this.nuevoSubscription.unsubscribe();
    }
  }
}
