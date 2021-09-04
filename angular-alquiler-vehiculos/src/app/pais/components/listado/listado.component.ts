import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Subscription } from 'rxjs';
import { PaisI } from 'src/app/models/pais.model';
import { PaisService } from '../../services/pais.service';

@Component({
  selector: 'app-listado',
  styleUrls: ['listado.component.css'],
  templateUrl: 'listado.component.html',
})

export class ListadoComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['idPais', 'denominacion', 'operaciones'];
  ELEMENT_DATA: PaisI[] = [];
  dataSource: MatTableDataSource<PaisI>;
  paisesSubscription: Subscription;
  
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private paisService: PaisService
  ){}
  
  ngOnInit(){}

  ngAfterViewInit(){
    this.paisesSubscription = this.paisService.getPaises()
    .subscribe(resp => {
      this.ELEMENT_DATA = resp;
      this.dataSource = new MatTableDataSource<PaisI>(this.ELEMENT_DATA);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    })
  }

  ngOnDestroy(){
    this.paisesSubscription.unsubscribe();
  }
}
