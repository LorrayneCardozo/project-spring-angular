import { Component } from '@angular/core';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { DoctorService } from 'src/app/services/doctor.service';
import { Doctor } from './doctor.model';
import { RegisterDoctorComponent } from './register-doctor/register-doctor.component';
import { Specialty } from 'src/app/enums/specialty.enum';
import { HealthCenterService } from 'src/app/services/health-center.service';
import { HealthCenter } from '../health-center/health-center.model';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor.component.html',
  styleUrls: ['./doctor.component.css'],
})

export class DoctorComponent {
  modalRef: MdbModalRef<RegisterDoctorComponent> | null = null;

  constructor(
    private route: ActivatedRoute,
    private doctorService: DoctorService,
    private healthCenterService: HealthCenterService,
    private modalService: MdbModalService,
    private authService: AuthService,
    private toastr: ToastrService
  ) {}

  role: string = '';

  healthCenterId: number | undefined | string;
  specialty: string | undefined;

  specialties = Specialty;
  healthCenters: HealthCenter[] = [];

  ngOnInit(): void {
    this.authService.getUserAuthenticated().subscribe((user: any) => {
      this.role = user?.role;
    });

    this.healthCenterId = '';
    this.specialty = '';
  
    this.doctorService.findAll().subscribe((data) => {
      this.doctorData = data;
  
      this.filteredDoctors = [...this.doctorData];

      this.healthCenterService.findAll().subscribe((data) => {
        this.healthCenters = data;
  
        this.route.queryParams.subscribe(params => {
          if(params['healthCenterId'] !== undefined) {
            this.healthCenterId = params['healthCenterId'];
            this.filterDoctors();
          }
        });
      });
    });

  }

  doctorData: Doctor[] = [];

  openModal() {
    this.modalRef = this.modalService.open(RegisterDoctorComponent);
    this.modalRef.onClose.subscribe(() => {
      this.doctorService.findAll().subscribe((data) => {
        this.doctorData = data;
        this.filteredDoctors = [...this.doctorData];
  
        this.filterDoctors();
      });
    })
  }


  selectHealthCenter() {
    this.filterDoctors();
  }

  selectSpecialty() {
    this.filterDoctors();
  }

  filteredDoctors: Doctor[] = [];  

  filterDoctors() {
    this.filteredDoctors = this.doctorData.filter((doctor) => {
      const filterByHealthCenter =
        !this.healthCenterId || doctor.healthCenter?.id == this.healthCenterId;
      const filterBySpecialty =
        !this.specialty || doctor.specialty === this.specialty;
      return filterByHealthCenter && filterBySpecialty;
    });
  }

  deleteDoctor(idDoctor: number): void {
    this.doctorService.delete(idDoctor).subscribe((response: any) => {
      this.toastr.success('Médico excluído com sucesso!', 'Sucesso');
      console.log('Médico excluido! ', response);
      this.doctorService.findAll().subscribe((data) => {
        this.doctorData = data;
        this.filteredDoctors = [...this.doctorData];
  
        this.filterDoctors();
      });
    });
  }

}
