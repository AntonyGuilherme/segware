import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AutenticacaoServiceService } from '../services/autenticacao-service.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  
  
  constructor(
    private autenticacaoService: AutenticacaoServiceService,
    private router: Router
  ) {}
  
  
  canActivate() {

    if(!this.autenticacaoService.usuarioEstaLogado()){
      return true;
    }

    return this.router.navigate(['/home']);
  
  }
  
}
