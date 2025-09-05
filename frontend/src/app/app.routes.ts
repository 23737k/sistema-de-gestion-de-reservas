import { Routes, CanActivateFn } from '@angular/router';
import { CarteleraComponent } from './features/cartelera/cartelera.component';
import { FuncionesComponent } from './features/funciones/funciones.component';
import { RegistroComponent } from './features/registro/registro.component';
import { authGuard, roleGuard } from './auth/auth.guard';

export const routes: Routes = [
  {path:'cartelera', component:CarteleraComponent, canActivate:[roleGuard(['CLIENTE'])]},
  {path:'cartelera/:eventoId/funciones', component:FuncionesComponent, canActivate:[roleGuard(['CLIENTE'])]},
  {path:'registro', component:RegistroComponent},
  {path:'**', redirectTo:'cartelera'}
];
