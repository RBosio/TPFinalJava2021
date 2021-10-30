import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort } from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Subscription } from 'rxjs';
import { AlquilerI } from 'src/app/models/alquiler.model';
import { DialogoConfirmacionComponent } from 'src/app/shared/components/dialogo-confirmacion/dialogo-confirmacion.component';
import { AlquilerService } from '../../services/alquiler.service';

@Component({
  selector: 'app-listado',
  styleUrls: ['listado.component.css'],
  templateUrl: 'listado.component.html',
})

export class ListadoComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['dni', 'fechahoraini', 'fechahorafin', 'nombre', 'mail', 'operaciones'];
  ELEMENT_DATA: AlquilerI[] = [];
  dataSource: MatTableDataSource<AlquilerI>;
  alquilerSubscription: Subscription;
  confirmacionSubscription: Subscription;
  cursoSubscription: Subscription;
  
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private alquilerService: AlquilerService,
    private confirmacion: MatDialog,
    private _snackBar: MatSnackBar
  ){}
  
  ngOnInit(){}

  ngAfterViewInit(){
    this.alquilerSubscription = this.alquilerService.getAlquileres()
    .subscribe(resp => {
      resp.forEach(a => {
        a.fechaHoraInicio = a.fechaHoraInicio.split('T')[0]+' '+a.fechaHoraInicio.split('T')[1];
        a.fechaHoraFin = a.fechaHoraFin.split('T')[0]+' '+a.fechaHoraFin.split('T')[1];
        a.estados = [];
        switch (a.estado) {
          case 'Pendiente':
            a.estados[0] = true;
          break;
          case 'En Curso':
            a.estados[1] = true;
          break;
          case 'Terminado':
            a.estados[2] = true;
          break;
          case 'Cancelado':
            a.estados[3] = true;
          break;
        }
      })
      this.ELEMENT_DATA = resp;
      this.dataSource = new MatTableDataSource<AlquilerI>(this.ELEMENT_DATA);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    })
  }

  confirmarAlquiler(dni: string, fechaHoraIni: string, indice: number){
    if(!this.ELEMENT_DATA[indice].estados[1]){
      fechaHoraIni = fechaHoraIni.split(' ')[0]+'T'+fechaHoraIni.split(' ')[1];
      this.confirmacionSubscription = this.confirmacion
          .open(DialogoConfirmacionComponent, {
            data: `¿Desea poner en curso el alquiler?`
          })
          .afterClosed()
          .subscribe((confirmado: Boolean) => {
            if (confirmado) {
              this.cursoSubscription = this.alquilerService.alquilerEnCurso(dni, fechaHoraIni)
              .subscribe(resp => {
                this.openSnackBar('Alquiler en curso con exito', 'Cerrar');
                this.ELEMENT_DATA[indice].estados[0] = false;
                this.ELEMENT_DATA[indice].estados[1] = true;
                this.ELEMENT_DATA[indice].estados[2] = false;
                this.ELEMENT_DATA[indice].estados[3] = false;
              });
            }
          });
    }
  }

  completarAlquiler(dni: string, fechaHoraIni: string, indice: number){
    if(!this.ELEMENT_DATA[indice].estados[2]){
      fechaHoraIni = fechaHoraIni.split(' ')[0]+'T'+fechaHoraIni.split(' ')[1];
      this.confirmacionSubscription = this.confirmacion
          .open(DialogoConfirmacionComponent, {
            data: `¿Desea completar el alquiler?`
          })
          .afterClosed()
          .subscribe((confirmado: Boolean) => {
            if (confirmado) {
              this.cursoSubscription = this.alquilerService.alquilerCompletado(dni, fechaHoraIni)
              .subscribe(resp => {
                this.openSnackBar('Alquiler completado con exito', 'Cerrar');
                this.ELEMENT_DATA[indice].estados[0] = false;
                this.ELEMENT_DATA[indice].estados[1] = false;
                this.ELEMENT_DATA[indice].estados[2] = true;
                this.ELEMENT_DATA[indice].estados[3] = false;
              });
            }
          });
    }
  }

  cancelarAlquiler(dni: string, fechaHoraIni: string, indice: number){
    if(!this.ELEMENT_DATA[indice].estados[3]){
      fechaHoraIni = fechaHoraIni.split(' ')[0]+'T'+fechaHoraIni.split(' ')[1];
      this.confirmacionSubscription = this.confirmacion
          .open(DialogoConfirmacionComponent, {
            data: `¿Desea cancelar el alquiler?`
          })
          .afterClosed()
          .subscribe((confirmado: Boolean) => {
            if (confirmado) {
              this.cursoSubscription = this.alquilerService.alquilerCancelado(dni, fechaHoraIni)
              .subscribe(resp => {
                this.openSnackBar('Alquiler cancelado con exito', 'Cerrar');
                this.ELEMENT_DATA[indice].estados[0] = false;
                this.ELEMENT_DATA[indice].estados[1] = false;
                this.ELEMENT_DATA[indice].estados[2] = false;
                this.ELEMENT_DATA[indice].estados[3] = true;
              });
            }
          });
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
    this.alquilerSubscription.unsubscribe();
    if(this.confirmacionSubscription){
      this.confirmacionSubscription.unsubscribe();
    }
  }
}