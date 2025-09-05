import { Component, Input, signal } from '@angular/core';
import { Evento } from '../../../services/interfaces/Evento';
import { RouterLink } from '@angular/router'

@Component({
  selector: 'app-evento',
  imports: [RouterLink],
  templateUrl: './evento.component.html',
  styleUrl: './evento.component.css'
})
export class EventoComponent {
  @Input()
  evento!:Evento;


}
