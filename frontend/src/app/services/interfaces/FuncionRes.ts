import {DisponibilidadRes} from './DisponibilidadRes';
import {Lugar} from './Lugar';

export interface FuncionRes {
  id: number;
  fecha: string;
  hora: string;
  lugar: Lugar;
  disponibilidades: DisponibilidadRes[];
}
