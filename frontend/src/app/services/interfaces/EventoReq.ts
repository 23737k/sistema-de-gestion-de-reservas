export interface EventoReq {
  nombre: string;
  descripcion: string;
  tipoDeEvento: 'OBRA_DE_TEATRO' | 'RECITAL' | 'CHARLA_CONFERENCIA';
}
