import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HealthCenterComponent } from './pages/health-center/health-center.component';
import { LoginComponent } from './pages/login/login.component';
import { DoctorComponent } from './pages/doctor/doctor.component';
import { AuthGuard } from './services/auth.guard';
import { SchedulingComponent } from './pages/scheduling/scheduling.component';
import { SchedulingAdminComponent } from './pages/scheduling-admin/scheduling-admin.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, pathMatch: 'full' },
  { path: 'health-center', component: HealthCenterComponent, canActivate: [AuthGuard] },
  { path: 'doctor', component: DoctorComponent, canActivate: [AuthGuard] },
  { path: 'scheduling', component: SchedulingComponent, canActivate: [AuthGuard] },
  { path: 'scheduling-admin', component: SchedulingAdminComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
