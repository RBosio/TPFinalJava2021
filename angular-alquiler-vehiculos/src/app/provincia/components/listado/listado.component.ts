import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { ProvinciaI } from 'src/app/models/provincia.model';
import { ProvinciaService } from '../../services/provincia.service';

@Component({
  selector: 'app-listado',
  styleUrls: ['listado.component.css'],
  templateUrl: 'listado.component.html',
})

export class ListadoComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['idPais', 'denominacion', 'pais', 'operaciones'];
  ELEMENT_DATA: ProvinciaI[] = [];
  dataSource: MatTableDataSource<ProvinciaI>;
  provinciasSubscription: Subscription;
  
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private ProvinciaService: ProvinciaService
  ){}
  
  ngOnInit(){}

  ngAfterViewInit(){
    this.provinciasSubscription = this.ProvinciaService.getProvincias()
    .subscribe(resp => {
      this.ELEMENT_DATA = resp;
      this.dataSource = new MatTableDataSource<ProvinciaI>(this.ELEMENT_DATA);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    })
  }

  ngOnDestroy(){
    this.provinciasSubscription.unsubscribe();
  }
}
