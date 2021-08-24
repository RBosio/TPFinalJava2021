import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderUsuarioComponent } from './components/header-usuario/header-usuario.component';
import { MaterialModule } from '../angular-material/material.module';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    FooterComponent,
    HeaderUsuarioComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  providers: [],
  exports: [
    CommonModule,
    FooterComponent,
    HeaderUsuarioComponent,
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class SharedModule { }
