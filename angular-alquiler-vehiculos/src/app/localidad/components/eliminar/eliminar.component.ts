import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { LocalidadI } from 'src/app/models/localidad.model';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';
import { LocalidadService } from '../../services/localidad.service';

@Component({
  selector: 'app-eliminar',
  templateUrl: './eliminar.component.html',
  styleUrls: ['./eliminar.component.css']
})
export class EliminarComponent implements OnInit, OnDestroy {
  localidad: LocalidadI;
  provincia: string;
  pais: string;
  routeSubscription: Subscription;
  localidadSubscription: Subscription;
  eliminarSubscription: Subscription;
  confirmacionSubscription: Subscription;
  constructor(
    private confirmacion: MatDialog,
    private route: ActivatedRoute,
    private localidadService: LocalidadService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {
    this.localidad = {"codPostal": '', "nombre": '', "idProv": 0};
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.localidadSubscription = this.localidadService.getLocalidad(params.codPostal)
      .subscribe(resp => {
        this.localidad = resp;
        this.provincia = resp.provincia.denominacion;
        this.pais = resp.provincia.pais.denominacion;
      })
    })
  }

  eliminarLocalidad(){
    this.confirmacionSubscription = this.confirmacion
      .open(DialogoConfirmacionComponent, {
        data: `¿Desea eliminar la localidad?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          const codPostal = this.localidad.codPostal;
          this.eliminarSubscription = this.localidadService.eliminarLocalidad(codPostal)
          .subscribe(resp => {
            this.openSnackBar('Localidad eliminada con exito', 'Cerrar');
            this.router.navigateByUrl('localidades');
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
    this.localidadSubscription.unsubscribe();
    if(this.eliminarSubscription){
      this.eliminarSubscription.unsubscribe();
      this.confirmacionSubscription.unsubscribe();
    }
  }
}
