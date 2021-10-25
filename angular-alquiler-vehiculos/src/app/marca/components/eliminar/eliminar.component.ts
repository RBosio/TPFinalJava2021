import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { MarcaI } from 'src/app/models/marca.model';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';
import { MarcaService } from '../../services/marca.service';

@Component({
  selector: 'app-eliminar',
  templateUrl: './eliminar.component.html',
  styleUrls: ['./eliminar.component.css']
})
export class EliminarComponent implements OnInit, OnDestroy {
  marca: MarcaI;
  routeSubscription: Subscription;
  marcaSubscription: Subscription;
  eliminarSubscription: Subscription;
  confirmacionSubscription: Subscription;
  constructor(
    private confirmacion: MatDialog,
    private route: ActivatedRoute,
    private marcaService: MarcaService,
    private _snackBar: MatSnackBar,
    private router: Router
  ) {
    this.marca = {"idMarca": 0, "denominacion": ''};
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.marcaSubscription = this.marcaService.getMarca(params.id)
      .subscribe(resp => {
        this.marca = resp;
      })
    })
  }

  eliminarMarca(){
    this.confirmacionSubscription = this.confirmacion
      .open(DialogoConfirmacionComponent, {
        data: `¿Desea eliminar la marca?`
      })
      .afterClosed()
      .subscribe((confirmado: Boolean) => {
        if (confirmado) {
          const idmarca = this.marca.idMarca;
          this.eliminarSubscription = this.marcaService.eliminarmarca(idmarca)
          .subscribe(resp => {
            this.openSnackBar('Marca eliminada con exito', 'Cerrar');
            this.router.navigateByUrl('marcas');
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
    this.marcaSubscription.unsubscribe();
    if(this.eliminarSubscription){
      this.eliminarSubscription.unsubscribe();
      this.confirmacionSubscription.unsubscribe();
    }
  }
}
