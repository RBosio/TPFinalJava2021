import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CoberturaI } from 'src/app/models/cobertura.model';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';
import { SeguroService } from '../../services/seguro.service';

@Component({
  selector: 'app-eliminar',
  templateUrl: './eliminar.component.html',
  styleUrls: ['./eliminar.component.css']
})
export class EliminarComponent implements OnInit, OnDestroy {
  seguro: CoberturaI;
  routeSubscription: Subscription;
  seguroSubscription: Subscription;
  eliminarSubscription: Subscription;
  confirmacionSubscription: Subscription;
  constructor(
    private confirmacion: MatDialog,
    private route: ActivatedRoute,
    private seguroService: SeguroService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {
    this.seguro = {"idCob": 0, "descripcion": '', "precioDia": 0};
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.seguroSubscription = this.seguroService.getSeguro(params.id)
      .subscribe(resp => {
        this.seguro = resp;
      })
    })
  }

  eliminarSeguro(){
    this.confirmacionSubscription = this.confirmacion
      .open(DialogoConfirmacionComponent, {
        data: `¿Desea eliminar el seguro?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          const idCob = this.seguro.idCob;
          this.eliminarSubscription = this.seguroService.eliminarSeguro(idCob)
          .subscribe(resp => {
            this.openSnackBar('Seguro eliminado con exito', 'Cerrar');
            this.router.navigateByUrl('seguros');
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
    this.seguroSubscription.unsubscribe();
    if(this.eliminarSubscription){
      this.eliminarSubscription.unsubscribe();
      this.confirmacionSubscription.unsubscribe();
    }
  }
}
