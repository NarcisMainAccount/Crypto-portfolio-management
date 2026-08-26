import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  loginForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl('')
  });

  onSubmit() {
    console.log(this.loginForm.value);
  }

  import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  loginForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl('')
  });

  constructor(private http: HttpClient) {}

  onSubmit() {
    this.http.post(
      'http://localhost:8080/api/auth/login',
      this.loginForm.value,
      {
        withCredentials: true
      }
    ).subscribe({
      next: () => {
        console.log('Login successful');
      },
      error: (error) => {
        console.error('Login failed', error);
      }
    });
  }
}
