import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/services/auth.service';
import { UserLoginResponseI } from 'src/app/models/user.model';
import { SidenavService } from 'src/app/services/sidenav.service';
import { LocalService } from '../../services/local.service';

@Component({
  selector: 'app-header-usuario',
  templateUrl: './header-usuario.component.html',
  styleUrls: ['./header-usuario.component.css']
})
export class HeaderUsuarioComponent implements OnInit {
  usuario: UserLoginResponseI;
  opened: boolean = false;
  constructor(
    public authService: AuthService,
    private localService: LocalService,
    private sidenavService: SidenavService) {
  }
  
  ngOnInit(): void {
    this.usuario = this.localService.getJsonValue('user') || '';
    this.authService.userEvent.subscribe(resp => {
      this.usuario = resp;
    })
  }

  vendedor(){
    if(this.usuario.roles.filter(valor => valor.nombre == 'Vendedor').length > 0) return true;
    return false;
  }
  
  administrador(){
    if(this.usuario.roles.filter(valor => valor.nombre == 'Administrador').length > 0) return true;
    return false;
  }

  toggleSidenavVendedor(){
    this.sidenavService.toggleSidenav('vendedor');
  }
  
  toggleSidenavAdministrador(){
    this.sidenavService.toggleSidenav('administrador');
  }
}
