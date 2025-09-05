import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '@environments/environment.development'
import {concat, concatMap, forkJoin, iif, Observable, of} from 'rxjs';
import { EventoRes } from './interfaces/EventoRes';
import {EventoReq} from './interfaces/EventoReq';
import {FuncionReq} from './interfaces/FuncionReq';

@Injectable({
  providedIn: 'root'
})
export class EventoService {
  private http = inject(HttpClient)

  obtenerEventos(): Observable<EventoRes[]>{
    return this.http.get<EventoRes[]>(`${environment.backendUrl}/eventos`);
  }

  crearEvento(eventoReq: EventoReq, funciones: FuncionReq[]): Observable<any> {
    return this.http.post<EventoRes>(`${environment.backendUrl}/eventos`, eventoReq).pipe(
      concatMap(evento => {
        if (funciones && funciones.length > 0) {
          const peticiones = funciones.map(funcion =>
            this.http.post(`${environment.backendUrl}/eventos/${evento.id}/funciones`, funcion)
          );

          // forkJoin espera a que todas terminen y devuelve un array de resultados
          return forkJoin(peticiones);
        } else {
          return of(evento); // No hay funciones, devolvemos el evento creado
        }
      })
    );
  }



}
