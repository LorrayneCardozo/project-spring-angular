import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(
    private authService: AuthService,
    private router: Router
    ) {}

    username: string = '';
    password: string = '';

    login(): void {
      const loginData = {
        username: this.username,
        password: this.password
      }

      this.authService.login(loginData).subscribe({
        next: (response: any) => {
          console.log('Login realizado com sucesso!', response);
          this.router.navigate(['/doctor']);
        },
        error: (error: any) => {
          console.error('Erro durante o login:', error);
        }
      });   
    }

}
