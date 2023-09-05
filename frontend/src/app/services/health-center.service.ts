import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class HealthCenterService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  findAll(): Observable<any> {
    return this.http.get(`${this.apiUrl}/healthCenter`);
  }

  findById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/healthCenter/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/healthCenter`, data);
  }
  
  update(data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/healthCenter/${data.id}`, data);
  }
  
  delete(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/healthCenter/${id}`);
  }
}
