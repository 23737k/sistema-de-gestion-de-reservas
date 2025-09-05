import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { RegistroReq } from 'src/app/services/interfaces/RegistroReq';

@Component({
  selector: 'app-registro',
  imports: [ReactiveFormsModule],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {
  private authService = inject(AuthService);
  registroForm: FormGroup;
  tiposDocumento = ['DNI', 'CI', 'LE'];

  constructor(private fb: FormBuilder) {
    this.registroForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      documento: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      tipo_de_documento: ['', Validators.required],
      telefono: ['', [Validators.required, Validators.pattern(/^\d+$/)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit() {
      if (this.registroForm.valid) {
        console.log('Datos enviados:', this.registroForm.value);
        const request : RegistroReq= {
          nombre: this.registroForm.value['nombre'],
          apellido: this.registroForm.value['apellido'],
          documento: this.registroForm.value['documento'],
          tipoDeDocumento: this.registroForm.value['tipo_de_documento'],
          telefono: this.registroForm.value['telefono'],
          email: this.registroForm.value['email'],
          password: this.registroForm.value['password'],
          rol: 'CLIENTE'
        };
        this.authService.registrar(request).subscribe({
          next: () => console.log('Usuario registrado con exito')
        })
      } else {
        this.registroForm.markAllAsTouched();
      }
  }


}
