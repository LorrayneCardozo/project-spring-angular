import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, map, switchMap, tap } from 'rxjs';
import { environment } from 'src/environment/environment';
import { StorageMap } from '@ngx-pwa/local-storage';
import { PersonService } from './person.service';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;
  private authenticatedUserSubject = new BehaviorSubject<any>(null);

  constructor(
    private http: HttpClient,
    private localStorage: StorageMap,
    private personService: PersonService
  ) {
    // Verifica se o usuário está autenticado ao inicializar o serviço
    this.checkAuthentication();
  }

  saveLocalStorage(key: string, value: string): Observable<void> {
    return this.localStorage.set(key, value);
  }

  clearLocalStorage(): Observable<void> {
    return this.localStorage.clear();
  }

  login(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, data).pipe(
      tap((response: any) => {
        this.saveLocalStorage('access_token', response?.token).subscribe();
        this.saveLocalStorage('username', data.username).subscribe();
        this.checkAuthentication();
      })
    );
  }

  logout(): Observable<any> {
    return this.clearLocalStorage().pipe(
      switchMap(() => this.http.post(`${this.apiUrl}/logout`, null))
    );
  }

  isAuthenticated(): Observable<boolean> {
    return this.localStorage.get('access_token').pipe(
      map(token => token !== undefined)
    );
  }

  getUsernameAuthenticated(): Observable<any> {
    return this.localStorage.get('username');
  }

  getUserAuthenticated(): Observable<any> {
    return this.authenticatedUserSubject.asObservable();
  }

  private checkAuthentication() {
    this.getUsernameAuthenticated().subscribe((username: any) => {
      if (username) {
        this.personService.getUser(username).subscribe((response: any) => {
          this.authenticatedUserSubject.next(response);
        });
      } else {
        this.authenticatedUserSubject.next(null);
      }
    });
  }
}
