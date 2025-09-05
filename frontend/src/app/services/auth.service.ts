import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { RegistroReq } from './interfaces/RegistroReq';
import { AuthRes } from './interfaces/AuthRes';
import { Observable, tap } from 'rxjs';
import { environment } from '@environments/environment.development';
import { Router } from '@angular/router';
import { LoginReq } from './interfaces/LoginReq';
import {jwtDecode} from 'jwt-decode';
import {JwtPayload} from './interfaces/JwtPayload';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient);
  private TOKEN_KEY = 'authToken'
  private router = inject(Router);
  private baseUrl = environment.backendUrl;


  registrar(registroReq: RegistroReq): Observable<AuthRes> {
    return this.http.post<AuthRes>(`${this.baseUrl}/auth/register`, registroReq).pipe(
      tap(res => {
        if (res.token) {
          sessionStorage.setItem(this.TOKEN_KEY, res.token);

          const user = jwtDecode<JwtPayload>(res.token);

          sessionStorage.setItem('ROL', user.roles[0]);

          switch (user.roles[0]) {
            case 'CLIENTE':
              this.router.navigateByUrl('/cartelera');
              break;
            case 'ADMIN':
              this.router.navigateByUrl('/eventos/nuevo');
              break;
            default:
              this.router.navigateByUrl('');
          }
        }
      })
    );
  }

  login(loginReq: LoginReq) {
    return this.http.post<AuthRes>(`${this.baseUrl}/auth/login`, loginReq).pipe(
      tap(res => {
        if (res.token) {
          sessionStorage.setItem(this.TOKEN_KEY, res.token);


          const user = jwtDecode<JwtPayload>(res.token);

          sessionStorage.setItem('ROL', user.roles[0]);


          switch (user.roles[0]) {
            case 'CLIENTE':
              this.router.navigateByUrl('/cartelera');
              break;
            case 'ADMIN':
              this.router.navigateByUrl('/eventos/nuevo');
              break;
            default:
              this.router.navigateByUrl('');
          }
        }
      })
    );
  }



  getToken() {
    return sessionStorage.getItem(this.TOKEN_KEY);
  }

  logout() {
    sessionStorage.removeItem(this.TOKEN_KEY);
    sessionStorage.removeItem('ROL');
    this.router.navigateByUrl('/login');
  }


}
