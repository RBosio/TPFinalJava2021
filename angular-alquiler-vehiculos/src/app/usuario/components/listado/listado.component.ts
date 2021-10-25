import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { UserLoginResponseI } from 'src/app/models/user.model';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-listado',
  styleUrls: ['listado.component.css'],
  templateUrl: 'listado.component.html',
})

export class ListadoComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['dni', 'nombre', 'apellido', 'telefono'];
  ELEMENT_DATA: UserLoginResponseI[] = [];
  dataSource: MatTableDataSource<UserLoginResponseI>;
  usuariosSubscription: Subscription;
  
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private usuarioService: UsuarioService
  ){}
  
  ngOnInit(){}

  ngAfterViewInit(){
    this.usuariosSubscription = this.usuarioService.getUsuarios()
    .subscribe(resp => {
      this.ELEMENT_DATA = resp;
      this.dataSource = new MatTableDataSource<UserLoginResponseI>(this.ELEMENT_DATA);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    })
  }

  ngOnDestroy(){
    this.usuariosSubscription.unsubscribe();
  }
}
