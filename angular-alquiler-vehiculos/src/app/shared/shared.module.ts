import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../angular-material/material.module';

import { FooterComponent } from './components/footer/footer.component';
import { HeaderUsuarioComponent } from './components/header-usuario/header-usuario.component';


@NgModule({
  declarations: [
    FooterComponent,
    HeaderUsuarioComponent
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
    MaterialModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class SharedModule { }
