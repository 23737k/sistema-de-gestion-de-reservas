import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FuncionesService } from 'src/app/services/funciones.service';
import { FuncionRes} from '../../services/interfaces/FuncionRes';
import { DatePipe, registerLocaleData, TitleCasePipe } from '@angular/common';
import localeEs from '@angular/common/locales/es';

registerLocaleData(localeEs);

@Component({
  selector: 'app-funciones',
  imports: [DatePipe, TitleCasePipe],
  templateUrl: './funciones.component.html',
  styleUrl: './funciones.component.css'
})
export class FuncionesComponent implements OnInit{
  private funcionesService = inject(FuncionesService);
  private router = inject(ActivatedRoute);

  funciones = signal<FuncionRes[]>([]);

  ngOnInit(){
    const eventoId:string= this.router.snapshot.paramMap.get('eventoId')!;
    this.funcionesService.obtenerFunciones(eventoId).subscribe({
      next: (res) => this.funciones.set(res)
    })
  }

  cuposDisponibles(funcion:FuncionRes){
    const disponibilidades = funcion.disponibilidades;
    return disponibilidades.reduce((total,disponibilidad) => total + (disponibilidad.cuposTotales - disponibilidad.cuposOcupados),0);
  }

}
