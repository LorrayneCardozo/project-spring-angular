import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { Specialty } from 'src/app/enums/specialty.enum';
import { CreateSchedulingComponent } from 'src/app/pages/doctor/create-scheduling/create-scheduling.component';
import { Doctor } from 'src/app/pages/doctor/doctor.model';
import { RegisterDoctorComponent } from 'src/app/pages/doctor/register-doctor/register-doctor.component';
import { AuthService } from 'src/app/services/auth.service';
import { DoctorService } from 'src/app/services/doctor.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  @Input() doctorData!: Doctor;
  @Output() clickIconEvent = new EventEmitter<number>();
  modalScheduling: MdbModalRef<CreateSchedulingComponent> | null = null;
  modalEdit: MdbModalRef<RegisterDoctorComponent> | null = null;

  constructor(
    private modalService: MdbModalService,
    private doctorService: DoctorService,
    private authService: AuthService
  ) {}

  specialty: string = '';
  role: string = '';

  ngOnInit(): void {
    this.specialty = Specialty[this.doctorData?.specialty as keyof typeof Specialty];
    //let index = Object.values(Specialty).indexOf('Cirurgia da mão' as unknown as Specialty);
    //console.log(Object.keys(Specialty).at(6))
    this.authService.getUserAuthenticated().subscribe((user: any) => {
      this.role = user.role;
    });
  }

  delete(): void {
    this.clickIconEvent.emit(this.doctorData?.id);
  }
  
  openModal() {
    const idDoctor = this.doctorData?.id;
    this.modalScheduling = this.modalService.open(CreateSchedulingComponent);
    this.modalScheduling.component.data = idDoctor;
  }

  edit(id: number): void {
    this.modalEdit = this.modalService.open(RegisterDoctorComponent);
    this.modalEdit.component.id = id;
  
    this.modalEdit.onClose.subscribe(() => {
      this.doctorService.findById(id).subscribe((response: any) => {
        this.doctorData = response;
      });
    });
  }
}
