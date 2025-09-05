import { Component, Input, signal } from '@angular/core';
import { EventoRes } from '../../../services/interfaces/EventoRes';
import { RouterLink } from '@angular/router'

@Component({
  selector: 'app-evento',
  imports: [RouterLink],
  templateUrl: './evento.component.html',
  styleUrl: './evento.component.css'
})
export class EventoComponent {
  @Input()
  evento!:EventoRes;


}
