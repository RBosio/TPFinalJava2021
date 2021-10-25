import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MarcaI } from 'src/app/models/marca.model';
import { MarcaService } from '../../services/marca.service';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit, OnDestroy {
  formulario: FormGroup;
  marca: MarcaI;
  routeSubscription: Subscription;
  marcaSubscription: Subscription;
  editarSubscription: Subscription;
  constructor(
    private route: ActivatedRoute,
    private marcaService: MarcaService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.marca = {"idMarca": 0, "denominacion": ''};
  }

  ngOnInit(): void {
    this.crearFormulario();
    this.routeSubscription = this.route.params.subscribe(params => {
      this.marcaSubscription = this.marcaService.getMarca(params.id)
      .subscribe(resp => {
        this.marca = resp;
      })
    })
  }

  crearFormulario(){
    this.formulario = this.fb.group({
      denominacion: ['', Validators.required]
    })
  }

  editarMarca(){
    const idMarca = this.marca.idMarca;
    const denominacion = this.formulario.value.denominacion;
    this.marca = {idMarca, denominacion};
    this.editarSubscription = this.marcaService.editarMarca(this.marca)
    .subscribe(resp => {
      this.openSnackBar('Marca editada con exito', 'Cerrar');
      this.router.navigateByUrl('marcas');
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
    this.marcaSubscription.unsubscribe();
    if(this.editarSubscription){
      this.editarSubscription.unsubscribe();
    }
  }
}
