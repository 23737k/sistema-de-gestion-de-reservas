import { CommonModule } from '@angular/common';
import {Component, inject} from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormBuilder, FormControl,
  FormGroup,
  ReactiveFormsModule, ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {EventoService} from '../../services/evento.service';
import {FuncionReq} from '../../services/interfaces/FuncionReq';

@Component({
  selector: 'app-evento-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './evento-form.component.html',
  styleUrl: './evento-form.component.css'
})
export class EventoFormComponent {
  eventForm: FormGroup;
  private eventoService: EventoService = inject(EventoService);

  tiposDeEventos = [
    {
    tipoDeEvento: 'OBRA_DE_TEATRO',
    tiposDeEntradas:['ENTRADA_GENERAL', 'ENTRADA_VIP']
    },
    {
      tipoDeEvento: 'RECITAL',
      tiposDeEntradas:['PALCO', 'PLATEA', 'CAMPO']
    },
    {
      tipoDeEvento:'CHARLA_CONFERENCIA',
      tiposDeEntradas:['CON_MEET_AND_GREET', 'SIN_MEET_AND_GREET']
    }
  ];

  constructor(private fb: FormBuilder) {
    this.eventForm = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      tipoDeEvento: ['', Validators.required],
      funciones: this.fb.array<FuncionReq>([])
    });
    // @ts-ignore
    this.eventForm.get('tipoDeEvento').valueChanges.subscribe(() => {
      this.funciones.controls.forEach((funcion) => {
        const dispArray = funcion.get('disponibilidades') as FormArray;
        dispArray.controls.forEach(disp => disp.get('tipoDeEntrada')!.setValue(''));
      });
    });
  }

  get funciones(): FormArray {
    return this.eventForm.get('funciones') as FormArray;
  }

  addFuncion() {
    const funcionForm = this.fb.group({
      fecha: ['', [Validators.required, fechaFuturaValidator()]],
      hora: ['', Validators.required],
      lugar: this.fb.group({
        direccion: ['', Validators.required],
        altura: ['', Validators.required],
        localidad: ['', Validators.required],
        provincia: ['', Validators.required]
      }),
      disponibilidades: this.fb.array([
        this.fb.group({
          tipoDeEntrada: ['', Validators.required],
          precio: ['', [Validators.required, Validators.min(1)]],
          cuposTotales: ['', [Validators.required, Validators.min(1)]],
        })
      ], [minArrayLength(1),Validators.required])
    });
    this.funciones.push(funcionForm);
  }


  removeFuncion(index: number) {
    this.funciones.removeAt(index);
  }

  disponibilidades(funcionIndex: number): FormArray {
    return this.funciones.at(funcionIndex).get('disponibilidades') as FormArray;
  }

  addDisponibilidad(funcionIndex: number) {
    const dispArray = this.disponibilidades(funcionIndex);

    if (dispArray && dispArray.controls.some(ctrl => ctrl.invalid)) {
      dispArray.controls.forEach(ctrl => {
        if (ctrl instanceof FormGroup || ctrl instanceof FormArray || ctrl instanceof FormControl) {
          this.markFormGroupTouched(ctrl as FormGroup | FormArray);
        }
      });
      return;
    }

    const newDisp = this.fb.group({
      tipoDeEntrada: ['', Validators.required],
      precio: [null, [Validators.required, Validators.min(1)]],
      cuposTotales: [null, [Validators.required, Validators.min(1)]]
    });

    dispArray.push(newDisp);
  }

  removeDisponibilidad(funcionIndex: number, dispIndex: number) {
    this.disponibilidades(funcionIndex).removeAt(dispIndex);
  }

  submit() {
    if (this.eventForm.valid) {
      console.log(this.eventForm.value);
      this.eventoService.crearEvento(this.eventForm.value, this.funciones.value).subscribe({
        next: () => {console.log('Evento creado')}
      });
    } else {
      alert('Completa los campos obligatorios');
      this.markFormGroupTouched(this.eventForm);
    }
  }

  private markFormGroupTouched(formGroup: FormGroup | FormArray) {
    Object.values(formGroup.controls).forEach(control => {
      if (control instanceof FormControl) {
        control.markAsTouched();
      } else if (control instanceof FormGroup || control instanceof FormArray) {
        this.markFormGroupTouched(control);
      }
    });
  }


  tiposDeEntradaPorFuncion(i: number) {
    const tipoDeEvento = this.eventForm.get('tipoDeEvento')!.value;
    const tipo = this.tiposDeEventos.find(t => t.tipoDeEvento === tipoDeEvento);
    return tipo ? tipo.tiposDeEntradas : [];
  }
}

export function minArrayLength(min: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (control instanceof FormArray) {
      return control.length >= min ? null : { minArrayLength: { requiredLength: min, actualLength: control.length } };
    }
    return null;
  };
}

  export function fechaFuturaValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const fechaValor = new Date(control.value);
      const hoy = new Date();

      hoy.setHours(0, 0, 0, 0);

      if (!control.value) {
        return null;
      }

      return fechaValor > hoy ? null : { fechaPasada: true };
    };


}
