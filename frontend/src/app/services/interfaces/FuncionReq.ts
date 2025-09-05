import { DisponibilidadReq } from "./DisponibilidadReq";
import {Lugar} from "./Lugar";

export interface FuncionReq {
  id: number;
  fecha: string;
  hora: string;
  lugar: Lugar;
  disponibilidades: DisponibilidadReq[];
}

