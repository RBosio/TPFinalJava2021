import { Component, OnInit } from '@angular/core';
import { SidenavService } from './services/sidenav.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  toggle: boolean;
  mode: string;
  
  dataVendedor = [
    {"item": "Alquileres", "options": ["Listado", "Confirmar alquiler", "Confirmar devolucion", "Cancelar alquiler"]}
  ];
  dataAdministrador = [
    {"item": "Paises", "options": ["Listado", "Agregar nuevo pais"]},
    {"item": "Provincias", "options": ["Listado", "Agregar nueva provincia"]},
    {"item": "Localidades", "options": ["Listado", "Agregar nueva localidad"]},
    {"item": "Usuarios", "options": ["Listado", "Agregar nuevo usuario"]},
    {"item": "Vehiculos", "options": ["Listado", "Agregar nuevo vehiculo"]},
    {"item": "Marcas", "options": ["Listado", "Agregar nueva marca"]},
    {"item": "Seguros", "options": ["Listado", "Agregar nuevo seguro"]},
    {"item": "Extras", "options": ["Listado", "Agregar nuevo extra"]}
  ];
  dataToggleVendedor: boolean[];
  dataToggleAdministrador: boolean[];
  ventas: boolean;

  block: boolean
  constructor(
    private sidenavService: SidenavService
  ) {
    this.toggle = false;
    this.mode = 'vendedor';
    this.dataToggleVendedor = [false];
    this.dataToggleAdministrador = [false, false, false, false, false, false, false, false];
  }

  ngOnInit(){
    this.sidenavService.openE.subscribe(resp => {
      this.toggle = resp;
    });
    this.sidenavService.modeE.subscribe(resp => {
      this.mode = resp;
    });
  }
  
  toggleVendedor(index: number){
    if(!this.dataToggleVendedor[index]){
      this.dataToggleVendedor = this.dataToggleVendedor.map(data => data = false);
      this.dataToggleVendedor[index] = true;
    }else{
      this.dataToggleVendedor[index] = false;
    }
  }

  toggleAdministrador(index: number){
    if(!this.dataToggleAdministrador[index]){
      this.dataToggleAdministrador = this.dataToggleAdministrador.map(data => data = false);
      this.dataToggleAdministrador[index] = true;
    }else{
      this.dataToggleAdministrador[index] = false;
    }
  }

  opciones(options: string){
    return options.split(' ')[0].toLowerCase();
  }
}
