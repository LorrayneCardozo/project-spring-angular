import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap, filter } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth.service';
import { PersonService } from 'src/app/services/person.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  
  username: string = '';
  role: string = '';
  name: string = '';
  showSidebar: boolean = false;

  constructor(
    private authService: AuthService,
    private personService: PersonService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.getUserAuthenticated().subscribe((user: any) => {
      this.name = user.name;
      this.role = user.role;
      this.showSidebar = true;
    });
    // this.authService.isAuthenticated().pipe(
    //   filter(isAuthenticated => isAuthenticated),
    //   switchMap(() => this.authService.getUsernameAuthenticated()),
    //   switchMap(username => {
    //     this.username = username;
    //     return this.personService.getUser(username);
    //   })
    // ).subscribe((response: any) => {
    //   this.name = response?.name;
    //   this.role = response?.role;
    //   this.showSidebar = true;
    // });
  }

  logout(): void {
    this.authService.logout().subscribe(() => {
      console.log('Logout realizado com sucesso!');
      this.router.navigate(['/login']);
    });
  }
}
