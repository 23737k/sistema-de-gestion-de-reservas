import { Routes, CanActivateFn } from '@angular/router';
import { CarteleraComponent } from './features/cartelera/cartelera.component';
import { FuncionesComponent } from './features/funciones/funciones.component';
import { RegistroComponent } from './features/registro/registro.component';
import {authGuard, redirectIfLoggedInGuard, roleGuard} from './auth/auth.guard';
import { LoginComponent } from './features/login/login.component';
import { EventoFormComponent } from './features/evento-form/evento-form.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [redirectIfLoggedInGuard] },
  { path: 'registro', component: RegistroComponent, canActivate: [redirectIfLoggedInGuard] },


  { path: 'cartelera', component: CarteleraComponent, canActivate: [roleGuard(['CLIENTE'])] },
  { path: 'cartelera/:eventoId/funciones', component: FuncionesComponent, canActivate: [roleGuard(['CLIENTE'])] },
  { path: 'eventos/nuevo', component: EventoFormComponent, canActivate: [roleGuard(['ADMIN'])] },


  { path: '**', redirectTo: 'login' }
];
