import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './pages/user/login/login.component';
import {AutenticacaoGuardGuard} from './pages/user/guards/autenticacao-guard.guard';

const routes: Routes = [
 
  { path : '', pathMatch: 'full', redirectTo: '/login' },
  {
    path: 'login',
    component: LoginComponent,
    canActivate:[ AutenticacaoGuardGuard ]
  }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    HttpClientModule,
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
