import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AlquilerI } from 'src/app/models/alquiler.model';
import { CoberturaI } from 'src/app/models/cobertura.model';
import { ExtraI } from 'src/app/models/extra.model';
import { VehiculoI } from 'src/app/models/vehiculo.model';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';
import { LocalService } from 'src/app/shared/services/local.service';
import { environment } from 'src/environments/environment';
import { AlquilerService } from '../../services/alquiler.service';

@Component({
  selector: 'app-factura',
  templateUrl: './factura.component.html',
  styleUrls: ['./factura.component.css']
})
export class FacturaComponent implements OnInit, OnDestroy {
  diferencia: number;
  BASE_URL = environment.BASE_URL;
  vehiculo: VehiculoI;
  seguro: CoberturaI;
  extras: ExtraI[];
  subtotal: number;
  iva: number;
  total: number;
  confirmacionSubscription: Subscription;
  alquilerSubscription: Subscription;
  constructor(
    private localService: LocalService,
    private confirmacion: MatDialog,
    private alquilerService: AlquilerService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.diferencia = this.localService.getJsonValue('fechas').diferencia;
    this.vehiculo = this.localService.getJsonValue('vehiculoSeleccionado')[0];
    this.seguro = this.localService.getJsonValue('seguro-extras').seguro;
    this.extras = this.localService.getJsonValue('seguro-extras').extras;
    this.subtotal = this.vehiculo.precioDia + this.seguro.precioDia;
    for (let i = 0; i < this.extras.length; i++) {
      this.subtotal += this.extras[i].precioDia;
    }
    this.iva = this.subtotal * .21;
    this.total = this.subtotal + this.iva;
  }

  confirmar(){
    this.confirmacionSubscription = this.confirmacion
      .open(DialogoConfirmacionComponent, {
        data: `¿Desea confirmar el registro de alquiler?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          const dni = this.localService.getJsonValue('user').dni;
          const fechaHoraInicio = this.localService.getJsonValue('fechas').fechaHoraInicio;
          const fechaHoraFin = this.localService.getJsonValue('fechas').fechaHoraFin;
          const idVeh = this.vehiculo.idVeh;
          const idCob = this.seguro.idCob;
          const extras = this.extras;
          const data: AlquilerI = {dni, fechaHoraInicio, fechaHoraFin, idVeh, idCob, extras};
          this.alquilerSubscription = this.alquilerService.confirmarAlquiler(data)
          .subscribe(resp => {
            this.openSnackBar('Alquiler de vehiculo registrado con exito', 'Cerrar');
            const token = this.localService.getJsonValue('token');
            const user = this.localService.getJsonValue('user');
            this.localService.clearToken();
            this.localService.setJsonValue('token', token);
            this.localService.setJsonValue('user', user);
            this.router.navigateByUrl('alquiler');
          })
        }
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
    if(this.alquilerSubscription){
      this.confirmacionSubscription.unsubscribe();
      this.alquilerSubscription.unsubscribe();
    }
  }
}
