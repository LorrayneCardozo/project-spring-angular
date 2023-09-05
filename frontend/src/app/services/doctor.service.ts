import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  findAll(): Observable<any> {
    return this.http.get(`${this.apiUrl}/doctor`);
  }

  findById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/doctor/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/doctor`, data);
  }

  delete(data: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/doctor/${data}`);
  }

  update(data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/doctor/${data.id}`, data);
  }
}
