import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class SchedulingService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  findAll(): Observable<any> {
    return this.http.get(`${this.apiUrl}/scheduling`);
  }

  update(data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/scheduling/${data.id}`, data);
  }

  getSchedulingPerson(data: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/scheduling/person/${data}`);
  }

  getSchedulingDoctor(data: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/scheduling/doctor/${data}`);
  }

  findByPersonOrDoctorAndDate(idPerson: number, idDoctor: number, date: string): Observable<any> {
    const params = new HttpParams()
      .set('idPerson', idPerson)
      .set('idDoctor', idDoctor)
      .set('date', date);

    return this.http.get(`${this.apiUrl}/scheduling/person-doctor-date`, { params });
  }

  create(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/scheduling`, data);
  }
}
