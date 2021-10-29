import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Subscription } from 'rxjs';
import { ExtraI } from 'src/app/models/extra.model';
import { ExtraService } from 'src/app/extra/services/extra.service';

@Component({
  selector: 'app-listado',
  styleUrls: ['listado.component.css'],
  templateUrl: 'listado.component.html',
})

export class ListadoComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['idExtra', 'descripcion', 'precioDia', 'operaciones'];
  ELEMENT_DATA: ExtraI[] = [];
  dataSource: MatTableDataSource<ExtraI>;
  extrasSubscription: Subscription;
  
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private extraService: ExtraService
  ){}
  
  ngOnInit(){}

  ngAfterViewInit(){
    this.extrasSubscription = this.extraService.getExtras()
    .subscribe(resp => {
      this.ELEMENT_DATA = resp;
      this.dataSource = new MatTableDataSource<ExtraI>(this.ELEMENT_DATA);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    })
  }

  ngOnDestroy(){
    this.extrasSubscription.unsubscribe();
  }
}