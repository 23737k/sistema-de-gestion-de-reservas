import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { RegistroReq } from './interfaces/RegistroReq';
import { AuthRes } from './interfaces/AuthRes';
import { Observable, tap } from 'rxjs';
import { environment } from '@environments/environment.development';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient);
  private TOKEN_KEY = 'authToken'
  private router = inject(Router);

  registrar(registroReq:RegistroReq):Observable<AuthRes>{
    return this.http.post<AuthRes>(`${environment.backendUrl}/auth/register`,registroReq).pipe(
        tap(res => {
          if (res.token) {
            sessionStorage.setItem(this.TOKEN_KEY, res.token);
          }
          this.router.navigateByUrl('')
        })
      );

  }

  getToken() {
    return sessionStorage.getItem(this.TOKEN_KEY);
  }

  logout() {
    sessionStorage.removeItem(this.TOKEN_KEY);
    this.router.navigateByUrl('/registro');
  }


}
