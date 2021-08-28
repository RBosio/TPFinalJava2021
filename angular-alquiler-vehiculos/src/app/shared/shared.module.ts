import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../angular-material/material.module';

import { FooterComponent } from './components/footer/footer.component';
import { HeaderUsuarioComponent } from './components/header-usuario/header-usuario.component';
import { DialogoConfirmacionComponent } from './components/dialogo-confirmacion/dialogo-confirmacion.component';


@NgModule({
  declarations: [
    FooterComponent,
    HeaderUsuarioComponent,
    DialogoConfirmacionComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [],
  exports: [
    CommonModule,
    FooterComponent,
    HeaderUsuarioComponent,
    DialogoConfirmacionComponent,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class SharedModule { }
