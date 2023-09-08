import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { DayPilot, DayPilotCalendarComponent } from "@daypilot/daypilot-lite-angular";

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
})
export class CalendarComponent implements OnInit {
  @ViewChild('calendar') calendar!: DayPilotCalendarComponent;
  @ViewChild("day") day!: DayPilotCalendarComponent;
  @Input() events: DayPilot.EventData[] = [];

  constructor() {
  }

  date = DayPilot.Date.today();

  configWeek: any = {
    viewType: "Week", 
    startDate: DayPilot.Date.today().firstDayOfWeek(),
    days: DayPilot.Date.today().daysInMonth(),
    locale: "pt-br",
  };


  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    //     text: "Evento 1",
    //     start: DayPilot.Date.today().addDays(1),
    //     end: DayPilot.Date.today().addDays(2),
    //   },
    //   {
    //     id: "2",
    //     text: "Evento 2",
    //     start: DayPilot.Date.today().addHours(4),
    //     end: DayPilot.Date.today().addHours(4.5),
    //   },
    // ];
  }

  navigateWeek(direction: number) {
    const currentStartDate = this.configWeek.startDate;
    const newStartDate = currentStartDate.addDays(7 * direction);

    this.configWeek.startDate = newStartDate;

    this.loadEvents();
  }
}
