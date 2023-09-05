import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class DoctorAvailabilityService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  findByIdDoctor(data: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/doctorAvailability/doctor/${data}`);
  }
}
