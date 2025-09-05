import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from "@angular/router";
import { catchError, throwError } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { LoginReq } from 'src/app/services/interfaces/LoginReq';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  private authService = inject(AuthService);
  badCredentials = signal(false);

    constructor(private fb: FormBuilder) {
      this.loginForm = this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
      });
    }

    onSubmit() {
      if (this.loginForm.valid) {
        console.log('Datos login:', this.loginForm.value);
        const loginReq:LoginReq = {
          email: this.loginForm.value['email'],
          password: this.loginForm.value['password']
        }
        this.authService.login(loginReq).pipe(
          catchError((err:HttpErrorResponse) =>{
            if(err.status === 401)
              this.badCredentials.set(true);
            return throwError(() => err);
          })
        ).subscribe({
           next: () => console.log('Usuario logueado con exito')
        })
      } else {
        this.loginForm.markAllAsTouched();
      }
    }
}
