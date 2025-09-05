import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment.development';
import {FuncionRes} from './interfaces/FuncionRes';

@Injectable({
  providedIn: 'root'
})
export class FuncionesService {
  private http = inject(HttpClient);

  obtenerFunciones(eventoId:string):Observable<FuncionRes[]>{
    return this.http.get<FuncionRes[]>(`${environment.backendUrl}/eventos/${eventoId}/funciones`);
  }
}
