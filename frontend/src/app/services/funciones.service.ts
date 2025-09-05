import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Funcion } from './interfaces/Funcion';
import { environment } from '@environments/environment.development';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class FuncionesService {
  private http = inject(HttpClient);

  obtenerFunciones(eventoId:string):Observable<Funcion[]>{
    return this.http.get<Funcion[]>(`${environment.backendUrl}/eventos/${eventoId}/funciones`);
  }
}
