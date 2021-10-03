import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './pages/user/login/login.component';
import {AutenticacaoGuardGuard} from './pages/user/guards/autenticacao-guard.guard';
import {LoginGuard} from './pages/user/guards/login.guard';
import { HomeComponent } from './pages/home/home.component';
import { PostesUsuarioComponent } from './pages/postes-usuario/postes-usuario.component';

const routes: Routes = [
 
  { path : '', pathMatch: 'full', redirectTo: '/login' },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [LoginGuard]
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate:[ AutenticacaoGuardGuard ]
  },

  {
    path : 'postes/usuario',
    component: PostesUsuarioComponent,
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
