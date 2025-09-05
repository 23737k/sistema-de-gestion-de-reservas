import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '@environments/environment.development'
import { Observable } from 'rxjs';
import { Evento } from './interfaces/Evento';

@Injectable({
  providedIn: 'root'
})
export class EventoService {
  private http = inject(HttpClient)

  obtenerEventos(): Observable<Evento[]>{
    return this.http.get<Evento[]>(`${environment.backendUrl}/eventos`);
  }



}
