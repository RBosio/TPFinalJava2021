import { NgModule } from '@angular/core';

import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSliderModule } from '@angular/material/slider';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatSidenavModule } from '@angular/material/sidenav';
const myComponents = [
  MatSnackBarModule,
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatRippleModule,
  MatSliderModule,
  MatSelectModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatRadioModule,
  MatCheckboxModule,
  MatDialogModule,
  MatTableModule,
  MatSidenavModule
]

@NgModule({
  declarations: [],
  imports: [...myComponents],
  exports: [...myComponents]
})
export class MaterialModule { }
