import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderUsuarioComponent } from './components/header-usuario/header-usuario.component';


@NgModule({
  declarations: [
    FooterComponent,
    HeaderUsuarioComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FooterComponent,
    HeaderUsuarioComponent
  ]
})
export class SharedModule { }
