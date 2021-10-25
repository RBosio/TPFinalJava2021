import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MarcaService } from 'src/app/marca/services/marca.service';
import { LocalidadIResponse } from 'src/app/models/localidad.model';
import { MarcaI } from 'src/app/models/marca.model';
import { PaisI } from 'src/app/models/pais.model';
import { ProvinciaI } from 'src/app/models/provincia.model';
import { VehiculoI } from 'src/app/models/vehiculo.model';
import { VehiculoService } from '../../services/vehiculo.service';

interface tipoDeCambio{
  sigla: string;
  denominacion: string;
}

@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent implements OnInit, OnDestroy {
  vehiculo: VehiculoI;
  formularioVehiculo: FormGroup;
  error: string;
  marcas: MarcaI[];
  imagen: File;
  photoSelected: string | ArrayBuffer;
  vehiculoSubscription: Subscription;
  marcaSubscription: Subscription;
  tiposCambio: tipoDeCambio[];

  paises: PaisI[];
  provincias: ProvinciaI[];
  localidades: LocalidadIResponse[];
  constructor(
    private vehiculoService: VehiculoService,
    private router: Router,
    private fb: FormBuilder,
    private marcaService: MarcaService,
    private _snackBar: MatSnackBar
    ) {}

  ngOnInit(): void {
    this.tiposCambio = [{"sigla": 'A', "denominacion": 'Automatico'}, {"sigla": 'M', "denominacion": 'Manual'}]
    this.crearFormulario();
    this.marcaSubscription = this.marcaService.getMarcas()
    .subscribe(resp => {
      this.marcas = resp;
    })
  }

  crearFormulario(){
    this.formularioVehiculo = this.fb.group({
      denominacion: ['', Validators.required],
      marca: ['', Validators.required],
      cantPersonas: ['', Validators.compose([Validators.required, Validators.pattern('[0-9]*')])],
      aireAc: [false, Validators.required],
      abs: [false, Validators.required],
      tipoCambio: ['', Validators.required],
      precioDia: ['', Validators.required],
    })
  }
  
  nuevoVehiculo(e: Event){
    e.preventDefault();
    this.vehiculo = {
      "denominacion": this.formularioVehiculo.value.denominacion,
      "idMarca": this.formularioVehiculo.value.marca,
      "cantPersonas": this.formularioVehiculo.value.cantPersonas,
      "aireAc": this.formularioVehiculo.value.aireAc,
      "abs": this.formularioVehiculo.value.abs,
      "tipoCambio": this.formularioVehiculo.value.tipoCambio,
      "precioDia": parseInt(this.formularioVehiculo.value.precioDia),
    }
    
    this.vehiculoSubscription = this.vehiculoService.nuevoVehiculo(this.vehiculo)
      .subscribe(res => {
        if(this.imagen != null){
          this.vehiculoService.cargarImagen(res.idVeh, this.imagen)
          .subscribe(res => {
            this.openSnackBar('Vehiculo registrado correctamente', 'Cerrar');
            this.router.navigateByUrl('vehiculos/listado');
          });
        }
      });
  }

  fotoSeleccionada(e){
    if(e.target.files && e.target.files[0]){
      this.imagen = <File>e.target.files[0];

      const reader = new FileReader()
      reader.onload = e => this.photoSelected = reader.result;
      reader.readAsDataURL(this.imagen);
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'top'
    });
  }

  ngOnDestroy(){
    this.marcaSubscription.unsubscribe();
    if(this.vehiculoSubscription){
      this.vehiculoSubscription.unsubscribe();
    }
  }
}
