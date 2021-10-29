import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Subscription } from 'rxjs';
import { CoberturaI } from 'src/app/models/cobertura.model';
import { SeguroService } from 'src/app/seguro/services/seguro.service';

@Component({
  selector: 'app-listado',
  styleUrls: ['listado.component.css'],
  templateUrl: 'listado.component.html',
})

export class ListadoComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['idCob', 'descripcion', 'precioDia', 'operaciones'];
  ELEMENT_DATA: CoberturaI[] = [];
  dataSource: MatTableDataSource<CoberturaI>;
  segurosSubscription: Subscription;
  
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private seguroService: SeguroService
  ){}
  
  ngOnInit(){}

  ngAfterViewInit(){
    this.segurosSubscription = this.seguroService.getSeguros()
    .subscribe(resp => {
      this.ELEMENT_DATA = resp;
      this.dataSource = new MatTableDataSource<CoberturaI>(this.ELEMENT_DATA);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    })
  }

  ngOnDestroy(){
    this.segurosSubscription.unsubscribe();
  }
}