import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../api.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  isRegistering = false;
  isSubmitting = false;
  errorMessage = '';
  loginForm = new FormGroup({
    email: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.email] }),
    username: new FormControl('', { nonNullable: true }),
    password: new FormControl('', { nonNullable: true, validators: [Validators.required, Validators.minLength(8)] })
  });

  constructor(
    private readonly api: ApiService,
    private readonly router: Router
  ) {}

  toggleMode(): void {
    this.isRegistering = !this.isRegistering;
    this.errorMessage = '';
    this.loginForm.controls.username.setValidators(this.isRegistering ? [Validators.required, Validators.minLength(3)] : []);
    this.loginForm.controls.username.updateValueAndValidity();
  }

  onSubmit(): void {
    if (this.loginForm.invalid || this.isSubmitting) { this.loginForm.markAllAsTouched(); return; }
    this.isSubmitting = true;
    this.errorMessage = '';
    const { email, username, password } = this.loginForm.getRawValue();
    const request = this.isRegistering ? this.api.register({ email, username, password }) : this.api.login(email, password);
    request.subscribe({
      next: () => {
        if (this.isRegistering) { this.isRegistering = false; this.isSubmitting = false; this.loginForm.controls.password.reset(); return; }
        this.router.navigate(['/dashboard']);
      },
      error: (error: { error?: string }) => {
        this.errorMessage = error.error || (this.isRegistering ? 'Unable to create your account.' : 'Email or password is incorrect.');
        this.isSubmitting = false;
      }
    });
  }
}
