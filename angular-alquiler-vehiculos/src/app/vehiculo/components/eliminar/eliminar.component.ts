import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { VehiculoI } from 'src/app/models/vehiculo.model';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';
import { environment } from 'src/environments/environment';
import { VehiculoService } from '../../services/vehiculo.service';

@Component({
  selector: 'app-eliminar',
  templateUrl: './eliminar.component.html',
  styleUrls: ['./eliminar.component.css']
})
export class EliminarComponent implements OnInit, OnDestroy {
  vehiculo: VehiculoI;
  pais: string;
  BASE_URL = environment.BASE_URL;

  routeSubscription: Subscription;
  vehiculoSubscription: Subscription;
  eliminarSubscription: Subscription;
  confirmacionSubscription: Subscription;
  constructor(
    private confirmacion: MatDialog,
    private route: ActivatedRoute,
    private vehiculoService: VehiculoService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {
    this.vehiculo = {"idVeh": 0, "denominacion": '', "imagen": 'predeterminada.png', "cantPersonas": 0, "tipoCambio": "", "aireAc": false, "abs": false, "precioDia": 0, "estado": true, "marca": {"idMarca": 0, "denominacion": "", "estado": true}};
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.vehiculoSubscription = this.vehiculoService.getVehiculo(params.idVeh)
      .subscribe(resp => {
        this.vehiculo = resp;
      })
    })
  }

  eliminarVehiculo(){
    this.confirmacionSubscription = this.confirmacion
      .open(DialogoConfirmacionComponent, {
        data: `¿Desea eliminar el vehiculo?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          const id = this.vehiculo.idVeh;
          this.eliminarSubscription = this.vehiculoService.eliminarVehiculo(id)
          .subscribe(resp => {
            this.openSnackBar('Vehiculo eliminado con exito', 'Cerrar');
            this.router.navigateByUrl('vehiculos/listado');
          });
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
    this.routeSubscription.unsubscribe();
    this.vehiculoSubscription.unsubscribe();
    if(this.eliminarSubscription){
      this.eliminarSubscription.unsubscribe();
      this.confirmacionSubscription.unsubscribe();
    }
  }
}