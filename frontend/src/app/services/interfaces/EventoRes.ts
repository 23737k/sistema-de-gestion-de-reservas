export interface EventoRes {
  id:number;
  nombre:string;
  descripcion:string;
  tipoDeEvento: 'OBRA_DE_TEATRO' | 'RECITAL' | 'CHARLA_CONFERENCIA';
}
