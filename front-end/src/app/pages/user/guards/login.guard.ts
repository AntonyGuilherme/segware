import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AutenticacaoService } from '../services/autenticacao-service.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  
  
  constructor(
    private autenticacaoService: AutenticacaoService,
    private router: Router
  ) {}
  
  
  canActivate() {

    if(!this.autenticacaoService.usuarioEstaLogado()){
      return true;
    }

    return this.router.navigate(['/home']);
  
  }
  
}
