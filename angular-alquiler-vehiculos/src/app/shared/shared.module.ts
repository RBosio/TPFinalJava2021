import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../angular-material/material.module';

import { FooterComponent } from './components/footer/footer.component';
import { HeaderUsuarioComponent } from './components/header-usuario/header-usuario.component';
import { DialogoConfirmacionComponent } from './components/dialogo-confirmacion/dialogo-confirmacion.component';
import { SharedRoutingModule } from './shared-routing.module';
import { PaginatorPipe } from './pipes/paginator.pipe';


@NgModule({
  declarations: [
    FooterComponent,
    HeaderUsuarioComponent,
    DialogoConfirmacionComponent,
    PaginatorPipe
  ],
  imports: [
    CommonModule,
    SharedRoutingModule,
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
    FormsModule,
    PaginatorPipe
  ]
})
export class SharedModule { }
