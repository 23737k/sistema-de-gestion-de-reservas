export interface RegistroReq{
    nombre:string;
    apellido:string;
    documento:string;
    tipoDeDocumento:string,
    telefono:string,
    email:string;
    password:string;
    rol: 'ADMIN' | 'CLIENTE'
}
