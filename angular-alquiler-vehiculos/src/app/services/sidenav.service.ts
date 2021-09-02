import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SidenavService {
  openE = new EventEmitter<boolean>();
  modeE = new EventEmitter<string>();
  open: boolean;
  mode: string;
  constructor() {
    this.open = false;
    this.mode = '';
  }

  toggleSidenav(mode: string){
    this.open = !this.open;
    this.openE.emit(this.open);
    this.modeE.emit(mode);
  }
}
