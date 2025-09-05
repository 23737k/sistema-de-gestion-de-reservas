import { Component, inject, OnInit, signal } from '@angular/core';
import { EventoRes } from '../../services/interfaces/EventoRes';
import { EventoComponent } from './components/evento.component';
import { EventoService } from 'src/app/services/evento.service';

@Component({
  selector: 'app-cartelera',
  imports: [EventoComponent],
  templateUrl: './cartelera.component.html',
  styleUrl: './cartelera.component.css'
})
export class CarteleraComponent implements OnInit{
  private eventoService = inject(EventoService);

  ngOnInit(): void {
    this.eventoService.obtenerEventos().subscribe({
      next: (resp) => this.eventos.set(resp),
    })
  }

  eventos=signal<EventoRes[]>([]);



}
