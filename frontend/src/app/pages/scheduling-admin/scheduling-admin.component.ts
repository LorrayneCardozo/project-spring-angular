import { Component, OnInit } from '@angular/core';
import { SchedulingService } from 'src/app/services/scheduling.service';

@Component({
  selector: 'app-scheduling-admin',
  templateUrl: './scheduling-admin.component.html',
  styleUrls: ['./scheduling-admin.component.css']
})
export class SchedulingAdminComponent implements OnInit {

  constructor(
    private schedulingService: SchedulingService
  ) { }

  listScheduling: any;
  originalSchedulingStates: any;

  ngOnInit(): void {
    this.schedulingService.findAll().subscribe((response: any) => {
      this.listScheduling = response;
      this.originalSchedulingStates = JSON.parse(JSON.stringify(response));
    });
  }

  editStates: { [key: number]: boolean } = {};

  edit(index: number): void {
    this.editStates[index] = true;
  }

  confirm(index: number): void {
    const updatedScheduling = this.listScheduling[index];
    this.schedulingService.update(updatedScheduling).subscribe(() => {
      this.editStates[index] = false;
    });
  }

  cancel(index: number): void {
    this.listScheduling[index] = JSON.parse(JSON.stringify(this.originalSchedulingStates[index]));
    this.editStates[index] = false;
  }

  formattedDate(dateString: string) {
    const date = new Date(dateString);
    return date.toLocaleString('pt-BR').slice(0, 10);
  }

  formattedTime(time: string) {
    return time.slice(0, 5);
  }

  formattedStatus(status: string) {
    switch (status) {
      case 'AGENDADO':
        return 'Agendado';
      case 'CONCLUIDO':
        return 'Concluído';
      case 'CANCELADO':
        return 'Cancelado';
      default:
        return '';
    }
  }
}
