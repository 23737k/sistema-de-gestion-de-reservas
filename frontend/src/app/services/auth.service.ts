import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { RegistroReq } from './interfaces/RegistroReq';
import { AuthRes } from './interfaces/AuthRes';
import { Observable, tap } from 'rxjs';
import { environment } from '@environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);

  registrar(registroReq:RegistroReq):Observable<AuthRes>{
    return this.http.post<AuthRes>(`${environment.backendUrl}/auth/register`,registroReq).pipe(
        tap(res => {
          if (res.token) {
            sessionStorage.setItem('TOKEN', res.token);
          }
        })
      );;
  }

}
