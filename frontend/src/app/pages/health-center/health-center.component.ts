import { Component, OnInit } from '@angular/core';
import { HealthCenterService } from 'src/app/services/health-center.service';
import { HealthCenter } from './health-center.model';
import { RegisterHealthCenterComponent } from './register-health-center/register-health-center.component';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-health-center',
  templateUrl: './health-center.component.html',
  styleUrls: ['./health-center.component.css']
})
export class HealthCenterComponent implements OnInit {
  modalRef: MdbModalRef<RegisterHealthCenterComponent> | null = null;
  searchText: string = '';

  constructor(
    private router: Router,
    private healthCenterService: HealthCenterService,
    private modalService: MdbModalService,
    private authService: AuthService,
    private toastr: ToastrService
  ) { }

  role: string = '';
  healthCenters: HealthCenter[] = [];

  ngOnInit(): void {
    this.authService.getUserAuthenticated().subscribe((user: any) => {
      this.role = user.role;
    });
    this.healthCenterService.findAll().subscribe((response: any) => {
      this.healthCenters = response;
    });
  }

  redirectToDoctorsPage(healthCenterId: number) {
    this.router.navigate(['/doctor'], { queryParams: { healthCenterId: healthCenterId } });
  }

  create() {
    this.modalRef = this.modalService.open(RegisterHealthCenterComponent);
    this.modalRef.onClose.subscribe(() => {
      this.healthCenterService.findAll().subscribe((response: any) => {
        this.healthCenters = response;
      });
    });
  }

  delete(id: number): void {
    this.healthCenterService.delete(id).subscribe(() => {
      this.toastr.success('Unidade excluída com sucesso!');
      this.healthCenterService.findAll().subscribe((response: any) => {
        this.healthCenters = response;
      });
    })
  }

  edit(id: number): void {
    this.modalRef = this.modalService.open(RegisterHealthCenterComponent);
    this.modalRef.component.id = id;

    this.modalRef.onClose.subscribe(() => {
      this.healthCenterService.findAll().subscribe((response: any) => {
        this.healthCenters = response;
      });
    });
  }

}
