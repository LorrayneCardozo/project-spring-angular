import { Component, EventEmitter, OnInit } from '@angular/core';
import { HealthCenterService } from 'src/app/services/health-center.service';
import { ToastrService } from 'ngx-toastr';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { HealthCenter } from '../health-center.model';

@Component({
  selector: 'app-register-health-center',
  templateUrl: './register-health-center.component.html',
  styleUrls: ['./register-health-center.component.css']
})
export class RegisterHealthCenterComponent implements OnInit {
  newHealthCenterCreated = new EventEmitter<HealthCenter>();

  constructor(
    private healthCenterService: HealthCenterService, 
    public modalRef: MdbModalRef<RegisterHealthCenterComponent>,
    private toastr: ToastrService
    ) {}

    ngOnInit(): void {
      if(this.id != 0){
        this.healthCenterService.findById(this.id).subscribe((response: any) => {
          this.name = response.name;
          this.address = response.address;
          this.phone = response.phone;
        });
      }      
    }

    id: number = 0;
    name: string = '';
    address: string = '';
    phone: string = '';

    save(): void {
      const healthCenterData: {
        name: string;
        address: string;
        phone: string;
        id?: number;
      } = {
        name: this.name,
        address: this.address,
        phone: this.phone
      };

      if(this.id == 0){
        this.healthCenterService.create(healthCenterData).subscribe((response: any) => {
          this.toastr.success('Unidade cadastrada com sucesso!');
          this.newHealthCenterCreated.emit(response);
          this.modalRef.close();
        })
      } else {
        healthCenterData.id = this.id;
        this.healthCenterService.update(healthCenterData).subscribe((response: any) => {
          this.toastr.success('Unidade atualizada com sucesso!');
          this.newHealthCenterCreated.emit(response);
          this.modalRef.close();
        })
      }
    }

}
