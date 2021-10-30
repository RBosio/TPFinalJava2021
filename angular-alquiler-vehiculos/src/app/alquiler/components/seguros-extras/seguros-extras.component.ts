import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ExtraService } from 'src/app/extra/services/extra.service';
import { CoberturaI } from 'src/app/models/cobertura.model';
import { ExtraI } from 'src/app/models/extra.model';
import { SeguroService } from 'src/app/seguro/services/seguro.service';
import { LocalService } from 'src/app/shared/services/local.service';

@Component({
  selector: 'app-seguros-extras',
  templateUrl: './seguros-extras.component.html',
  styleUrls: ['./seguros-extras.component.css']
})
export class SegurosExtrasComponent implements OnInit, OnDestroy {
  seguros: CoberturaI[];
  extras: ExtraI[];
  extrasElegidos: ExtraI[];
  diferencia: number;
  seguro: CoberturaI;
  seguroSubscription: Subscription;
  extrasSubscription: Subscription;
  constructor(
    private seguroService: SeguroService,
    private localService: LocalService,
    private extraService: ExtraService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.diferencia = this.localService.getJsonValue('fechas').diferencia;
    this.seguroSubscription = this.seguroService.getSegurosFiltrados()
    .subscribe(resp => {
      this.seguros = resp;
    })
    this.extrasSubscription = this.extraService.getExtras()
    .subscribe(resp => {
      this.extras = resp;
    })
    this.extrasElegidos = [];
  }

  check(extra: ExtraI){
    const indice = this.extrasElegidos.indexOf(extra);
    if(indice == -1) return this.extrasElegidos.push(extra);
    return this.extrasElegidos.splice(indice, 1);
  }

  option(seguro: CoberturaI){
    this.seguro = seguro;
  }

  seleccionar(){
    if(this.seguro){
      const seguro_extras = {seguro: this.seguro, extras: this.extrasElegidos};
      this.localService.setJsonValue('seguro-extras', seguro_extras);
      this.router.navigateByUrl('alquileres/factura');
    }else{
      this.openSnackBar('Seleccione un seguro', 'Cerrar');
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom'
    });
  }

  ngOnDestroy(){
    this.seguroSubscription.unsubscribe();
    this.extrasSubscription.unsubscribe();
  }
}
