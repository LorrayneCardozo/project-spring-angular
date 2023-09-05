import { Component, EventEmitter, OnInit } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { HealthCenterService } from 'src/app/services/health-center.service';
import { HealthCenter } from '../../health-center/health-center.model';
import { DoctorService } from 'src/app/services/doctor.service';
import { Specialty } from '../../../enums/specialty.enum'
import { Observable } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

interface Availability {
  dayOfWeek: string;
  startTime: string;
  endTime: string;
}

@Component({
  selector: 'app-register-doctor',
  templateUrl: './register-doctor.component.html',
  styleUrls: ['./register-doctor.component.css']
})
export class RegisterDoctorComponent implements OnInit{
  newDoctorCreated = new EventEmitter<any>();

  constructor(
    private healthCenterService: HealthCenterService, 
    private doctorService: DoctorService,
    public modalRef: MdbModalRef<RegisterDoctorComponent>,
    private toastr: ToastrService
    ) {}

  specialtiesEnum = Specialty;
  daysOfWeek = Object.values(DayOfWeek);

  healthCenterData: HealthCenter[] = [];

  id: number = 0;
  cpf: string = '';
  crm: string = '';
  name: string = '';
  phone: string = '';
  selectedSpecialty: string = '';
  selectedHealthCenter: string = '';
  availabilities: Availability[] = [];
  
  ngOnInit(): void {
    this.healthCenterService.findAll().subscribe((data) => {
      this.healthCenterData = data;
    });
    
    if(this.id != 0){
      this.doctorService.findById(this.id).subscribe((response: any) => {
        this.name = response.name;
        this.cpf = response.cpf;
        this.crm = response.crm
        this.phone = response.phone;
        this.selectedSpecialty = response.specialty;
        this.selectedHealthCenter = response.healthCenter.id;
        this.availabilities = response.availabilities;
      });
    }      
  }

  getAvailability(day: string) {
    let availability = this.availabilities.find(avail => avail.dayOfWeek === day);
    
    if (!availability) {
      availability = {
        dayOfWeek: day,
        startTime: '',
        endTime: '',
      };
      this.availabilities.push(availability);
    }
    
    return availability;
  }

  saveDoctor(): void {
    const doctorData: {
      id?: number;
      cpf: string;
      crm: string;
      name: string;
      phone: string;
      specialty: string;
      healthCenter: {id: number};
      availabilities: Availability[];
    } = {
      cpf: this.cpf,
      name: this.name,
      crm: this.crm,
      phone: this.phone,
      specialty: this.selectedSpecialty,
      healthCenter: {
        id: parseInt(this.selectedHealthCenter)
      },
      availabilities: this.availabilities,
    };
    
    if(this.id == 0){
      this.doctorService.create(doctorData).subscribe((response: Observable<any>) => {
        this.toastr.success('Médico cadastrado com sucesso!', 'Sucesso');
      });
    } else{
      doctorData.id = this.id;
      this.doctorService.update(doctorData).subscribe((response: Observable<any>) => {
        this.toastr.success('Médico atualizado com sucesso!', 'Sucesso');
      });
    }
    this.newDoctorCreated.emit(true);
    this.modalRef.close();
  }
}



enum DayOfWeek {
  SUNDAY = 'Domingo',
  MONDAY = 'Segunda-feira',
  TUESDAY = 'Terça-feira',
  WEDNESDAY = 'Quarta-feira',
  THURSDAY = 'Quinta-feira',
  FRIDAY = 'Sexta-feira',
  SATURDAY = 'Sábado',
}