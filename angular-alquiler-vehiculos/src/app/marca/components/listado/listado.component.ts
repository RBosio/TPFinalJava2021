import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Subscription } from 'rxjs';
import { MarcaI } from 'src/app/models/marca.model';
import { MarcaService } from '../../services/marca.service';

@Component({
  selector: 'app-listado',
  styleUrls: ['listado.component.css'],
  templateUrl: 'listado.component.html',
})

export class ListadoComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['idMarca', 'denominacion', 'operaciones'];
  ELEMENT_DATA: MarcaI[] = [];
  dataSource: MatTableDataSource<MarcaI>;
  marcasSubscription: Subscription;
  
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private marcaService: MarcaService
  ){}
  
  ngOnInit(){}

  ngAfterViewInit(){
    this.marcasSubscription = this.marcaService.getMarcas()
    .subscribe(resp => {
      this.ELEMENT_DATA = resp;
      this.dataSource = new MatTableDataSource<MarcaI>(this.ELEMENT_DATA);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    })
  }

  ngOnDestroy(){
    this.marcasSubscription.unsubscribe();
  }
}
