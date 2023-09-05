import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { HealthCenterComponent } from './pages/health-center/health-center.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { CardComponent } from './components/card/card.component';
import { DoctorComponent } from './pages/doctor/doctor.component';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ModalModule, BsModalService } from 'ngx-bootstrap/modal';
import { RegisterDoctorComponent } from './pages/doctor/register-doctor/register-doctor.component';
import { MdbModalService } from 'mdb-angular-ui-kit/modal';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { AuthInterceptor } from './services/auth-interceptor';
import { StorageModule } from '@ngx-pwa/local-storage';
import { SchedulingComponent } from './pages/scheduling/scheduling.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import ptLocale from '@angular/common/locales/pt';
import { CreateSchedulingComponent } from './pages/doctor/create-scheduling/create-scheduling.component';
import { DayPilotModule } from '@daypilot/daypilot-lite-angular';
import { CalendarNavigatorComponent } from './components/calendar-navigator/calendar-navigator.component';
import { ToastrModule } from 'ngx-toastr';
import { provideAnimations } from '@angular/platform-browser/animations';

import { provideToastr } from 'ngx-toastr';
import { RegisterHealthCenterComponent } from './pages/health-center/register-health-center/register-health-center.component';
import { SchedulingAdminComponent } from './pages/scheduling-admin/scheduling-admin.component';

registerLocaleData(ptLocale);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HealthCenterComponent,
    SidebarComponent,
    CardComponent,
    DoctorComponent,
    RegisterDoctorComponent,
    SchedulingComponent,
    CalendarComponent,
    CreateSchedulingComponent,
    CalendarNavigatorComponent,
    RegisterHealthCenterComponent,
    SchedulingAdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule, 
    MatDialogModule,
    ModalModule,
    FormsModule,
    MatIconModule,
    StorageModule,
    FullCalendarModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    DayPilotModule,
    ToastrModule.forRoot(),
  ],
  providers: [
    BsModalService, 
    MdbModalService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: LOCALE_ID, useValue: 'pt' },
    provideAnimations(),
    provideToastr(), 
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }