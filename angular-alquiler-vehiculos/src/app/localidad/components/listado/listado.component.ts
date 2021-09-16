import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { LocalidadI } from 'src/app/models/localidad.model';
import { LocalidadService } from '../../services/localidad.service';

@Component({
  selector: 'app-listado',
  styleUrls: ['listado.component.css'],
  templateUrl: 'listado.component.html',
})

export class ListadoComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['idPais', 'denominacion', 'provincia', 'pais', 'operaciones'];
  ELEMENT_DATA: LocalidadI[] = [];
  dataSource: MatTableDataSource<LocalidadI>;
  LocalidadsSubscription: Subscription;
  
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private LocalidadService: LocalidadService
  ){}
  
  ngOnInit(){}

  ngAfterViewInit(){
    this.LocalidadsSubscription = this.LocalidadService.getLocalidades()
    .subscribe(resp => {
      this.ELEMENT_DATA = resp;
      this.dataSource = new MatTableDataSource<LocalidadI>(this.ELEMENT_DATA);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    })
  }

  ngOnDestroy(){
    this.LocalidadsSubscription.unsubscribe();
  }
}
