import { NgModule } from '@angular/core';

import {MatSnackBarModule} from '@angular/material/snack-bar';
const myComponents = [MatSnackBarModule]

@NgModule({
  declarations: [],
  imports: [...myComponents],
  exports: [...myComponents]
})
export class MaterialModule { }
