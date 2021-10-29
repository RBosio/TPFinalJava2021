import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ExtraI } from 'src/app/models/extra.model';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';
import { ExtraService } from '../../services/extra.service';

@Component({
  selector: 'app-eliminar',
  templateUrl: './eliminar.component.html',
  styleUrls: ['./eliminar.component.css']
})
export class EliminarComponent implements OnInit, OnDestroy {
  extra: ExtraI;
  routeSubscription: Subscription;
  extraSubscription: Subscription;
  eliminarSubscription: Subscription;
  confirmacionSubscription: Subscription;
  constructor(
    private confirmacion: MatDialog,
    private route: ActivatedRoute,
    private extraService: ExtraService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {
    this.extra = {"idExtra": 0, "descripcion": '', "precioDia": 0};
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.extraSubscription = this.extraService.getExtra(params.id)
      .subscribe(resp => {
        this.extra = resp;
      })
    })
  }

  eliminarExtra(){
    this.confirmacionSubscription = this.confirmacion
      .open(DialogoConfirmacionComponent, {
        data: `¿Desea eliminar el extra?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          const idCob = this.extra.idExtra;
          this.eliminarSubscription = this.extraService.eliminarExtra(idCob)
          .subscribe(resp => {
            this.openSnackBar('Extra eliminado con exito', 'Cerrar');
            this.router.navigateByUrl('extras');
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
    this.extraSubscription.unsubscribe();
    if(this.eliminarSubscription){
      this.eliminarSubscription.unsubscribe();
      this.confirmacionSubscription.unsubscribe();
    }
  }
}
