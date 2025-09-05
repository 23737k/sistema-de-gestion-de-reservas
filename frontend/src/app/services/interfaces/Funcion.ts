import { Disponibilidad } from "./Disponibilidad";
import { Lugar } from "./Lugar";

export interface Funcion {
  id: number;
  fecha: string;
  hora: string;
  lugar: Lugar;
  disponibilidades: Disponibilidad[];
}

