import { Component, OnInit } from '@angular/core';
import { DayPilot } from '@daypilot/daypilot-lite-angular';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { ToastrService } from 'ngx-toastr';
import { switchMap, take } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth.service';
import { DoctorAvailabilityService } from 'src/app/services/doctor-availability.service';
import { PersonService } from 'src/app/services/person.service';
import { SchedulingService } from 'src/app/services/scheduling.service';

@Component({
  selector: 'app-create-scheduling',
  templateUrl: './create-scheduling.component.html',
  styleUrls: ['./create-scheduling.component.css']
})
export class CreateSchedulingComponent implements OnInit {
  constructor(
    public modalRef: MdbModalRef<CreateSchedulingComponent>,
    private personService: PersonService,
    private authService: AuthService,
    private doctorAvailabilityService: DoctorAvailabilityService,
    private schedulingSevice: SchedulingService,
    private toastr: ToastrService
  ) {}

  // idDoctor
  data: any;
  idPatient: number = 0;
  username: string = '';
  selectedDate: DayPilot.Date = DayPilot.Date.today();
  doctorAvailability: any = {}; 
  unavailable: string[] = [];

  selectedTime: string = '';
  availableTimes: string[] = [];
  
  ngOnInit(): void {
    this.authService.isAuthenticated().pipe(
      switchMap(() => this.authService.getUsernameAuthenticated()),
      switchMap(username => {
        this.username = username;
        return this.personService.getUser(username);
      }),
      take(1)
    ).subscribe((response: any) => {
      this.idPatient = response?.id;
    });

    this.doctorAvailabilityService.findByIdDoctor(this.data).subscribe((response: any) => {
      this.doctorAvailability = response;
    });


  }

  generateAvailableTimes(dayOfWeek: string): void {
    const availability = this.findAvailabilityForDayOfWeek(dayOfWeek);
    
    if(availability == null || availability?.startTime == null) {
      this.availableTimes = [];
    }  else {
      this.availableTimes = [];
      const startTime = availability?.startTime;
      const appointmentDuration = "01:00";
      const durationParts = appointmentDuration.split(':');
      const durationInMinutes = parseInt(durationParts[0]) * 60 + parseInt(durationParts[1]);

      const startTimeParts = startTime.split(':');
      const startTimeInMinutes = parseInt(startTimeParts[0]) * 60 + parseInt(startTimeParts[1]);

      const endTimeParts = availability?.endTime.split(':');
      const endTimeInMinutes = parseInt(endTimeParts[0]) * 60 + parseInt(endTimeParts[1]);

      let currentTimeInMinutes = startTimeInMinutes;

      while (currentTimeInMinutes + durationInMinutes <= endTimeInMinutes) {
        const hours = Math.floor(currentTimeInMinutes / 60);
        const minutes = currentTimeInMinutes % 60;
        const formattedTime = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
        
        this.availableTimes.push(formattedTime);
        currentTimeInMinutes += durationInMinutes;
      }
      this.availableTimes = this.availableTimes.filter(time => !this.unavailable.includes(time));

    }

  }

  handleDateChange(date: DayPilot.Date): void {
    this.selectedDate = date;
    const daysOfWeek = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
    const dayOfWeek = daysOfWeek[date.toDate().getDay()];
    
    this.formatSelectedDate();

    const listUnavailable: string[] = [];
    this.schedulingSevice.findByPersonOrDoctorAndDate(this.idPatient, this.data, this.selectedDate.toString("yyyy-MM-dd")).subscribe((response: any) => {
      
      for (const unavailable of response) {
        listUnavailable.push(unavailable?.appointmentTime.substring(0, 5));
      }
      this.unavailable = listUnavailable;
      this.generateAvailableTimes(dayOfWeek);
    });
  }

  selectedDateFormatted: string = '';
  formatSelectedDate(): void {
    if (this.selectedDate) {
      this.selectedDateFormatted = this.selectedDate.toString("dd/MM/yyyy");
    } else {
      this.selectedDateFormatted = "";
    }
  }
 
  findAvailabilityForDayOfWeek(dayOfWeek: string): any {
    for (const availability of this.doctorAvailability) {
      if (availability.dayOfWeek === dayOfWeek) {
        return availability;
      }
    }
    return null;
  }

  createScheduling() {
    const data = {
      appointmentTime: this.selectedTime,
      appointmentDate: this.selectedDate.toString("yyyy-MM-dd"),
      doctor: {
        id: this.data
      },
      person: {
        id: this.idPatient
      },
      status: "AGENDADO"
    }
    
    this.schedulingSevice.create(data).subscribe((response: any) => {
      this.toastr.success('Agendamento concluído com sucesso!', 'Sucesso');
    })
    this.modalRef.close();
  }

}
