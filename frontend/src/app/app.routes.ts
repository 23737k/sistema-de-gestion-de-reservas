import { Routes } from '@angular/router';
import { CarteleraComponent } from './features/cartelera/cartelera.component';
import { FuncionesComponent } from './features/funciones/funciones.component';
import { RegistroComponent } from './features/registro/registro.component';

export const routes: Routes = [
  {path:'cartelera', component:CarteleraComponent},
  {path:'cartelera/:eventoId/funciones', component:FuncionesComponent},
  {path:'registro', component:RegistroComponent},
  {path:'**', redirectTo:'cartelera'}
];
