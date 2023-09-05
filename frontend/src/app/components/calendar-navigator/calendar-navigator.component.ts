import { Component, EventEmitter, Output } from '@angular/core';
import { DayPilot } from '@daypilot/daypilot-lite-angular';

@Component({
  selector: 'app-calendar-navigator',
  templateUrl: './calendar-navigator.component.html',
  styleUrls: ['./calendar-navigator.component.css']
})
export class CalendarNavigatorComponent {
  events: DayPilot.EventData[] = [];
  date = DayPilot.Date.today();
  @Output() dateChanged = new EventEmitter<DayPilot.Date>();


  configNavigator: DayPilot.NavigatorConfig = {
    locale: "pt-br",
    showMonths: 1, 
    cellWidth: 25,
    cellHeight: 25,
    //onVisibleRangeChanged: args => {
      //this.loadEvents();
    //}
  };

  changeDate(date: DayPilot.Date): void {
    this.date = date;
    this.dateChanged.emit(date);
  }
}
