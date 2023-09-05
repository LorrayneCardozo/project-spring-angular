import { Component, OnInit } from '@angular/core';
import { DayPilot } from '@daypilot/daypilot-lite-angular';
import { switchMap, take } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { PersonService } from 'src/app/services/person.service';
import { SchedulingService } from 'src/app/services/scheduling.service';

@Component({
  selector: 'app-scheduling',
  templateUrl: './scheduling.component.html',
  styleUrls: ['./scheduling.component.css']
})
export class SchedulingComponent implements OnInit {
  constructor(
    private schedulingService: SchedulingService,
    private authService: AuthService,
    private personService: PersonService
  ) {}
  
  idPatient: number = 0;
  events: DayPilot.EventData[] = [];

  ngOnInit(): void {
    this.authService.isAuthenticated().pipe(
      switchMap(() => this.authService.getUsernameAuthenticated()),
      switchMap(username => {
        return this.personService.getUser(username);
      }),
      take(1)
    ).subscribe((response: any) => {
      this.idPatient = response?.id;
      
      this.schedulingService.getSchedulingPerson(this.idPatient).subscribe((response:any) => {
        this.events = this.generateEventList(response);
        
      })
    });
  }

  generateEventList(data: any): any[] {
    const events: any[] = [];
    
    for (const item of data) {
      const hour: number = parseInt(item.appointmentTime.split(':')[0]);
      const event = {
        id: item.id.toString(),
        text: 'Consulta com Dr(a).: ' + item.doctor.name,
        start: new DayPilot.Date(item.appointmentDate).addHours(hour),
        end: new DayPilot.Date(item.appointmentDate).addHours(hour+1), 
      };
      events.push(event);
    }
  
    return events;
  }

}
