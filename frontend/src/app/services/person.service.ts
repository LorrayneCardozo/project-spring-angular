import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getUser(data: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/person/user/${data}`);
  }  
}
