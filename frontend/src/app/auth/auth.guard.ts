import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import {JwtPayload} from '../services/interfaces/JwtPayload';
import {jwtDecode} from 'jwt-decode';


export const roleGuard = (rolesPermitidos: string[]): CanActivateFn => {
  return (route, state) => {
    const router = inject(Router);
    const user = getRol();

    if (!user || !user.roles.some(r => rolesPermitidos.includes(r))) {
      router.navigateByUrl('/login');
      return false;
    }
    return true;
  };
};

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const user = sessionStorage.getItem('TOKEN_KEY');

  if (!user) {
    router.navigateByUrl('/login');
    return false;
  }
  return true;
};


export const redirectIfLoggedInGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const rol = sessionStorage.getItem('ROL');

  if (rol) {
    if (rol.includes('CLIENTE')) {
      router.navigateByUrl('/cartelera');
    } else if (rol.includes('ADMIN')) {
      router.navigateByUrl('/eventos/nuevo');
    } else {
      router.navigateByUrl('/login');
    }
    return false;
  }

  return true;
};
