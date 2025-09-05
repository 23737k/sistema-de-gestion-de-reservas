import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import {jwtDecode} from 'jwt-decode';
import { AuthService } from "../services/auth.service";


export const roleGuard = (rolesPermitidos: string[]): CanActivateFn => {
  return (route, state) => {
    const router = inject(Router);
    const user = getUserFromToken();

    if (!user || !user.roles.some(r => rolesPermitidos.includes(r))) {
      router.navigateByUrl('/registro');
      return false;
    }
    return true;
  };
};

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const user = getUserFromToken();

  if (!user) {
    router.navigateByUrl('/registro');
    return false;
  }
  return true;
};



export interface JwtPayload {
  sub: string;
  username: string;
  roles: string[];
  exp: number;
}

export const getUserFromToken = (): JwtPayload | null => {
  const authService = inject(AuthService);
  const token = authService.getToken();
  if (!token) return null;
  try {
    console.log(jwtDecode<JwtPayload>(token))
    return jwtDecode<JwtPayload>(token);
  } catch (err) {
    return null;
  }
};
