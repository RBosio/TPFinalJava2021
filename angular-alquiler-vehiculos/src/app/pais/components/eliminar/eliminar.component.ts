import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PaisI } from 'src/app/models/pais.model';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';
import { PaisService } from '../../services/pais.service';

@Component({
  selector: 'app-eliminar',
  templateUrl: './eliminar.component.html',
  styleUrls: ['./eliminar.component.css']
})
export class EliminarComponent implements OnInit, OnDestroy {
  pais: PaisI;
  routeSubscription: Subscription;
  paisSubscription: Subscription;
  eliminarSubscription: Subscription;
  confirmacionSubscription: Subscription;
  constructor(
    private confirmacion: MatDialog,
    private route: ActivatedRoute,
    private paisService: PaisService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {
    this.pais = {"idPais": 0, "denominacion": ''};
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.paisSubscription = this.paisService.getPais(params.id)
      .subscribe(resp => {
        this.pais = resp;
      })
    })
  }

  eliminarPais(){
    this.confirmacionSubscription = this.confirmacion
      .open(DialogoConfirmacionComponent, {
        data: `¿Desea eliminar el pais?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          const idPais = this.pais.idPais;
          const denominacion = this.pais.denominacion;
          this.pais = {idPais, denominacion};
          this.eliminarSubscription = this.paisService.eliminarPais(this.pais)
          .subscribe(resp => {
            this.openSnackBar('Pais eliminado con exito', 'Cerrar');
            this.router.navigateByUrl('paises');
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
    this.paisSubscription.unsubscribe();
    if(this.confirmacionSubscription){
      this.eliminarSubscription.unsubscribe();
      this.confirmacionSubscription.unsubscribe();
    }
  }
}
