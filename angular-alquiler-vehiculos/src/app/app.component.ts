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
    {"item": "Paises", "options": ["Listado", "Crear pais", "Actualizar pais", "Eliminar pais"]},
    {"item": "Provincias", "options": ["Listado", "Crear provincia", "Actualizar provincia", "Eliminar provincia"]},
    {"item": "Localidades", "options": ["Listado", "Crear localidad", "Actualizar localidad", "Eliminar localidad"]},
    {"item": "Usuarios", "options": ["Listado", "Crear usuario", "Actualizar usuario", "Eliminar usuario"]},
    {"item": "Vehiculos", "options": ["Listado", "Crear vehiculo", "Actualizar vehiculo", "Eliminar vehiculo"]},
    {"item": "Marcas", "options": ["Listado", "Crear marca", "Actualizar marca", "Eliminar marca"]},
    {"item": "Seguros", "options": ["Listado", "Crear seguro", "Actualizar seguro", "Eliminar seguro"]},
    {"item": "Extras", "options": ["Listado", "Crear extra", "Actualizar extra", "Eliminar extra"]}
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
