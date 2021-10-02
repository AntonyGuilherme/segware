import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AutenticacaoServiceService } from '../services/autenticacao-service.service';

@Injectable({
  providedIn: 'root'
})
export class AutenticacaoGuardGuard implements CanActivate {


  constructor(
    private autenticacaoService: AutenticacaoServiceService,
    private router: Router
  ) {



  }

  canActivate() {

    if (this.autenticacaoService.usuarioEstaLogado()) {
      console.log('LOGADO')
      return this.router.navigate(['/']);
    }

    return true;

  }

}
