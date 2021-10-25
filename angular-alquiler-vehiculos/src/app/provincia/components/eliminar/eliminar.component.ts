import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ProvinciaI } from 'src/app/models/provincia.model';
import { ProvinciaService } from 'src/app/provincia/services/provincia.service';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';

@Component({
  selector: 'app-eliminar',
  templateUrl: './eliminar.component.html',
  styleUrls: ['./eliminar.component.css']
})
export class EliminarComponent implements OnInit, OnDestroy {
  provincia: ProvinciaI;
  pais: string;
  routeSubscription: Subscription;
  provinciaSubscription: Subscription;
  eliminarSubscription: Subscription;
  confirmacionSubscription: Subscription;
  constructor(
    private confirmacion: MatDialog,
    private route: ActivatedRoute,
    private provinciaService: ProvinciaService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {
    this.provincia = {"idProvincia": 0, "denominacion": '', "idPais": 0};
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.provinciaSubscription = this.provinciaService.getProvincia(params.id)
      .subscribe(resp => {
        this.provincia = resp;
        this.pais = resp.pais.denominacion;
      })
    })
  }

  eliminarProvincia(){
    this.confirmacionSubscription = this.confirmacion
      .open(DialogoConfirmacionComponent, {
        data: `¿Desea eliminar la provincia?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          const idProvincia = this.provincia.idProvincia;
          this.eliminarSubscription = this.provinciaService.eliminarProvincia(idProvincia)
          .subscribe(resp => {
            this.openSnackBar('Provincia eliminada con exito', 'Cerrar');
            this.router.navigateByUrl('provincias');
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
    this.provinciaSubscription.unsubscribe();
    if(this.eliminarSubscription){
      this.eliminarSubscription.unsubscribe();
      this.confirmacionSubscription.unsubscribe();
    }
  }
}
